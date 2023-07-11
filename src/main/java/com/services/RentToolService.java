/*
 * RentToolService.java
 * 7/7/2023
 * Ian Percy
 * 
 * Service layer to handle tool renting logic
 */
package com.services;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.TemporalAdjusters;
import com.models.RentalAgreement;
import com.models.ToolCharges;
import com.models.ToolChoices;

@Service
public class RentToolService {
    private static final Logger logger = LoggerFactory.getLogger(RentToolService.class);
    @Autowired
    ToolChargesService toolChargesService;
    @Autowired
    ToolChoicesService toolChoicesService;

    /*
     * Generate a rental agreement once passed in rental parameters
     * 
     * @param code string to represent the tool choices code
     * 
     * @param inputDate string representing the checkout date
     * 
     * @param rentalDays int number of days for the rental
     * 
     * @param discountRaw int that is the discount percentage
     * 
     * @return string with the rental agreement printed out
     */
    public String rentTool(String code, String inputDate, String rentalDays, String discountRaw) {
        try {
            // Gather the ToolChoices object and the ToolCharges object
            ToolChoices toolChoices;
            ToolCharges toolCharges;
            code = code.toUpperCase();
            toolChoices = toolChoicesService.findToolChoicesByCode(code);
            if (toolChoices == null) {
                logger.error("Cannot get Tool Choice");
                return "ERROR_TOOL_CHOICE";
            }
            toolCharges = toolChargesService.findToolChargesByTypeId(toolChoices.getToolType().getId());
            if (toolCharges == null) {
                logger.error("Cannot get Tool Charge");
                return "ERROR_TOOL_CHARGE";
            }

            // Validate and calculate the discount percent
            int discountInteger;
            try {
                discountInteger = Integer.parseInt(discountRaw);
            } catch (NumberFormatException discounte) {
                logger.error("Percent discount must be integer");
                return "ERROR_PERCENT_FORMAT";
            }
            // Check discount percent is 0 - 100
            if (discountInteger < 0 || discountInteger > 100) {
                return "ERROR_PERCENT_OUT_OF_RANGE";
            }
            double discountCalculated = discountInteger / 100.0;

            // Format the start date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate startDate = formatter.parse(inputDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // TODO: add in ability for counter for holiday + weekend + weekday if those are
            // different charges
            // Grab the rental days as the end counter for the loop
            int daysToCharge = 0;
            int maxDaysToCharge;
            try {
                maxDaysToCharge = Integer.parseInt(rentalDays);
            } catch (NumberFormatException rentale) {
                logger.error("Rental days must be integer");
                return "ERROR_RENTAL_DAYS_FORMAT";
            }
            // Check to make sure that the rental day is 1 or greater
            if (maxDaysToCharge < 1) {
                return "ERROR_RENTAL_DAY_COUNT_OUT_OF_RANGE";
            }
            // Create the date to be used in the loop below
            // The start date for the rental is the day after the checkout day
            LocalDate date = startDate.plusDays(1);
            // Lists for saving the charge and no charge dates
            List<LocalDate> chargeDates = new ArrayList<LocalDate>();
            List<LocalDate> noChargeDates = new ArrayList<LocalDate>();
            for (; maxDaysToCharge > 0; date = date.plusDays(1), maxDaysToCharge -= 1) {
                int dayOfWeek = date.getDayOfWeek().getValue();
                // Check if holiday
                if (holidayDate(date)) {
                    // If holiday and the tool charges for holiday
                    if (toolCharges.getHolidayCharge() == 1) {
                        daysToCharge += 1;
                        chargeDates.add(date);
                        continue;
                    } else {
                        noChargeDates.add(date);
                        continue;
                    }
                }
                // Check if weekday
                if (dayOfWeek != 6 && dayOfWeek != 7) {
                    // If weekday and the tool charges for weekday
                    if (toolCharges.getWeekdayCharge() == 1) {
                        daysToCharge += 1;
                        chargeDates.add(date);
                        continue;
                    }
                }
                // Check if weekend
                if (dayOfWeek == 6 || dayOfWeek == 7) {
                    // If weekend and the tool charges for weekday
                    if (toolCharges.getWeekendCharge() == 1) {
                        daysToCharge += 1;
                        chargeDates.add(date);
                        continue;
                    }
                }
                // Otherwise add to the no charge dates
                noChargeDates.add(date);
            }

            // Pre-discounted charge -> calculated after loop
            double prediscountCharge = new BigDecimal(daysToCharge * toolCharges.getDailyCharge())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
            // Discount percent -> from input
            int discountPercent = (int) Math.round(discountCalculated * 100);
            // Discount Amount -> amount saved from discount - calculated after loop
            double discountAmount = new BigDecimal(prediscountCharge * discountCalculated)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
            // Final charge -> pre-discounted charge minus discount amount - caluclated
            // after loop
            double finalCharge = new BigDecimal(prediscountCharge - discountAmount)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();

            logger.info("Charge Dates " + chargeDates.toString());
            logger.info("No Charge Dates " + noChargeDates.toString());

            // Generate RentalAgreement object
            RentalAgreement rentalAgreement = new RentalAgreement(toolChoices,
                    Integer.parseInt(rentalDays),
                    startDate,
                    date.minusDays(1), // Subtract a day because was incremented in the loop
                    toolCharges,
                    daysToCharge,
                    prediscountCharge,
                    discountPercent,
                    discountAmount,
                    finalCharge);

            return generateAgreement(rentalAgreement);

        } catch (Exception e) {
            logger.error("Error in rent tool " + e);
            return "ERROR_GENERAL";
        }
    }

    /*
     * Calculate if day is a holiday
     * Currently checking for July 4th and Labor Day
     * 
     * @param date LocalDate to check
     * 
     * @return boolean of if day is holiday or not
     */
    public boolean holidayDate(LocalDate date) {
        // Check if Labor Day
        if (date.getMonthValue() == 9) {
            LocalDate firstMonday = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
            if (date.equals(firstMonday)) {
                logger.info("Labor Day " + date);
                return true;
            }
        }
        // Check if July 4th or observed July 4th
        // If July 4th is only Saturday, observed day is Friday
        // If July 4th is only Sunday, observed day is Monday
        if (date.getMonthValue() == 7) {
            if (date.getDayOfMonth() == 3 && date.getDayOfWeek().getValue() == 5) {
                logger.info("Observed July 4th on Friday " + date);
                return true;
            } else if (date.getDayOfMonth() == 5 && date.getDayOfWeek().getValue() == 1) {
                logger.info("Observed July 4th on Monday " + date);
                return true;
            } else if (date.getDayOfMonth() == 4 && date.getDayOfWeek().getValue() < 6) {
                logger.info("Acutal July 4th " + date);
                return true;
            }
        }
        return false;
    }

    /*
     * Generate rental agreement for the tool rental
     * 
     * @param rentalAgreement RentalAgreement object
     * 
     * @return string with the formatted rental agreement String
     */
    public String generateAgreement(RentalAgreement rentalAgreement) {
        // Created extra function call here so if we want to modify the output format or
        // add some logic with the output of the agreement
        return rentalAgreement.formatRentalAgreement();
    }
}

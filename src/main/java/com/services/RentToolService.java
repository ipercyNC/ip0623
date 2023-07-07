package com.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.TemporalAdjusters;
import com.models.ToolCharges;
import com.models.ToolChoices;

@Service
public class RentToolService {
    @Autowired
    ToolChargesService toolChargesService;
    @Autowired
    ToolChoicesService toolChoicesService;

    public List<String> rentTool(String code, String s, String rentalDays, String discountRaw) {
        try {
            ToolChoices toolChoices = toolChoicesService.findToolChoicesByCode(code);
            ToolCharges toolCharges = toolChargesService.findToolChargesByTypeId(toolChoices.getToolType().getId());
            int discount = Integer.parseInt(discountRaw);
            double discountCalculated = discount / 100.0;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date rawStart = formatter.parse(s);
            LocalDate startDate = rawStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // TODO: add in ability for counter for holiday + weekend + weekday if those are
            // different charges
            int daysToCharge = 0;
            int dateCounter = Integer.parseInt(rentalDays);
            LocalDate date = startDate.plusDays(1);
            List<LocalDate> chargeDates = new ArrayList<LocalDate>();
            List<LocalDate> noChargeDates = new ArrayList<LocalDate>();
            for (; dateCounter > 0; date = date.plusDays(1), dateCounter -= 1) {
                // Do your job here with `date`.
                int dayOfWeek = date.getDayOfWeek().getValue();

                // TODO: make sure holiday doesn't get double charged if on weekend
                if (holidayDate(date)) {
                    if (toolCharges.getHolidayCharge() == 1) {
                        daysToCharge += 1;
                        chargeDates.add(date);
                        continue;
                    } else {
                        noChargeDates.add(date);
                        continue;
                    }
                }

                if (dayOfWeek != 6 && dayOfWeek != 7) {
                    if (toolCharges.getWeekdayCharge() == 1) {
                        daysToCharge += 1;
                        chargeDates.add(date);
                        continue;
                    }
                }

                if (dayOfWeek == 6 || dayOfWeek == 7) {
                    if (toolCharges.getWeekendCharge() == 1) {
                        daysToCharge += 1;
                        chargeDates.add(date);
                        continue;
                    }
                }
                noChargeDates.add(date);
            }

            // Tool Code -> get from ToolChoices.getCode()
            String outputToolCode = toolChoices.getCode();
            // Tool Type -> get from ToolChoices.getToolType()
            String outputToolType = toolChoices.getToolType().getName();
            // Tool Brand -> get from ToolChoices.getToolBrand()
            String outputToolBrand = toolChoices.getToolBrand().getName();
            // Rental Days -> From input
            int outputRentalDays = Integer.parseInt(rentalDays);
            // Checkout date -> from input
            LocalDate outputCheckoutDate = startDate;
            // Due date -> calculated in loop
            // Need to subtract one as we are past the loop execution
            LocalDate outputDueDate = date.minusDays(1);
            // Daily rental charge -> ToolCharges.getDailyCharge()
            double outputDailyCharge = toolCharges.getDailyCharge();
            // Charge days -> calculated in loop
            int outputChargeDays = daysToCharge;
            // Pre-discounted charge -> calculated after loop
            double outputPrediscountCharge = new BigDecimal(daysToCharge * toolCharges.getDailyCharge()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            // Discount percent -> from input
            int outputDiscountCalculated = new BigDecimal(discountCalculated * 100).setScale(0, RoundingMode.HALF_UP).intValue();
            // Discount Amount -> amount saved from discount - calculated after loop
            double outputDiscountAmount = new BigDecimal(outputPrediscountCharge * outputDiscountCalculated).setScale(2, RoundingMode.HALF_UP).doubleValue();
            // final charge -> pre-discounted charge minus discount amount - caluclated
            // after loop
            double outputFinalCharge = new BigDecimal(outputPrediscountCharge - outputDiscountAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();

            System.out.println("Charge Dates " + chargeDates.toString());
            System.out.println("No Charge Dates " + noChargeDates.toString());
            return generateAgreement(outputToolCode,
                    outputToolType,
                    outputToolBrand,
                    outputRentalDays,
                    outputCheckoutDate,
                    outputDueDate,
                    outputDailyCharge,
                    outputChargeDays,
                    outputPrediscountCharge,
                    outputDiscountCalculated,
                    outputDiscountAmount,
                    outputFinalCharge);
        } catch (ParseException ex) {
            return new ArrayList<>();
        }
    }

    public boolean holidayDate(LocalDate date) {
        if (date.getMonthValue() == 9) {
            LocalDate firstMonday = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
            if (date.equals(firstMonday)) {
                System.out.println("Labor Day " + date);
                return true;
            }
        }
        if (date.getMonthValue() == 7) {
            if (date.getDayOfMonth() == 3 && date.getDayOfWeek().getValue() == 5) {
                System.out.println("Observed July 4th on Friday " + date);
                return true;
            } else if (date.getDayOfMonth() == 3 && date.getDayOfWeek().getValue() == 1) {
                System.out.println("Observed July 4th on Monday " + date);
                return true;
            } else if (date.getDayOfMonth() == 4 && date.getDayOfWeek().getValue() < 6) {
                System.out.println("Acutal July 4th " + date);
                return true;
            }
        }
        return false;
    }

    public List<String> generateAgreement(String outputToolCode,
            String outputToolType,
            String outputToolBrand,
            int outputRentalDays,
            LocalDate outputCheckoutDate,
            LocalDate outputDueDate,
            double outputDailyCharge,
            int outputChargeDays,
            double outputPrediscountCharge,
            int outputDiscountCalculated,
            double outputDiscountAmount,
            double outputFinalCharge) {
        List<String> agreementLines = new ArrayList<String>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        agreementLines.add("Tool Code           : " + outputToolCode);
        agreementLines.add("Tool Type           : " + outputToolType);
        agreementLines.add("Tool Brand          : " + outputToolBrand);
        agreementLines.add("Rental Days         : " + outputRentalDays);
        agreementLines.add("Check out date      : " + outputCheckoutDate.format(formatter));
        agreementLines.add("Due date            : " + outputDueDate.format(formatter));
        agreementLines.add("Daily Rental Charge : $" + outputDailyCharge);
        agreementLines.add("Charge Days         : " + outputChargeDays);
        agreementLines.add("Pre-discount Charge : $" + outputPrediscountCharge);
        agreementLines.add("Discount Percent    : " + outputDiscountCalculated + "%");
        agreementLines.add("Discount Amount     : $" + outputDiscountAmount);
        agreementLines.add("Final Charge        : $" + outputFinalCharge);
        return agreementLines;
    }
}

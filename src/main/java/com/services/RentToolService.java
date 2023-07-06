package com.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.models.ToolCharges;

@Service
public class RentToolService {
    @Autowired
    ToolChargesService toolChargesService;

    public double rentTool(String typeId, String s, String rentalDays, String discountRaw) {
        try {
            ToolCharges toolCharges = toolChargesService.findToolChargesById(Integer.parseInt(typeId));
            int discount = Integer.parseInt(discountRaw);
            System.out.println("discount? " + discount);
            double discountCalculated = (100 - discount) / 100.0;
            System.out.println(toolCharges);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date rawStart = formatter.parse(s);
            // Date rawEnd = formatter.parse(e);
            LocalDate startDate = rawStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // LocalDate endDate = rawEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            System.out.println("start date " + startDate);

            // System.out.println("end date " + endDate);
            // TODO: add in ability for counter for holiday + weekend + weekday if those are different charges
            int daysToCharge = 0;
            int dateCounter = Integer.parseInt(rentalDays);
            for (LocalDate date = startDate; dateCounter > 0; date = date.plusDays(1), dateCounter -=1) {
                // Do your job here with `date`.
                String dayOfWeek = date.getDayOfWeek().toString();
                if (toolCharges.getHolidayCharge() == 1) {
                    //TODO: make sure holiday doesn't get double charged if on weekend
                    if(isHoliday(date)) {
                        
                        continue;
                    }
                }
                if (toolCharges.getWeekdayCharge() == 1) {
                    if (dayOfWeek != "SATURDAY" && dayOfWeek != "SUNDAY") {
                        daysToCharge += 1;
                        continue;
                    } 
                }
                if (toolCharges.getWeekendCharge() == 1 ) {
                    if (dayOfWeek == "SATURDAY" || dayOfWeek == "SUNDAY") {
                        daysToCharge += 1;
                        continue;
                    } 
                }
            }

            System.out.println("Total days to charge " + daysToCharge);
            System.out.println("Total percent after discount " + discountCalculated);
            System.out.println("Total cost: " + daysToCharge * toolCharges.getDailyCharge());
            System.out.println("Total cost after discount : " + (daysToCharge * toolCharges.getDailyCharge()) *discountCalculated);
            return daysToCharge * toolCharges.getDailyCharge();
        } catch (ParseException ex) {
            return 0.0;
        }
    }
    public boolean isHoliday(LocalDate date) {
        if (date.getMonthValue() == 9) {
            System.out.println("september" + date);
        }
        if (date.getMonthValue() == 7 ) {
            System.out.println("july " + date);
        }
        return false;
    }
}

/*
 * ToolCharges.java
 * 7/5/2023
 * Ian Percy
 * 
 * Model for the ToolCharges object
 * 
 * Attributes: 
 * - id - int
 * - type_id - int
 * - daily_charge - decimal
 * - weekday_charge - int (true or false)
 * - weekend_charge - int (true or false)
 * - holiday_charge - int (true or false)
 */

 package com.models;

 public class ToolCharges {
    private int id;
    private int typeId;
    private double dailyCharge;
    private int weekdayCharge;
    private int weekendCharge;
    private int holidayCharge;


    public ToolCharges() {

    }

    @Override
    public String toString() {
        return String.format(
            "ToolCharges[id=%d, typeId=%d, dailyCharge=%d, weekdayCharge=%d, " +
            "weekendCharge=%d, holidayCharge=%d]",
            id, typeId, dailyCharge, weekdayCharge, weekendCharge, holidayCharge
        );
    }

    public int getId() {
        return id;
    }

    public void setTypeId (int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setDailyCharge(double dailyCharge) {
        this.dailyCharge = dailyCharge;
    }
    
    public double getDailyCharge() {
        return dailyCharge;
    }

    public void setWeekdayCharge(int weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public int getWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekendCharge(int weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public int getWeekendCharge() {
        return weekendCharge;
    }

    public void setHolidayCharge(int holidayCharge) {
        this.holidayCharge = holidayCharge;
    }

    public int getHolidayCharge() {
        return holidayCharge;
    }
 }
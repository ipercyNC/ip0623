/*
 * ToolCharges.java
 * 7/5/2023
 * Ian Percy
 * 
 * Model for the ToolCharges object
 * 
 * Attributes: 
 * - id - int
 * - type_id - int (maps to ToolType id)
 * - daily_charge - decimal
 * - weekday_charge - int (true or false)
 * - weekend_charge - int (true or false)
 * - holiday_charge - int (true or false)
 */

package com.models;

public class ToolCharges {
    private int id;
    private ToolType toolType;
    private double dailyCharge;
    private int weekdayCharge;
    private int weekendCharge;
    private int holidayCharge;

    public ToolCharges() {
    }

    public ToolCharges(int id, ToolType toolType, double dailyCharge,
            int weekdayCharge, int weekendCharge, int holidayCharge) {
        this.id = id;
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    @Override
    public String toString() {
        return String.format(
                "ToolCharges[id=%d, toolType='%s', dailyCharge=%10.2f, weekdayCharge=%d, " +
                        "weekendCharge=%d, holidayCharge=%d]",
                id, toolType.toString(), dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
    }

    public int getId() {
        return id;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public ToolType getToolType() {
        return toolType;
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
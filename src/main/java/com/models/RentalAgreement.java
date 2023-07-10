/*
 * RentalAgreement.java
 * 7/5/2023
 * Ian Percy
 * 
 * Model for the RentalAgreement object
 * 
 * Attributes: 
 * - id - int
 * - toolChoicesId - int (maps to ToolChoices id)
 * - rentalDays - int
 * - checkoutDate - date
 * - dueDate - date 
 * - toolChargesId - int (maps to ToolCharges id)
 * - prediscountCharge - double
 * - discountPercent - double
 * - discountAmount - double
 * - finalCharge - double
 */
package com.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentalAgreement {
    private int id;
    private ToolChoices toolChoices;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private ToolCharges toolCharges;
    private int chargeDays;
    private double prediscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement() {

    }

    public RentalAgreement(ToolChoices toolChoices, int rentalDays, LocalDate checkoutDate,
            LocalDate dueDate, ToolCharges toolCharges, int chargeDays, double prediscountCharge,
            int discountPercent, double discountAmount, double finalCharge) {
        this.toolChoices = toolChoices;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.toolCharges = toolCharges;
        this.chargeDays = chargeDays;
        this.prediscountCharge = prediscountCharge;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.finalCharge = finalCharge;
    }


    @Override
    public String toString() {
        return String.format(
                "RentalAgreement[id=%d, toolChoices='%s', rentalDays=%d, checkoutDate='%s', dueDate='%s', " +
                        "toolCharges='%s', chargeDays=%d, prediscountCharge=%.2f, discountPercent=%.2f, discountAmount=%.2f, finalCharge=%.2f",
                id, toolChoices.toString(), rentalDays, checkoutDate, dueDate, toolCharges.toString(), chargeDays,
                prediscountCharge, discountPercent, discountAmount, finalCharge);
    }

    public int getId() {
        return id;
    }

    public void setToolChoices(ToolChoices toolChoices) {
        this.toolChoices = toolChoices;
    }

    public ToolChoices getToolChoices() {
        return toolChoices;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setToolCharges(ToolCharges toolCharges) {
        this.toolCharges = toolCharges;
    }

    public ToolCharges getToolCharges() {
        return toolCharges;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public int getChargeDays(){
        return chargeDays;
    }

    public void setPrediscountCharge(double prediscountCharge) {
        this.prediscountCharge = prediscountCharge;
    }

    public double getPrediscountCharge() {
        return prediscountCharge;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setFinalCharge(double finalCharge) {
        this.finalCharge = finalCharge;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public String formatRentalAgreement() {
        List<String> agreementLines = new ArrayList<String>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        agreementLines.add("Tool Code           : " + toolChoices.getCode());
        agreementLines.add("Tool Type           : " + toolChoices.getToolType().getName());
        agreementLines.add("Tool Brand          : " + toolChoices.getToolBrand().getName());
        agreementLines.add("Rental Days         : " + rentalDays);
        agreementLines.add("Checkout Date      : " + checkoutDate.format(formatter));
        agreementLines.add("Due Date            : " + dueDate.format(formatter));
        agreementLines.add("Daily Rental Charge : $" + toolCharges.getDailyCharge());
        agreementLines.add("Charge Days         : " + chargeDays);
        agreementLines.add(String.format("Pre-discount Charge : $%.2f", prediscountCharge));
        agreementLines.add("Discount Percent    : " + discountPercent + "%");
        agreementLines.add(String.format("Discount Amount     : $%.2f", discountAmount));
        agreementLines.add(String.format("Final Charge        : $%.2f", finalCharge));
        return  String.join("\n", agreementLines);
    }
}

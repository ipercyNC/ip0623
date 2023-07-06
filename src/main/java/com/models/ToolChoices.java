/*
 * ToolChoices.java
 * 7/5/2023
 * Ian Percy
 * 
 * Model for the ToolChoices object
 * 
 * Attributes: 
 * - id - int
 * - code - String
 * - brand_id - int (maps to ToolBrand id)
 * - type_id - int (maps to ToolType id)
 */
package com.models;

public class ToolChoices {
    private int id;
    private String code;
    private ToolBrand toolBrand;
    private ToolType toolType;

    public ToolChoices() {

    }

    public ToolChoices(int id, String code, ToolBrand toolBrand, ToolType toolType) {
        this.id = id;
        this.code = code;
        this.toolBrand = toolBrand;
        this.toolType = toolType;
    }

    @Override
    public String toString() {
        return String.format(
            "ToolChoices[id=%d, code='%s', toolBrand='%s', toolType = '%s']",
            id, code, toolBrand.toString(), toolType.toString()
        );
    }

    public int getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setToolBrand(ToolBrand toolBrand) {
        this.toolBrand = toolBrand;
    }

    public ToolBrand getToolBrand() {
        return toolBrand;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public ToolType getToolType() {
        return toolType;
    }
}

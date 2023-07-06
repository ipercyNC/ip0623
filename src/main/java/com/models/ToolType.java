/*
 * ToolType.java
 * 7/4/2023
 * Ian Percy
 * 
 * Model for the ToolType object
 * 
 * Attributes: 
 * - id - int
 * - name - String
 */
package com.models;

public class ToolType {

    private int id;
    private String name;
    
    public ToolType() {
    }

    public ToolType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
            "ToolType[id=%d, name='%s']",
            id, name
        );
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
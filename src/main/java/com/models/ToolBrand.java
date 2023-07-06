/*
 * ToolBrand.java
 * 7/5/2023
 * Ian Percy
 * 
 * Model for the ToolBrand object
 * 
 * Attributes: 
 * - id - int
 * - name - String
 */
package com.models;

public class ToolBrand {
    private int id;
    private String name;

    public ToolBrand() {

    }

    public ToolBrand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
            "ToolBrand[id=%d, name='%s']",
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

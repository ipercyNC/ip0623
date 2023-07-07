/*
 * ToolChoicesMapper.java
 * 7/6/2023
 * Ian Percy
 * 
 * Maps a resultset to a ToolChoices object
 */
package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ToolChoicesMapper implements RowMapper<ToolChoices> {
    // TODO: Fix ToolType/ToolBrand to autowire in this class to find actual
    // ToolType/ToolBrand object, currently it is being set with actual values after
    // the the object is instantiated
    @Override
    public ToolChoices mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ToolChoices(
                rs.getInt("id"),
                rs.getString("code"),
                new ToolBrand(rs.getInt("brand_id"), null),
                new ToolType(rs.getInt("type_id"), null));

    }
}
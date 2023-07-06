package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ToolChargesMapper implements RowMapper<ToolCharges> {


    @Override
    public ToolCharges mapRow(ResultSet rs, int rowNum) throws SQLException {        
        return new ToolCharges(
                rs.getInt("id"),
                new ToolType(rs.getInt("type_id"), null),
                rs.getDouble("daily_charge"),
                rs.getInt("weekday_charge"),
                rs.getInt("weekend_charge"),
                rs.getInt("holiday_charge"));
    }
}

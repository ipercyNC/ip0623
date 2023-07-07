/*
 * JdbcToolChargesRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Jdbc implmentation of the ToolChargesRepository.
 * Allows specific queries on top of the ToolChargesRepository.
 */
package com.repositories;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.models.ToolCharges;
import com.models.ToolChargesMapper;

@Repository
public class JdbcToolChargesRepository implements ToolChargesRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcToolChargesRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * Returns all ToolCharges objects from the database
     * 
     * @return List of ToolCharges objects
     */
    @Override
    public List<ToolCharges> findAll() {
        // Map result of the SELECT to the default RowMapper for ToolCharges
        return jdbcTemplate.query("SELECT * FROM tool_charges",
                new ToolChargesMapper());
    }

    /*
     * Saves ToolCharge to database
     * 
     * @param typeId int of the new ToolCharge typeId
     * 
     * @return int number of rows saved
     */
    @Override
    public int save(int typeId) {
        try {
            // Insert into ToolCharges table and return the number of rows affected
            return jdbcTemplate.update(
                    "INSERT into tool_charges(type_id,daily_charge,weekday_charge,weekend_charge,holiday_charge) " +
                            " VALUES(?,0.0,0,0,0)",
                    typeId);
        } catch (Exception e) {
            logger.error("Error inserting into tool_charges table " + e);
            return -1;
        }
    }

    /*
     * Delete all ToolCharges objects from the database
     * 
     * @return number of affected rows
     */
    @Override
    public int deleteAll() {
        try {
            // Delete from ToolCharges table and return number of rows affected
            return jdbcTemplate.update("DELETE FROM tool_charges");
        } catch (DataAccessException e) {
            logger.error("Error deleting from tool_charges table " + e);
            return -1;
        }
    }

    /*
     * Returns a matching ToolCharges from the database if it exists
     * 
     * @param id int of id to search for in the database
     * 
     * @return ToolCharges object
     */
    @Override
    public ToolCharges findById(int id) {
        try {
            // Map result of the SELECT with id filter to the custom RowMapper for
            // ToolCharges
            ToolCharges toolCharges = jdbcTemplate.queryForObject("SELECT * FROM tool_charges WHERE id = ?",
                    new ToolChargesMapper(), id);
            return toolCharges;
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error with selecting by id from tool_charges " + e);
            return null;
        }
    }

    /*
     * Returns a matching ToolCharges from the database if it exists
     * 
     * @param typeId int to search for type_id in the database
     * 
     * @return ToolCharges object
     */
    @Override
    public ToolCharges findByTypeId(int typeId) {
        try {
            // Map result of the SELECT with id filter to the custom RowMapper for
            // ToolCharges
            ToolCharges toolCharges = jdbcTemplate.queryForObject("SELECT * FROM tool_charges WHERE type_id = ?",
                    new ToolChargesMapper(), typeId);
            return toolCharges;
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error with selecting by name from tool_charges " + e);
            return null;
        }
    }

}

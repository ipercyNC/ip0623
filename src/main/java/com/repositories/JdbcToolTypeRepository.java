/*
 * JdbcToolTypeRepository.java
 * 7/4/2023
 * Ian Percy
 * 
 * Jdbc implmentation of the ToolTypeRepository.
 * Allows specific queries on top of the ToolTypeRepository.
 */
package com.repositories;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.models.ToolType;

@Repository
public class JdbcToolTypeRepository implements ToolTypeRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcToolTypeRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * Returns all ToolType objects from the database
     * 
     * @return List of ToolType objects
     */
    @Override
    public List<ToolType> findAll() {
        // Map result of the SELECT to the default RowMapper for ToolType
        return jdbcTemplate.query("SELECT * FROM tool_type",
                BeanPropertyRowMapper.newInstance(ToolType.class));
    }

    /*
     * Saves ToolType to database
     * 
     * @param toolTypeName String of the new ToolType name
     * 
     * @return int number of rows saved
     */
    @Override
    public int save(String toolTypeName) {
        try {
            // Insert into ToolType table and return the number of rows affected
            return jdbcTemplate.update("INSERT INTO tool_type (name) VALUES(?)",
                    toolTypeName);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error inserting into tool_type " + e);
            return -1;
        }
    }

    /*
     * Delete all ToolType objects from the database
     * 
     * @return number of affected rows
     */
    @Override
    public int deleteAll() {
        try {
            // Delete from ToolType table and return number of rows affected
            return jdbcTemplate.update("DELETE FROM tool_type");
        } catch (DataAccessException e) {
            logger.error("Error deleting from tool_type " + e);
            return -1;
        }
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * 
     * @param id int for the id to search for in the database
     * 
     * @return ToolType object
     */
    @Override
    public ToolType findById(int id) {
        try {
            // Map result of the SELECT with id filter to the default RowMapper for
            // ToolType
            ToolType toolType = jdbcTemplate.queryForObject("SELECT * FROM tool_type WHERE id = ?",
                    BeanPropertyRowMapper.newInstance(ToolType.class), id);
            return toolType;
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error selecting by id from tool_type " + e);
            return null;
        }
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * 
     * @param toolTypeName String to search for in the database
     * 
     * @return ToolType object
     */
    @Override
    public ToolType findByName(String toolTypeName) {
        try {
            // Map result of the SELECT with name filter to the default RowMapper for
            // ToolType
            ToolType toolType = jdbcTemplate.queryForObject("SELECT * FROM tool_type where name = ?",
                    BeanPropertyRowMapper.newInstance(ToolType.class), toolTypeName);
            return toolType;
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error selecting by name from tool_type " + e);
            return null;
        }
    }

}

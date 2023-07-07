/*
 * JdbcToolChoicesRepository.java
 * 7/6/2023
 * Ian Percy
 * 
 * Jdbc implmentation of the ToolChoicesRepository.
 * Allows specific queries on top of the ToolChoicesRepository.
 */
package com.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.models.ToolChoices;
import com.models.ToolChoicesMapper;

@Repository
public class JdbcToolChoicesRepository implements ToolChoicesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * Returns all ToolChoices objects from the database
     * 
     * @return List of ToolChoices objects
     */
    @Override
    public List<ToolChoices> findAll() {
        // Map result of the SELECT to the default RowMapper for ToolChoices
        return jdbcTemplate.query("SELECT * FROM tool_choices", new ToolChoicesMapper());
    }

    /*
     * Saves ToolChoices to database
     * 
     * @param toolChoicesCode String of the new ToolChoices code
     * 
     * @param brandId int of the ToolBrand
     * 
     * @param typeId int of the ToolType
     * 
     * @return int number of rows saved
     */
    @Override
    public int save(String toolChoicesCode, int brandId, int typeId) {
        try {
            // Insert into ToolChoices table and return the number of rows affected
            return jdbcTemplate.update("INSERT into tool_choices(code, brand_id, type_id) " +
                    " VALUES(?,?,?)", toolChoicesCode, brandId, typeId);
        } catch (DataAccessException e) {
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    /*
     * Delete all ToolChoices objects from the database
     * 
     * @return number of affected rows
     */
    @Override
    public int deleteAll() {
        try {
            // Delete from ToolChoices table and return number of rows affected
            return jdbcTemplate.update("DELETE FROM tool_choices");
        } catch (DataAccessException e) {
            return -1;
        }
    }

    /*
     * Returns a matching ToolChoices from the database if it exists
     * 
     * @param id int for the id to search for in the database
     * 
     * @return ToolChoices object
     */
    @Override
    public ToolChoices findById(int id) {
        try {
            // Map result of the SELECT with id filter to the default RowMapper for
            // ToolChoices
            ToolChoices toolChoices = jdbcTemplate.queryForObject("SELECT * FROM tool_choices where id = ?",
                    new ToolChoicesMapper(), id);
            return toolChoices;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    /*
     * Returns a matching ToolChoices from the database if it exists
     * 
     * @param code String for tool code to search for in the database
     * 
     * @return ToolChoices object
     */
    @Override
    public ToolChoices findByCode(String code) {
        try {
            // Map result of the SELECT with name filter to the default RowMapper for
            // ToolChoices
            ToolChoices toolChoices = jdbcTemplate.queryForObject("SELECT * FROM tool_choices where code = ? ",
                    new ToolChoicesMapper(), code);
            return toolChoices;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}

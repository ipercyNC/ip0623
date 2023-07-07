/*
 * JdbcToolBrandRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Jdbc implmentation of the ToolBrandRepository.
 * Allows specific queries on top of the ToolBrandRepository.
 */
package com.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.models.ToolBrand;

@Repository
public class JdbcToolBrandRepository implements ToolBrandRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * Returns all ToolBrand objects from the database
     * 
     * @return List of ToolBrands
     */
    @Override
    public List<ToolBrand> findAll() {
        // Map result of the SELECT to the default RowMapper for ToolBrand
        return jdbcTemplate.query("SELECT * FROM tool_brand",
                BeanPropertyRowMapper.newInstance(ToolBrand.class));
    }

    /*
     * Saves ToolBrand to the database
     * 
     * @param toolBrandName String of the new ToolBrand name
     * 
     * @return int number of rows saved
     */
    @Override
    public int save(String toolBrandName) {
        try {
            // Insert into ToolBrand table and return the number of rows affected
            return jdbcTemplate.update("INSERT INTO tool_brand(name) VALUES(?)", toolBrandName);
        } catch (IncorrectResultSizeDataAccessException e) {
            return -1;
        }
    }

    /*
     * Delete all ToolBrand objects from the database
     * 
     * @return int number of affected rows
     */
    @Override
    public int deleteAll() {
        try {
            // Delete from ToolBrand table and return number of rows affected
            return jdbcTemplate.update("DELETE FROM tool_brand");
        } catch (DataAccessException e) {
            return -1;
        }
    }

    /*
     * Returns a matching ToolBrand from the database if it exists
     * 
     * @param id int to search for in the database
     * 
     * @return ToolBrand object
     */
    @Override
    public ToolBrand findById(int id) {
        try {
            // Map result of the SELECT with id filter to the default RowMapper for
            // ToolBrand
            ToolBrand toolBrand = jdbcTemplate.queryForObject("SELECT * FROM tool_brand WHERE id = ?",
                    BeanPropertyRowMapper.newInstance(ToolBrand.class), id);
            return toolBrand;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    /*
     * Returns a matching ToolBrand from the database if it exists
     * 
     * @param toolBrandName String to search for in the database
     * 
     * @return ToolBrand object
     */
    @Override
    public ToolBrand findByName(String toolBrandName) {
        try {
            // Map result of the SELECT with name filter to the default RowMapper for
            // ToolBrand
            ToolBrand toolBrand = jdbcTemplate.queryForObject("SELECT * FROM tool_brand WHERE name = ?",
                    BeanPropertyRowMapper.newInstance(ToolBrand.class), toolBrandName);
            return toolBrand;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}

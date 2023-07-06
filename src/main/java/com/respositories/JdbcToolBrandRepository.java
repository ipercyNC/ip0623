/*
 * JdbcToolBrandRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Jdbc implmentation of the ToolBrandRepository.
 * Allows specific queries on top of the ToolBrandRepository.
 */
package com.respositories;

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
     * @return List of ToolBrands
     */
    @Override
    public List<ToolBrand> findAll() {
        return jdbcTemplate.query("SELECT * FROM tool_brand", 
            BeanPropertyRowMapper.newInstance(ToolBrand.class));
    }

    /*
     * Saves ToolBrand to the database
     * @param String of the new ToolBrand name
     * @return int number of rows saved
     */
    @Override
    public int save(String toolBrandName) {
        try {
            return jdbcTemplate.update("INSERT INTO tool_brand(name) VALUES(?)", toolBrandName);
        } catch (IncorrectResultSizeDataAccessException e) {
            return -1;
        }
    }

    /*
     * Delete all ToolBrand objects from the database
     * @return int number of affected rows
     */
    @Override
    public int deleteAll() {
        try {
            return jdbcTemplate.update("DELETE FROM tool_brand");
        } catch (DataAccessException e) {
            return -1;
        }
    }

    /*
     * Returns a matching ToolBrand from the database if it exists
     * @param id to serach for in the database
     * @return ToolBrand object
     */
    @Override
    public ToolBrand findById(int id) {
        try {
            ToolBrand toolBrand = jdbcTemplate.queryForObject("SELECT * FROM tool_brand WHERE id = ?",
                BeanPropertyRowMapper.newInstance(ToolBrand.class), id);
            return toolBrand;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    /*
     * Returns a matching ToolBrand from the database if it exists
     * @param String to search for in the database
     * @return ToolBrand object
     */
    @Override
    public ToolBrand findByName(String toolBrandName) {
                try {
            ToolBrand toolBrand = jdbcTemplate.queryForObject("SELECT * FROM tool_brand WHERE name = ?",
                BeanPropertyRowMapper.newInstance(ToolBrand.class), toolBrandName);
            return toolBrand;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    
}

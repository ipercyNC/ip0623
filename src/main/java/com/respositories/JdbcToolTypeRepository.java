/*
 * JdbcToolTypeRepository.java
 * 7/4/2023
 * Ian Percy
 * 
 * Jdbc implmentation of the ToolTypeRepository.
 * Allows specific queries on top of the ToolTypeRepository.
 */
package com.respositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.models.ToolType;

@Repository
public class JdbcToolTypeRepository implements ToolTypeRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /*
     * Returns all ToolType objects from the database
     * @return List of ToolType objects
     */
    @Override
    public List<ToolType> findAll() {
        return jdbcTemplate.query("SELECT * FROM tool_type", 
            BeanPropertyRowMapper.newInstance(ToolType.class));
    }

    /*
     * Saves ToolType to database
     * @param String of the new ToolType name
     * @return int id of the saved ToolType
     */
    @Override
    public int save(String toolTypeName) {
        try {
            // ToolType toolType = jdbcTemplate.queryForObject("SELECT * FROM tool_type where name = ?",
            //     BeanPropertyRowMapper.newInstance(ToolType.class), toolTypeName);
            return jdbcTemplate.update("INSERT INTO tool_type (name) VALUES(?)",
                toolTypeName);
        } catch (IncorrectResultSizeDataAccessException e) {
            return -1;
        }
    }

    /*
     * Delete all ToolType objects from the database
     * @return null
     */
    @Override
    public int deleteAll() {
        try {
            return jdbcTemplate.update("DELETE FROM tool_type");
        } catch (DataAccessException e) {
            return -1;
        }
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * @param id to search for in the database
     * @return ToolType object
     */
    @Override
    public ToolType findById(int id) {
        try {
            ToolType toolType = jdbcTemplate.queryForObject("SELECT * FROM tool_type WHERE id = ?",
                BeanPropertyRowMapper.newInstance(ToolType.class), id);
            return toolType;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    
    /*
     * Returns a matching ToolType from the database if it exists
     * @param String to search for in the database
     * @return ToolType object
     */
    @Override
    public ToolType findByName(String toolTypeName) {
        try {
            ToolType toolType = jdbcTemplate.queryForObject("SELECT * FROM tool_type where name = ?",
                BeanPropertyRowMapper.newInstance(ToolType.class), toolTypeName);
            return toolType;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    
}

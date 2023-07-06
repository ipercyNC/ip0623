/*
 * JdbcToolChargesRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Jdbc implmentation of the ToolChargesRepository.
 * Allows specific queries on top of the ToolChargesRepository.
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
import com.models.ToolCharges;

@Repository
public class JdbcToolChargesRepository implements ToolChargesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * Returns all ToolCharges objects from the database
     * @return List of ToolCharges objects
     */
    @Override
    public List<ToolCharges> findAll() {
        return jdbcTemplate.query("SELECT * FROM tool_charges",
            BeanPropertyRowMapper.newInstance(ToolCharges.class));
    }

    /*
     * Saves ToolCharge to database
     * @param int of the new ToolCharge typeId
     * @return int number of rows saved
     */
    @Override
    public int save(int typeId) {
        try {
            return jdbcTemplate.update("INSERT into tool_charges(type_id,daily_charge,weekday_charge,weekend_charge,holiday_charge) "+
            " VALUES(?,0.0,0,0,0)", typeId);
        } catch (DataAccessException e) {
            return -1;
        
        }catch (Exception e) {
            return -1;
        }
    }

    /*
     * Delete all ToolCharges objects from the database
     * @return number of affected rows
     */
    @Override
    public int deleteAll() {
        try {
            return jdbcTemplate.update("DELETE FROM tool_charges");
        } catch (DataAccessException e) {
            return -1;
        }
    }

    /*
     * Returns a matching ToolCharges from the database if it exists
     * @param id to search for in the database
     * @return ToolCharges object
     */
    @Override
    public ToolCharges findById(int id) {
        try {
            ToolCharges toolCharges = jdbcTemplate.queryForObject("SELECT * FROM tool_charges WHERE id = ?",
                BeanPropertyRowMapper.newInstance(ToolCharges.class), id);
            return toolCharges;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    /*
     * Returns a matching ToolCharges from the database if it exists
     * @param int to search for type_id in the database
     * @return ToolCharges object
     */
    @Override
    public ToolCharges findByTypeId(int typeId) {
        try {
            ToolCharges toolCharges = jdbcTemplate.queryForObject("SELECT * FROM tool_charges WHERE type_id = ?",
                BeanPropertyRowMapper.newInstance(ToolCharges.class), typeId);
            return toolCharges;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    
}

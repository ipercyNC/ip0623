/*
 * ToolTypeService.java
 * 7/4/2023
 * Ian Percy
 * 
 * Service layer to interact with the ToolType repository. 
 */
package com.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.models.ToolType;
import com.repositories.ToolTypeRepository;

@Service
public class ToolTypeService {
    private static final Logger logger = LoggerFactory.getLogger(ToolTypeService.class);
    @Autowired
    ToolTypeRepository toolTypeRepository;

    /*
     * Returns all ToolType objects from the database.
     * Initiates a query to the database and maps the results
     * 
     * @return List of ToolType objects
     */

    public List<ToolType> findAllToolTypes() {
        try {
            // Get all ToolTypes from database and map to List
            List<ToolType> toolTypes = new ArrayList<ToolType>();
            toolTypeRepository.findAll().forEach(toolTypes::add);
            return toolTypes;
        } catch (Exception e) {
            logger.error("Error getting ToolTypes from database " + e);
            return new ArrayList<>();
        }
    }

    /*
     * Adds a ToolType to the database.
     * 
     * @param toolTypeName String of the ToolType name to add into the database
     * 
     * @return status of the insert success
     */
    public boolean createToolType(String toolTypeName) {
        try {
            // Save new Tooltype to database and return the result (number of rows
            // affected)
            int result = toolTypeRepository.save(toolTypeName);
            if (result > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Error creating ToolType " + e);
            return false;
        }
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * 
     * @param id int of id to search for in the database
     * 
     * @return ToolType object
     */
    public ToolType findToolTypeById(int id) {
        // Query ToolChoices table to find by specific id and return result
        ToolType toolType = toolTypeRepository.findById(id);
        return toolType;
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * 
     * @param name String of name to search for in the database
     * 
     * @return ToolType object
     */
    public ToolType findToolTypeByName(String name) {
        // Query ToolType table to find by specific name and return result
        ToolType toolType = toolTypeRepository.findByName(name);
        return toolType;
    }

    /*
     * Deletes all ToolTypes
     * 
     * @return int indicating success or failure of deletion
     */
    public int deleteAllToolTypes() {
        return toolTypeRepository.deleteAll();
    }
}

/*
 * ToolBrandService.java
 * 7/5/2023
 * Ian Percy
 * 
 * Service layer to interact with the ToolBrand repository. 
 */
package com.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.models.ToolBrand;
import com.repositories.ToolBrandRepository;

@Service
public class ToolBrandService {
    private static final Logger logger = LoggerFactory.getLogger(ToolBrandService.class);
    @Autowired
    ToolBrandRepository toolBrandRepository;

    /*
     * Returns all ToolBrand objects from the database.
     * Initiates a query to the database and maps the results
     * 
     * @return List of ToolBrand objects
     */
    public List<ToolBrand> findAllToolBrands() {
        try {
            // Get all ToolBrands from database and map to List
            List<ToolBrand> toolBrands = new ArrayList<ToolBrand>();
            toolBrandRepository.findAll().forEach(toolBrands::add);
            return toolBrands;
        } catch (Exception e) {
            logger.error("Error getting all ToolBrands from database " + e);
            return new ArrayList<>();
        }
    }

    /*
     * Adds a ToolBrand to the database
     * 
     * @param toolBrandName String of the ToolBrand name to add into the database
     * 
     * @return status of the insert success
     */
    public boolean createToolBrand(String toolBrandName) {
        try {
            // Save new ToolBrand to database and return the result (number of rows
            // affected)
            int result = toolBrandRepository.save(toolBrandName);
            if (result > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Error creating ToolBrand " + e);
            return false;
        }
    }

    /*
     * Returns a matching ToolBrand from the database if it exists
     * 
     * @param id int for id to search for in the database
     * 
     * @return ToolBrand object
     */
    public ToolBrand findToolBrandById(int id) {
        // Query ToolBrand table to find by specific id and return result
        ToolBrand toolBrand = toolBrandRepository.findById(id);
        return toolBrand;
    }

    /*
     * Returns a matching ToolBrand from the database if it exists
     * 
     * @param name String to search for in the database
     * 
     * @return ToolBrand object
     */
    public ToolBrand findToolBrandByName(String name) {
        // Query ToolBrand table to find by specific name and return result
        ToolBrand toolBrand = toolBrandRepository.findByName(name);
        return toolBrand;
    }

    /*
     * Deletes all ToolBrands
     * 
     * @return int indicating success or failure of deletion
     */
    public int deleteAllToolBrands() {
        return toolBrandRepository.deleteAll();
    }
}

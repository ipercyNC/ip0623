/*
 * ToolChargesService.java
 * 7/5/2023
 * Ian Percy
 * 
 * Service layer to interact with the ToolCharges repository. 
 */
package com.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.models.ToolCharges;
import com.repositories.ToolChargesRepository;
import com.repositories.ToolTypeRepository;

@Service
public class ToolChargesService {
    @Autowired
    ToolChargesRepository toolChargesRepository;

    @Autowired
    ToolTypeRepository toolTypeRepository;

    /*
     * Returns all ToolCharges objects from the database
     * Initiates a query to the database and maps the results
     * 
     * @return List of ToolCharges objects
     */
    public List<ToolCharges> findAllToolCharges() {
        try {
            // Get all ToolCharges from database and map to List
            List<ToolCharges> toolCharges = new ArrayList<ToolCharges>();
            toolChargesRepository.findAll().forEach(toolCharges::add);
            // Set the ToolType to the actual ToolType object
            toolCharges.forEach(tt -> {
                tt.setToolType(toolTypeRepository.findById(tt.getToolType().getId()));
            });
            return toolCharges;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /*
     * Adds a ToolCharge to the database
     * 
     * @param typeId int of the type id to add
     * 
     * @return status of the insert success
     */
    public boolean createToolCharge(int typeId) {
        try {
            // Save new ToolCharges to database and return the result (number of rows
            // affected)
            int result = toolChargesRepository.save(typeId);
            if (result > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Returns a matching ToolCharge from the database if it exists
     * 
     * @param id int of id to search for in the database
     * 
     * @return ToolCharge
     */
    public ToolCharges findToolChargesById(int id) {
        // Query ToolCharges table to find by specific id and return result
        ToolCharges toolCharges = toolChargesRepository.findById(id);
        // Set the ToolType to the actual ToolType object
        toolCharges.setToolType(toolTypeRepository.findById(toolCharges.getToolType().getId()));
        return toolCharges;
    }

    /*
     * Returns a matching ToolCharge from the database if it exists
     * 
     * @param typeId int of type id to search for in the database
     * 
     * @return ToolCharges
     */
    public ToolCharges findToolChargesByTypeId(int typeId) {
        // Query ToolCharges table to find by specific type id and return result
        ToolCharges toolCharges = toolChargesRepository.findByTypeId(typeId);
        // Set the ToolType to the actual ToolType object
        toolCharges.setToolType(toolTypeRepository.findById(toolCharges.getToolType().getId()));
        return toolCharges;
    }

    /*
     * Deletes all ToolCharges
     * 
     * @return int indicating success or failure of deletion
     */
    public int deleteAllToolCharges() {
        return toolChargesRepository.deleteAll();
    }
}

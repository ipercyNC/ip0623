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
import com.respositories.ToolChargesRepository;

@Service
public class ToolChargesService {
    @Autowired
    ToolChargesRepository toolChargesRepository;

    /*
     * Returns all ToolCharges objects from the database
     * Initiates a query to the database and maps the results
     * @return List of ToolChoices objects
     */
    public List<ToolCharges> findAllToolCharges() {
        try {
            List<ToolCharges> toolCharges = new ArrayList<ToolCharges>();
            toolChargesRepository.findAll().forEach(toolCharges::add);
            return toolCharges;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /*
     * Adds a ToolCharge to the database
     * @param int of the type id to add 
     * @return status of the insert success
     */
    public boolean createToolCharge(int typeId) {
        try {
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
     * @param int id to search for in the database
     * @return ToolCharge
     */
    public ToolCharges findToolChargesById(int id) {
        ToolCharges toolCharges = toolChargesRepository.findById(id);
        return toolCharges;
    }

    /*
     * Deletes all ToolCharges
     * @return int indicating success or failure of deletion
     */
    public int deleteAllToolCharges() {
        return toolChargesRepository.deleteAll();
    }
}

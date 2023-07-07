/*
 * ToolChoicesService.java
 * 7/6/2023
 * Ian Percy
 * 
 * Service layer to interact with the ToolChoices repository. 
 */
package com.services;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.models.ToolChoices;
import com.repositories.ToolBrandRepository;
import com.repositories.ToolChoicesRepository;
import com.repositories.ToolTypeRepository;

@Service
public class ToolChoicesService {
    @Autowired
    ToolChoicesRepository toolChoicesRepository;

    @Autowired
    ToolBrandRepository toolBrandRepository;

    @Autowired
    ToolTypeRepository toolTypeRepository;

    /*
     * Returns all ToolChoices from the database
     * Initiates a query to the database and maps the results 
     * @return List of ToolChoices objects
     */
    public List<ToolChoices> findAllToolChoices() {
        try {
            // Get all ToolChoices from database and map to List
            List<ToolChoices> toolChoices = new ArrayList<ToolChoices>();
            toolChoicesRepository.findAll().forEach(toolChoices::add);
            // Set the ToolType and ToolBrand to the actual objects
            toolChoices.forEach(tc -> {
                tc.setToolType(toolTypeRepository.findById(tc.getToolType().getId()));
                tc.setToolBrand(toolBrandRepository.findById(tc.getToolBrand().getId()));
            });
            return toolChoices;
        } catch (Exception e) {
            return new ArrayList<>();
        }
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
     * @return boolean success of creation
     */
    public boolean createToolChoice(String toolChoiceCode, int brandId, int typeId) {
        try {
            // Save new ToolChoices to database and return the result (number of rows
            // affected)
            int result = toolChoicesRepository.save(toolChoiceCode, brandId, typeId);
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
     * Returns a matching ToolChoice from the database if it exists
     * @param id int of id to search for in the database
     * @return ToolChoices
     */
    public ToolChoices findToolChoicesById(int id) {
        // Query ToolChoices table to find by specific id and return result
        ToolChoices toolChoices = toolChoicesRepository.findById(id);
        // Set the ToolType and ToolBrand to the actual objects
        toolChoices.setToolType(toolTypeRepository.findById(toolChoices.getToolType().getId()));
        toolChoices.setToolBrand(toolBrandRepository.findById(toolChoices.getToolBrand().getId()));
        return toolChoices;
    }

    /*
     * Returns a matching ToolChoices from the database if it exists
     * @param code String of code to search for in the database
     * @return ToolChoices
     */
    public ToolChoices findToolChoicesByCode(String code){
        // Query ToolChoices table to find by specific code and return result
        ToolChoices toolChoices = toolChoicesRepository.findByCode(code);
        // Set the ToolType and ToolBrand to the actual objects
        toolChoices.setToolType(toolTypeRepository.findById(toolChoices.getToolType().getId()));
        toolChoices.setToolBrand(toolBrandRepository.findById(toolChoices.getToolBrand().getId()));
        return toolChoices;
    }

    /*
     * Deletes all ToolChoices
     * @return int indiciating success or failure of deletion
     */
    public int deleteAllToolChoices() {
        return toolChoicesRepository.deleteAll();
    }
}

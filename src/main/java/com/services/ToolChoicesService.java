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
            List<ToolChoices> toolChoices = new ArrayList<ToolChoices>();
            toolChoicesRepository.findAll().forEach(toolChoices::add);
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
     * Adds a ToolChoice to the database
     * @param String of the type code to add
     * @return status of the insert success
     */
    public boolean createToolChoice(String toolChoiceCode, int brandId, int typeId) {
        try {
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
     * @param int id to search for in the database
     * @return ToolChoice
     */
    public ToolChoices findToolChoicesById(int id) {
        ToolChoices toolChoices = toolChoicesRepository.findById(id);
        toolChoices.setToolType(toolTypeRepository.findById(toolChoices.getToolType().getId()));
        toolChoices.setToolBrand(toolBrandRepository.findById(toolChoices.getToolBrand().getId()));
        return toolChoices;
    }

    /*
     * Returns a matching ToolChoice from the database if it exists
     * @param String code to search for in the database
     * @return ToolChoice
     */
    public ToolChoices findToolChoicesByCode(String code){
        ToolChoices toolChoices = toolChoicesRepository.findByCode(code);
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

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.models.ToolType;
import com.respositories.ToolTypeRepository;

@Service
public class ToolTypeService {
        @Autowired 
    ToolTypeRepository toolTypeRepository;

    /*
     * Returns all ToolType objects from the database. 
     * Initiates a query to the database and maps the results
     * @return List of ToolType objects
     */
    
    public List<ToolType> findAllToolTypes() {
        try {
            List<ToolType> toolTypes = new ArrayList<ToolType>();
            toolTypeRepository.findAll().forEach(toolTypes::add);
            return toolTypes;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /*
     * Adds a ToolType to the database. 
     * @param String of the ToolType name to add into the database
     * @return status of the insert success
     */
    public boolean createToolType(String toolTypeName) {
        System.out.println("Create tool " + toolTypeName);
        try {
            int result = toolTypeRepository.save(toolTypeName);
            System.out.println("here " + result);
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * @param int to search for in the database
     * @return ToolType objects
     */
    public ToolType findToolTypeById(int id) {
        ToolType toolType = toolTypeRepository.findById(id);
        return toolType;
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * @param String to search for in the database
     * @return ToolType object
     */
    public ToolType findToolTypeByName(String name) {
        ToolType toolType = toolTypeRepository.findByName(name);
        return toolType;
    }

    /*
     * Deletes all ToolTypes
     * @return int indicating success or failure of deletion
     */
    public int deleteAllToolTypes() {
        return toolTypeRepository.deleteAll();
    }
}

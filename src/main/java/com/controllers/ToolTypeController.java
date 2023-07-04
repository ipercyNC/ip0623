/*
 * ToolTypeController.java
 * 7/4/2023
 * Ian Percy
 * 
 * Controller for the ToolType objects. Implements the CRUD and other
 * endpoints for the ToolType model
 */
package com.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.RepaintManager;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.models.ToolType;
import com.respositories.ToolTypeRepository;
import com.services.ToolTypeService;

@RestController
@RequestMapping("/api")
public class ToolTypeController {
    @Autowired 
    ToolTypeService toolTypeService;

    /*
     * Returns all ToolType objects from the database. 
     * Gathers the ToolType results from the service layer
     * @return List of ToolType objects
     */
    @GetMapping("/toolType")
    public ResponseEntity<List<ToolType>> getAllToolTypes() {
        try {
            List<ToolType> toolTypes = toolTypeService.findAllToolTypes();
            if (toolTypes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<ToolType>>(toolTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Creates a new ToolType through the service layer
     * @param String of the ToolType name to add into the database
     * @return status of the insert success
     */
    @PostMapping("/toolType")
    public ResponseEntity<String> createToolType(@RequestBody ObjectNode objectNode) {
        try {
            boolean result = toolTypeService.createToolType(objectNode.get("name").asText());
            if (result) {
                return new ResponseEntity<String>("ToolType created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>("ToolType not created.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Returns a matching ToolType from the service layer if it exists
     * @param int to search for in the database
     * @return ToolType object
     */
    @GetMapping("/toolType/{id}")
    public ResponseEntity<ToolType> getToolTypeById(@PathVariable("id") int id) {
        ToolType toolType = toolTypeService.findToolTypeById(id);

        if (toolType != null) {
            return new ResponseEntity<>(toolType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Returns a matching ToolType from the database if it exists
     * @param String to search for in the database
     * @return ToolType object
     */
    @GetMapping("/toolType/{name}")
    public ResponseEntity<ToolType> getToolTypeByName(@PathVariable("name") String name) {
        ToolType toolType = toolTypeService.findToolTypeByName(name);

        if (toolType != null) {
            return new ResponseEntity<>(toolType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Initiate call to delete all ToolTypes from the database
     * @return status of the delete success
     */
    @DeleteMapping("/toolType")
    public ResponseEntity<String> deleteAllToolTypes() {
        try {
            int numRowsDeleted = toolTypeService.deleteAllToolTypes();
            return new ResponseEntity<>("Deleted " + numRowsDeleted + " ToolType(s) deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete ToolTypes.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
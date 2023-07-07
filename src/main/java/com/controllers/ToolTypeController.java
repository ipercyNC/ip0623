/*
 * ToolTypeController.java
 * 7/4/2023
 * Ian Percy
 * 
 * Controller for the ToolType objects. Implements the CRUD and other
 * endpoints for the ToolType model
 */
package com.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.models.ToolType;
import com.services.ToolTypeService;

@RestController
@RequestMapping("/api/toolType")
public class ToolTypeController {
    private static final Logger logger = LoggerFactory.getLogger(ToolTypeController.class);

    @Autowired
    ToolTypeService toolTypeService;

    /*
     * Returns all ToolType objects from the database.
     * Gathers the ToolType results from the service layer
     * 
     * @return List of ToolType objects
     */
    @GetMapping("")
    public ResponseEntity<List<ToolType>> getAllToolTypes() {
        try {
            // Call ToolTypeService to get all ToolTypes from the database
            List<ToolType> toolTypes = toolTypeService.findAllToolTypes();
            // If empty, return NO_CONTENT
            if (toolTypes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<ToolType>>(toolTypes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting all ToolTypes " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Creates a new ToolType through the service layer
     * 
     * @param objectNode object containing the ToolType name to add
     * 
     * @return status of the insert success
     */
    @PostMapping("")
    public ResponseEntity<String> createToolType(@RequestBody ObjectNode objectNode) {
        try {
            // Call ToolTypeService to create tool and return the result
            boolean result = toolTypeService.createToolType(objectNode.get("name").asText());
            if (result) {
                return new ResponseEntity<String>("ToolType created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>("ToolType not created.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            logger.error("Error creating ToolType " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Returns a matching ToolType from the service layer if it exists
     * 
     * @param id to search for in the database
     * 
     * @return ToolType object
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ToolType> getToolTypeById(@PathVariable("id") int id) {
        try {
            // Call ToolTypeService to find ToolType by id
            ToolType toolType = toolTypeService.findToolTypeById(id);
            return new ResponseEntity<ToolType>(toolType, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting ToolType by id" + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Returns a matching ToolType from the service layer if it exists
     * 
     * @param name to search for in the database
     * 
     * @return ToolType object
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ToolType> getToolTypeByName(@PathVariable("name") String name) {
        try {
            // Call ToolTypeService to find ToolType by id
            ToolType toolType = toolTypeService.findToolTypeByName(name);
            return new ResponseEntity<ToolType>(toolType, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting ToolType by name");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Initiate call to delete all ToolTypes from the database
     * 
     * @return status of the delete success
     */
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllToolTypes() {
        try {
            // Call ToolTypeService to delete ToolTypes and return number of rows deleted
            int numRowsDeleted = toolTypeService.deleteAllToolTypes();
            return new ResponseEntity<>("Deleted " + numRowsDeleted + " ToolType(s) deleted", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting ToolTypes " + e);
            return new ResponseEntity<>("Cannot delete ToolTypes.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
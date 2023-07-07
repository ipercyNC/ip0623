/*
 * ToolBrandController.java
 * 7/5/2023
 * Ian Percy
 * 
 * Controller for the ToolBrand objects. Implements the CRUD and other
 * endpoints for the ToolBrand model
 */
package com.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.models.ToolBrand;
import com.services.ToolBrandService;

@RestController
@RequestMapping("/api/toolBrand")
public class ToolBrandController {
    @Autowired
    ToolBrandService toolBrandService;

    /*
     * Returns all ToolBrand objects from the database
     * Gathers the ToolBrand objects from the service layer
     * 
     * @return List of ToolBrand objects
     */
    @GetMapping("")
    public ResponseEntity<List<ToolBrand>> getAllToolBrands() {
        try {
            // Get all ToolBrands
            List<ToolBrand> toolBrands = toolBrandService.findAllToolBrands();
            // If empty, return NO_CONTENT
            if (toolBrands.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<ToolBrand>>(toolBrands, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Creates a new ToolBrand through the service layer
     * 
     * @param objectNode String of the ToolBrand name to add into the database
     * 
     * @return status of the insert success
     */
    @PostMapping("")
    public ResponseEntity<String> createToolBrand(@RequestBody ObjectNode objectNode) {
        try {
            // Pass the name to the ToolBrandService to create ToolBrand
            boolean result = toolBrandService.createToolBrand(objectNode.get("name").asText());
            if (result) {
                return new ResponseEntity<String>("ToolBrand created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>("ToolBrand not created.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Returns a matching ToolBrand from the service layer if it exists
     * 
     * @param id to search for in the database
     * 
     * @return ToolBrand object
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ToolBrand> getToolBrandById(@PathVariable("id") int id) {
        try {
            // Call ToolBrandService to find ToolBrand by id
            ToolBrand toolBrand = toolBrandService.findToolBrandById(id);
            return new ResponseEntity<ToolBrand>(toolBrand, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Returns a matching ToolBrand from the service layer if it exists
     * 
     * @param name to search for in the database
     * 
     * @return ToolBrand object
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ToolBrand> getToolBrandByName(@PathVariable("name") String name) {
        try {
            // Call ToolBrandService to find ToolBrand by name
            ToolBrand toolBrand = toolBrandService.findToolBrandByName(name);
            return new ResponseEntity<ToolBrand>(toolBrand, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Indicate call to delete all ToolBrands from the database
     * 
     * @return status of the delete success
     */
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllToolBrands() {
        try {
            // Call to ToolBrandService to delete rows from table and return number of rows
            // Returns number of rows deleted
            int numRowsDeleted = toolBrandService.deleteAllToolBrands();
            return new ResponseEntity<>("Deleted " + numRowsDeleted + " ToolBrand(s) deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete ToolBrands.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

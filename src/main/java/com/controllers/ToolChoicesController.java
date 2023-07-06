/*
 * ToolChoicesController.java
 * 7/5/2023
 * Ian Percy
 * 
 * Controller for the ToolChoices objects. Implements the CRUD and other
 * endpoints for the ToolChoices model
 */
package com.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.models.ToolChoices;
import com.services.ToolChoicesService;

@RestController
@RequestMapping("/api/toolChoices")
public class ToolChoicesController {
    @Autowired
    ToolChoicesService toolChoicesService;

    /*
     * Returns all ToolChoices objects from the database
     * Gathers all ToolChoices from the service layer
     * @return List of ToolCharges objects
     */
    @GetMapping("")
    public ResponseEntity<List<ToolChoices>> getAllToolChoices() {
        try {
            List<ToolChoices> toolChoices = toolChoicesService.findAllToolChoices();
            if (toolChoices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<ToolChoices>>(toolChoices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Creates a new ToolChoice through the service layer
     * @param String of the tool code to add into the database
     * @return status of the insert success
     */
    @PostMapping("")
    public ResponseEntity<String> createToolChoices(@RequestBody ObjectNode objectNode) {
        try {
            boolean result = toolChoicesService.createToolChoice(
                        objectNode.get("code").asText(),
                        objectNode.get("brandId").asInt(),
                        objectNode.get("typeId").asInt());
            if (result) {
                return new ResponseEntity<String>("ToolChoices created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>("ToolChoices not created", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
     * Returns a matching ToolChoices from the service layer if it exists
     * @param int to search for in the database
     * @return ToolChoices object
     */
    @GetMapping("/{id}")
    public ResponseEntity<ToolChoices> getToolChoicesById(@PathVariable("id") int id) {
        try {
            ToolChoices toolChoices = toolChoicesService.findToolChoicesById(id);
            return new ResponseEntity<ToolChoices>(toolChoices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Indicate call to delete all ToolChoices from the database
     * @return status of the delete success
     */
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllToolChoices() {
        try {
            int numRowsDeleted = toolChoicesService.deleteAllToolChoices();
            return new ResponseEntity<String>("Deleted " + numRowsDeleted + " ToolChoices deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete ToolChoices.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

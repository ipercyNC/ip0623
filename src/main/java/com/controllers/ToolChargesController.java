/*
 * ToolChargesController.java
 * 7/5/2023
 * Ian Percy
 * 
 * Controller for the ToolCharges objects. Implements the CRUD and other
 * endpoints for the ToolCharges model
 */
package com.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.models.ToolCharges;
import com.services.ToolChargesService;

@RestController
@RequestMapping("/api/toolCharges")
public class ToolChargesController {
    @Autowired
    ToolChargesService toolChargesService;

    /*
     * Returns all ToolChoices objects from the database
     * Gathers all the ToolCharges objects from the service layer
     * @return List of ToolCharges objects
     */
    @GetMapping("")
    public ResponseEntity<List<ToolCharges>> getAllToolCharges() {
        try {
            List<ToolCharges> toolCharges = toolChargesService.findAllToolCharges();
            if (toolCharges.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<ToolCharges>>(toolCharges, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Creates a new ToolCharges through the service layer
     * @param String of the ToolBrand name to add into the database
     * @return status of the insert success
     */
    @PostMapping("")
    public ResponseEntity<String> createToolCharges(@RequestBody ObjectNode objectNode) {
        try {
            boolean result = toolChargesService.createToolCharge(objectNode.get("typeId").asInt());
            if (result) {
                return new ResponseEntity<String>("ToolCharges created successully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>("ToolCharges not created.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Returns a matching ToolCharges from the service layer if it exists
     * @param int to search for in the database
     * @return ToolCharges object
     */
    @GetMapping("/{id}")
    public ResponseEntity<ToolCharges> getToolChargesById(@PathVariable("id") int id) {
        try{
            ToolCharges toolCharges = toolChargesService.findToolChargesById(id);
            return new ResponseEntity<ToolCharges>(toolCharges, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Indicate call to delete all ToolCharges from the database
     * @return status of the delete success
     */
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllToolCharges() {
        try {
            int numRowsDeleted = toolChargesService.deleteAllToolCharges();
            return new ResponseEntity<>("Deleted " + numRowsDeleted + " ToolCharges deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete ToolCharges.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

/*
 * RentController.java
 * 7/6/2023
 * Ian Percy
 * 
 * Controller for the renting Tools. Interfaces with the RentService.
 * Handles the logic when users are wanting to rent tools and will generate
 * the rental agreements.
 */
package com.controllers;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.services.RentToolService;

@RestController
@RequestMapping("/api/rentTool")
public class RentToolController {
    @Autowired
    RentToolService rentToolService;

    /*
     * Returns a List of Strings representing the rental agreement
     * Accepts an object with the details of the tool rental and then generates the
     * agreement
     * 
     * @param objectNode object with the tool rental request
     * 
     * @return List of Strings with the rental agreement
     */
    @PostMapping("")
    public ResponseEntity<List<String>> rentTool(@RequestBody ObjectNode objectNode) {
        try {
            // Pass parameters to the RentToolService
            List<String> charges = rentToolService.rentTool(objectNode.get("code").asText(),
                    objectNode.get("startDate").asText(),
                    objectNode.get("days").asText(),
                    objectNode.get("discount").asText());

            return new ResponseEntity<List<String>>(charges, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
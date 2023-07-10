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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
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
    private static final Logger logger = LoggerFactory.getLogger(RentToolController.class);

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
    public ResponseEntity<String> rentTool(@RequestBody ObjectNode objectNode) {
        try {
            // Pass parameters to the RentToolService
            String charges = rentToolService.rentTool(objectNode.get("code").asText(),
                    objectNode.get("startDate").asText(),
                    objectNode.get("days").asText(),
                    objectNode.get("discount").asText());

            // Return error if percent is not in proper range
            if (charges == "ERROR_PERCENT_OUT_OF_RANGE") {
                return new ResponseEntity<String>("Please Give Percent That Is 0-100", HttpStatus.BAD_REQUEST);
            }

            // Return error if rental days is not valid
            if (charges == "ERROR_RENTAL_DAY_COUNT_OUT_OF_RANGE") {
                return new ResponseEntity<String>("Please Rental Day Count That Is 1 Day Or More", HttpStatus.BAD_REQUEST);
            }

            // Return error if ToolChoice not found
            if (charges == "ERROR_TOOL_CHOICE") {
                return new ResponseEntity<String>("Cannot Find Tool Choice, Please Try a Valid Tool Choice", HttpStatus.BAD_REQUEST);
            }

            // Return error if ToolCharge not found
            if (charges == "ERROR_TOOL_CHARGE") {
                return new ResponseEntity<String>("Cannot Find Tool Charges For The Chosen Tool", HttpStatus.BAD_REQUEST);
            }

            // Error in service call 
            if (charges == "ERROR_GENERAL") {
                return new ResponseEntity<String>("Tool Rental Failed, Please Try Again", HttpStatus.BAD_REQUEST);
            }
            
            return new ResponseEntity<String>(charges, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in rentTool() " + e);
            return new ResponseEntity<>("ERROR_RENTING_TOOL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
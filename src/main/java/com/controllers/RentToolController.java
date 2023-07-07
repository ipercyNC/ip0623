/*
 * RentController.java
 * 7/6/2023
 * Ian Percy
 * 
 * Controller for the renting Tools. Interfaces with the RentService
 */
package com.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.catalina.connector.Response;
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
import com.services.RentToolService;


@RestController
@RequestMapping("/api/rentTool")
public class RentToolController {
    @Autowired
    RentToolService rentToolService;
    
    @PostMapping("")
    public ResponseEntity<List<String>> rentTool(@RequestBody ObjectNode objectNode) {
        try {
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
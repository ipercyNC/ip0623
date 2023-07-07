/*
 * MainController.java
 * 7/4/2023
 * Ian Percy
 * 
 * Main Controller - handles the main entry point into the application. 
 */
package com.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    /*
     * General catch for the root entry point into the application
     */
    @GetMapping(path = "/")
    public @ResponseBody String getRoot() {
        logger.info("Main entrypoint called");
        return "Application Running";
    }
}

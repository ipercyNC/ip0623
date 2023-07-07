/*
 * MainController.java
 * 7/4/2023
 * Ian Percy
 * 
 * Main Controller - handles the main entry point into the application. 
 */
package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/")
public class MainController {
    /*
     * General catch for the root entry point into the application
     */
    @GetMapping(path = "/")
    public @ResponseBody String getRoot() {
        return "Application Running";
    }
}

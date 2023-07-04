/*
 * MainController.java
 * 7/4/2023
 * Ian Percy
 * 
 * Controller for the ToolType objects. This implements all calls that 
 * might be necessary from the front end of the application. 
 */
package com.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/")
public class MainController {

    @GetMapping(path="/")
    public @ResponseBody String getRoot() {
        return "Application Running";
    }
}

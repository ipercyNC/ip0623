/*
 * Application.java
 * 7/4/2023
 * Ian Percy
 * 
 * Main entrypoint for the application- Toolywood.
 * Toolywood allows for managing tools rentals using a simple api. 
 */
package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
@SpringBootApplication
public class Application {
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

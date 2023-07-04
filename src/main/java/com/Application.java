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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
 
@SpringBootApplication
@ComponentScan("com")
@EntityScan("com")
public class Application {
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

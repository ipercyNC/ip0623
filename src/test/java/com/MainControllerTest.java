/*
 * MainControllerTest.java
 * 7/4/2023
 * Ian Percy
 * 
 * Tests for the MainController
 */
package com;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainControllerTest {
    

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void smokeTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", 
            String.class)).contains("Application Running");
    }
}

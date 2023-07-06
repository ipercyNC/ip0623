/*
 * ToolChargesControllerTest.java
 * 7/5/2023
 * Ian Percy
 * 
 * Test for the ToolChargesController. Test the endpoints exposed for the 
 * ToolCharges api.
 */
package com;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Value;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jayway.jsonpath.JsonPath;
import com.services.ToolChargesService;
import com.services.ToolTypeService;

import org.json.JSONObject;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class ToolChargesControllerTest {
    @Autowired
    ToolChargesService toolChargesService;

    @Autowired
    ToolTypeService toolTypeService;

    @Autowired
    private MockMvc mvc;

    @Value(value="${local.server.port}")
    private int port;

    @BeforeEach
    public void clear() {
        toolTypeService.deleteAllToolTypes();
        toolChargesService.deleteAllToolCharges();
    }

    // Test creating one ToolCharges through controller
    @Test 
    public void createOneToolCharges() throws Exception {

        // Create ToolType first
        String newToolTypeName = "testToolType";
        JSONObject toolTypeJson = new JSONObject();
        toolTypeJson.put("name", newToolTypeName);
        mvc.perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(toolTypeJson.toString())).andExpect(status().isCreated());
        MvcResult toolTypeResult = mvc.perform(get("/api/toolType")).andReturn();
        String toolTypeResultAsString = toolTypeResult.getResponse().getContentAsString();
        assertThat(toolTypeResultAsString.contains(newToolTypeName));
        MvcResult toolTypeIdResult = mvc.perform(get("/api/toolType/" + newToolTypeName)).andExpect(status().isOk()).andReturn();
        String toolTypeIdResultString = toolTypeIdResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(toolTypeIdResultString);
        int newToolChargesId = Integer.parseInt(jsonObject.get("id").toString());

        JSONObject json = new JSONObject();
        json.put("typeId", newToolChargesId);
        mvc.perform(post("/api/toolCharges").contentType(MediaType.APPLICATION_JSON).content(json.toString())).andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolCharges")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.contains(String.valueOf(newToolChargesId)));
    }

    //Test gathering all ToolCharges when it's empty
    @Test
    public void gatherAllToolChargesEmpty() throws Exception {
        MvcResult result = mvc.perform(get("/api/toolCharges")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.isBlank());
    }
}

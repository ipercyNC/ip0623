/*
 * ToolChargesControllerTest.java
 * 7/5/2023
 * Ian Percy
 * 
 * Test for the ToolChargesController. Test the endpoints exposed for the 
 * ToolCharges api.
 * 
 * Tests:
 * 1) Get All ToolCharges - done
 * 2) Create ToolCharges - done
 * 3) Get ToolCharges by ID - done
 * 5) Delete ToolCharges - done
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.services.ToolChargesService;
import com.services.ToolTypeService;

import org.json.JSONArray;
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
    public void clearBefore() {
        toolChargesService.deleteAllToolCharges();
        toolTypeService.deleteAllToolTypes();
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
        assertTrue(toolTypeResultAsString.contains(newToolTypeName));
        MvcResult toolTypeIdResult = mvc.perform(get("/api/toolType/name/" + newToolTypeName)).andExpect(status().isOk()).andReturn();
        String toolTypeIdResultString = toolTypeIdResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(toolTypeIdResultString);
        int newToolChargesId = Integer.parseInt(jsonObject.get("id").toString());

        JSONObject json = new JSONObject();
        json.put("typeId", newToolChargesId);
        mvc.perform(post("/api/toolCharges").contentType(MediaType.APPLICATION_JSON).content(json.toString())).andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolCharges")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains(String.valueOf(newToolChargesId)));
    }

    
    // Test creating one ToolCharges through controller
    @Test 
    public void getToolChargesById() throws Exception {

        // Create ToolType first
        String newToolTypeName = "testToolType";
        JSONObject toolTypeJson = new JSONObject();
        toolTypeJson.put("name", newToolTypeName);
        
        mvc.perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(toolTypeJson.toString())).andExpect(status().isCreated());
        MvcResult toolTypeResult = mvc.perform(get("/api/toolType")).andReturn();
        String toolTypeResultAsString = toolTypeResult.getResponse().getContentAsString();
        assertTrue(toolTypeResultAsString.contains(newToolTypeName));
        MvcResult toolTypeIdResult = mvc.perform(get("/api/toolType/name/" + newToolTypeName)).andExpect(status().isOk()).andReturn();
        String toolTypeIdResultString = toolTypeIdResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(toolTypeIdResultString);
        int newToolChargesId = Integer.parseInt(jsonObject.get("id").toString());

        // Query 
        JSONObject json = new JSONObject();
        json.put("typeId", newToolChargesId);
        mvc.perform(post("/api/toolCharges").contentType(MediaType.APPLICATION_JSON).content(json.toString())).andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolCharges")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains(String.valueOf(newToolChargesId)));

        // Get the object from the get all query
        JSONArray toolChargesResultArray = new JSONArray(resultAsString);
        String firstToolCharges = toolChargesResultArray.get(0).toString();
        JSONObject firstToolChargesObject = new JSONObject(firstToolCharges);
        int retrievedToolChargesId = Integer.parseInt(firstToolChargesObject.get("id").toString());

        //Query for id
        MvcResult toolChargesFindById = mvc.perform(get("/api/toolCharges/id/" + retrievedToolChargesId)).andExpect(status().isOk()).andReturn();
        String toolChargesByIdString = toolChargesFindById.getResponse().getContentAsString();
        assertTrue(toolChargesByIdString.contains(Integer.toString(newToolChargesId)));
    }


    //Test gathering all ToolCharges when it's empty
    @Test
    public void gatherAllToolChargesEmpty() throws Exception {
        MvcResult result = mvc.perform(get("/api/toolCharges")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.isBlank());
    }

    @AfterAll
    public void clearAfter() {
        toolChargesService.deleteAllToolCharges();
        toolTypeService.deleteAllToolTypes();
    }

}

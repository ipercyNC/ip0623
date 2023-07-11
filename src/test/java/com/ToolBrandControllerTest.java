/*
 * ToolBrandControllerTest.java
 * 7/5/2023
 * Ian Percy
 * 
 * Test for the ToolBrandController. Test the endpoints exposed for the 
 * ToolBrand api.
 * 
 * Tests:
 * 1) Get All ToolBrands - done
 * 2) Create ToolBrand - done
 * 3) Get ToolBrand by ID - done
 * 4) Get ToolBrand by name - done
 * 5) Delete ToolBrands - done
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

import org.json.JSONArray;

import com.services.ToolBrandService;
import org.json.JSONObject;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class ToolBrandControllerTest {
    @Autowired
    ToolBrandService toolBrandService;

    @Autowired
    private MockMvc mvc;

    @Value(value = "${local.server.port}")
    private int port;

    @BeforeEach
    public void clearBefore() {
        toolBrandService.deleteAllToolBrands();
    }

    // Test creating one ToolBrand through controller
    @Test
    public void createOneToolBrand() throws Exception {
        String newToolBrandName = "testToolBrand";
        JSONObject json = new JSONObject();
        json.put("name", newToolBrandName);
        mvc.perform(post("/api/toolBrand").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolBrand")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains(newToolBrandName));
    }

    // Test creating and query by ID
    @Test
    public void queryToolBrandById() throws Exception {
        String newToolBrandName = "testToolBrand";
        JSONObject json = new JSONObject();
        json.put("name", newToolBrandName);
        mvc.perform(post("/api/toolBrand").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolBrand")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains(newToolBrandName));

        // Get ID of returned object
        JSONArray toolBrandResultArray = new JSONArray(resultAsString);
        String firstToolBrand = toolBrandResultArray.get(0).toString();
        assertTrue(firstToolBrand.contains(newToolBrandName));
        // Get id from the return
        JSONObject jsonObject = new JSONObject(firstToolBrand);
        int retrievedToolBrandId = Integer.parseInt(jsonObject.get("id").toString());

        // Query by id
        MvcResult toolBrandByIdResult = mvc.perform(get("/api/toolBrand/id/" + retrievedToolBrandId))
                .andExpect(status().isOk())
                .andReturn();
        String toolBrandByIdResultString = toolBrandByIdResult.getResponse().getContentAsString();
        // Check name matches what we put in as the test name
        assertTrue(toolBrandByIdResultString.contains(newToolBrandName));
    }

    // Test creating and query by ID
    @Test
    public void queryToolBrandByName() throws Exception {
        String newToolBrandName = "testToolBrand";
        JSONObject json = new JSONObject();
        json.put("name", newToolBrandName);
        mvc.perform(post("/api/toolBrand").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolBrand")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains(newToolBrandName));

        // Get ID of returned object
        JSONArray toolBrandResultArray = new JSONArray(resultAsString);
        String firstToolBrand = toolBrandResultArray.get(0).toString();
        assertTrue(firstToolBrand.contains(newToolBrandName));
        // Get id from the return
        JSONObject jsonObject = new JSONObject(firstToolBrand);
        String retrievedToolBrandName = jsonObject.get("name").toString();

        // Query by id
        MvcResult toolBrandByNameResult = mvc.perform(get("/api/toolBrand/name/" + retrievedToolBrandName))
                .andExpect(status().isOk())
                .andReturn();
        String toolBrandByNameResultString = toolBrandByNameResult.getResponse().getContentAsString();
        // Check name matches what we put in as the test name
        assertTrue(toolBrandByNameResultString.contains(newToolBrandName));
    }

    // Test creating and deleting
    @Test
    public void createAndDeleteAll() throws Exception {
        String newToolBrandName = "testToolBrand";
        JSONObject json = new JSONObject();
        json.put("name", newToolBrandName);
        mvc.perform(post("/api/toolBrand").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolBrand")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains(newToolBrandName));

        // Delete and check result is empty
        mvc.perform(delete("/api/toolBrand")).andReturn();
        MvcResult toolBrandResult = mvc.perform(get("/api/toolBrand")).andReturn();
        String toolBrandResultAsString = toolBrandResult.getResponse().getContentAsString();
        assertTrue(toolBrandResultAsString.isBlank());
    }

    // Test gathering all ToolBrands when it's empty
    @Test
    public void gatherAllToolBrandsEmpty() throws Exception {
        MvcResult result = mvc.perform(get("/api/toolBrand")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.isBlank());
    }

    @AfterAll
    public void clearAfter() {
        toolBrandService.deleteAllToolBrands();
    }

}

/*
 * ToolTypeControllerTest.java
 * 7/4/2023
 * Ian Percy
 * 
 * Test for the ToolTypeController. Test the endpoints exposed for the 
 * ToolType api.
 * 
 * 1) Get All ToolTypes - done
 * 2) Create ToolType - done
 * 3) Get ToolType by ID - done
 * 4) Get ToolType by name - done
 * 5) Delete ToolTypes - done
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.services.ToolTypeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class ToolTypeControllerTest {
    @Autowired
    ToolTypeService toolTypeService;

    @Autowired
    private MockMvc mvc;

    @Value(value = "${local.server.port}")
    private int port;

    @BeforeEach
    public void clear() {
        toolTypeService.deleteAllToolTypes();
    }

    @Test
    public void createAndGetAll() throws Exception {
        // Create ToolType
        String newToolTypeName = "testToolType";
        JSONObject json = new JSONObject();
        json.put("name", newToolTypeName);
        MvcResult createResult = mvc
                .perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated()).andReturn();
        // Check the response has the new ToolType name
        String createResultAsString = createResult.getResponse().getContentAsString();
        assertThat(createResultAsString.contains(newToolTypeName));

        // Get all ToolTypes and check that the new ToolType name is in the response
        MvcResult getAllResult = mvc.perform(get("/api/toolType")).andExpect(status().isOk()).andReturn();
        String getAllResultAsString = getAllResult.getResponse().getContentAsString();
        assertThat(getAllResultAsString.contains(newToolTypeName));
    }

    // Test creating one ToolType through controller
    @Test
    public void createToolType() throws Exception {
        String newToolTypeName = "testToolType";
        JSONObject json = new JSONObject();
        json.put("name", newToolTypeName);
        MvcResult result = mvc
                .perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated()).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.contains(newToolTypeName));
    }

    // Get ToolType by id
    @Test
    public void getToolTypeById() throws Exception {
        // Create ToolType first
        String newToolTypeName = "testToolType";
        JSONObject toolTypeJson = new JSONObject();
        toolTypeJson.put("name", newToolTypeName);
        mvc.perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(toolTypeJson.toString()))
                .andExpect(status().isCreated());
        MvcResult toolTypeResult = mvc.perform(get("/api/toolType")).andReturn();
        String toolTypeResultAsString = toolTypeResult.getResponse().getContentAsString();
        JSONArray toolTypeResultArray = new JSONArray(toolTypeResultAsString);
        String firstToolType = toolTypeResultArray.get(0).toString();
        assertThat(firstToolType.contains(newToolTypeName));
        // Get id from the return
        JSONObject jsonObject = new JSONObject(firstToolType);
        int retrievedToolTypeId = Integer.parseInt(jsonObject.get("id").toString());

        // Query by id
        MvcResult toolTypeIdResult = mvc.perform(get("/api/toolType/id/" + retrievedToolTypeId))
                .andExpect(status().isOk())
                .andReturn();
        String toolTypeIdResultString = toolTypeIdResult.getResponse().getContentAsString();
        // Check name matches what we put in as the test name
        assertThat(toolTypeIdResultString.contains(newToolTypeName));
    }

    // Get ToolType by name
    @Test
    public void getToolTypeByName() throws Exception {
        // Create ToolType first
        String newToolTypeName = "testToolType";
        JSONObject toolTypeJson = new JSONObject();
        toolTypeJson.put("name", newToolTypeName);
        mvc.perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(toolTypeJson.toString()))
                .andExpect(status().isCreated());
        MvcResult toolTypeResult = mvc.perform(get("/api/toolType")).andReturn();
        String toolTypeResultAsString = toolTypeResult.getResponse().getContentAsString();
        assertThat(toolTypeResultAsString.contains(newToolTypeName));
        MvcResult toolTypeIdResult = mvc.perform(get("/api/toolType/name/" + newToolTypeName))
                .andExpect(status().isOk())
                .andReturn();
        String toolTypeNameResultString = toolTypeIdResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(toolTypeNameResultString);
        String retrievedToolTypeName = jsonObject.get("name").toString();
        assertEquals(newToolTypeName, retrievedToolTypeName);
    }

    // Test gathering all ToolTypes when it's empty
    @Test
    public void gatherAllToolTypesEmpty() throws Exception {
        MvcResult result = mvc.perform(get("/api/toolType")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.isBlank());
    }

    // Test creating and deleting ToolTypes
    @Test
    public void createAndDeleteAll() throws Exception {
        // Create ToolType
        String newToolTypeName = "testToolType";
        JSONObject json = new JSONObject();
        json.put("name", newToolTypeName);
        MvcResult result = mvc
                .perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated()).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.contains(newToolTypeName));

        // Delete and check result is empty
        mvc.perform(delete("/api/toolType")).andReturn();
        MvcResult toolTypeResult = mvc.perform(get("/api/toolType")).andReturn();
        String toolTypeResultAsString = toolTypeResult.getResponse().getContentAsString();
        assertThat(toolTypeResultAsString.isBlank());
    }
}

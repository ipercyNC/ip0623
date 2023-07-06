/*
 * ToolChoicesControllerTest.java
 * 7/5/2023
 * Ian Percy
 * 
 * Test for the ToolChoicesController. Test the endpoints exposed for the 
 * ToolChoices api.
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
import com.services.ToolChoicesService;
import com.services.ToolTypeService;
import com.services.ToolBrandService;

import org.json.JSONObject;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class ToolChoicesControllerTest {
    @Autowired
    ToolChoicesService toolChoicesService;

    @Autowired
    ToolTypeService toolTypeService;

    @Autowired
    ToolBrandService toolBrandService;

    @Autowired
    private MockMvc mvc;

    @Value(value="${local.server.port}")
    private int port;

    @BeforeEach
    public void clear() {
        toolBrandService.deleteAllToolBrands();
        toolChoicesService.deleteAllToolChoices();
        toolTypeService.deleteAllToolTypes();
    }

    //Test creating one ToolChoices through controller
    @Test
    public void createOneToolChoices() throws Exception {
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
        int newToolTypeChoicesId = Integer.parseInt(jsonObject.get("id").toString());

        // Create ToolBrand
        String newToolBrandName = "testToolBrand";
        JSONObject toolBrandJson = new JSONObject();
        toolBrandJson.put("name", newToolBrandName);
        mvc.perform(post("/api/toolBrand").contentType(MediaType.APPLICATION_JSON).content(toolBrandJson.toString())).andExpect(status().isCreated());
        MvcResult toolBrandResult = mvc.perform(get("/api/toolBrand")).andReturn();
        String toolBrandResultAsString = toolBrandResult.getResponse().getContentAsString();
        assertThat(toolBrandResultAsString.contains(newToolBrandName));
        MvcResult toolBrandIdResult = mvc.perform(get("/api/toolBrand/" + newToolBrandName)).andExpect(status().isOk()).andReturn();
        String toolBrandIdResultString = toolBrandIdResult.getResponse().getContentAsString();
        JSONObject toolBrandJsonObject = new JSONObject(toolBrandIdResultString);
        int newToolBrandChoicesId = Integer.parseInt(toolBrandJsonObject.get("id").toString());

        //Create ToolChoices
        String newToolCode = "testToolCode";
        JSONObject toolChoiceJson = new JSONObject();
        toolChoiceJson.put("code", newToolCode);
        toolChoiceJson.put("brandId", newToolBrandChoicesId);
        toolChoiceJson.put("typeId", newToolTypeChoicesId);
        mvc.perform(post("/api/toolChoices").contentType(MediaType.APPLICATION_JSON).content(toolChoiceJson.toString())).andExpect(status().isCreated());
        MvcResult toolChoicesResult = mvc.perform(get("/api/toolChoices")).andReturn();
        String toolChoicesResultAsString = toolChoicesResult.getResponse().getContentAsString();
        assertThat(toolChoicesResultAsString.contains(newToolCode));
    }
    @Test
    public void gatherAllToolChoicesEmpty() throws Exception {
        MvcResult result = mvc.perform(get("/api/toolChoices")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.isBlank());
    }
}

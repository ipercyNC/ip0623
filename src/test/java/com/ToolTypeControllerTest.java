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
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc 
public class ToolTypeControllerTest {
    @Autowired
    ToolTypeService toolTypeService;

    @Autowired
    private MockMvc mvc;

    @Value(value="${local.server.port}")
    private int port;


    @BeforeEach
    public void clear() {
        toolTypeService.deleteAllToolTypes();
    }

    // Test creating one ToolType through controller
    @Test
    public void createOneToolType() throws Exception {
        String newToolTypeName = "testToolType";
        JSONObject json = new JSONObject();
        json.put("name", newToolTypeName);
        mvc.perform(post("/api/toolType").contentType(MediaType.APPLICATION_JSON).content(json.toString())).andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolType")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.contains(newToolTypeName));
        Thread.sleep(10000);
    }

    //Test gathering all ToolTypes when it's empty
    @Test
    public void gatherAllToolTypesEmpty() throws Exception {
        MvcResult result = mvc.perform(get("/api/toolType")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        System.out.println(resultAsString);
        assertThat(resultAsString.isEmpty());
    }
}

/*
 * ToolBrandControllerTest.java
 * 7/5/2023
 * Ian Percy
 * 
 * Test for the ToolBrandController. Test the endpoints exposed for the 
 * ToolBrand api.
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
import com.services.ToolBrandService;
import net.minidev.json.JSONObject;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class ToolBrandControllerTest {
    @Autowired
    ToolBrandService toolBrandService;

    @Autowired
    private MockMvc mvc;

    @Value(value="${local.server.port}")
    private int port;

    @BeforeEach
    public void clear() {
        toolBrandService.deleteAllToolBrands();
    }

    // Test creating one ToolBrand through controller
    @Test
    public void createOneToolBrand() throws Exception {
        String newToolBrandName = "testToolBrand";
        JSONObject json = new JSONObject();
        json.put("name", newToolBrandName);
        mvc.perform(post("/api/toolBrand").contentType(MediaType.APPLICATION_JSON).content(json.toString())).andExpect(status().isCreated());
        MvcResult result = mvc.perform(get("/api/toolBrand")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.contains(newToolBrandName));
    }
    //Test gathering all ToolBrands when it's empty
    @Test
    public void gatherAllToolBrandsEmpty() throws Exception {
        MvcResult result = mvc.perform(get("/api/toolBrand")).andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        assertThat(resultAsString.isBlank());
    }

}

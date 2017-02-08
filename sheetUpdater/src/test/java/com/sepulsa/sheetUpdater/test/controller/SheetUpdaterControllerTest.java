package com.sepulsa.sheetUpdater.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.sepulsa.sheetUpdater.controller.SheetUpdaterController;

@RunWith(SpringRunner.class)
@WebMvcTest(SheetUpdaterController.class)
public class SheetUpdaterControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
	
    
    @Test
    public void contextLoads() {
    	
    }

    @Test
    public void testSheetUpdate() throws Exception {
        ResultActions result = this.mockMvc.perform(get("/updateSheet"));
        //result.accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
        result.andExpect(status().isOk());
        //result.andExpect(content().contentType("application/json"));

    }
    
    @After
    public void tearDown() {
    	this.mockMvc = null;
    }
}

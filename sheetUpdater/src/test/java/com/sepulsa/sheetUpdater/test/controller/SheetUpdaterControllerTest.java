package com.sepulsa.sheetUpdater.test.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sepulsa.sheetUpdater.SheetUpdaterApplication;
import com.sepulsa.sheetUpdater.configuration.RepositoryConfiguration;
import com.sepulsa.sheetUpdater.controller.SheetUpdaterController;
import com.sepulsa.sheetUpdater.entity.Sheet;
import com.sepulsa.sheetUpdater.repository.SheetRepository;
import com.sepulsa.sheetUpdater.service.SheetService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure=false)
@ComponentScan("com.sepulsa")
public class SheetUpdaterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SheetService sheetService;

    @Test
    public void testCallback() throws Exception {
        ResultActions result = this.mockMvc.perform(get("/Callback"));
        result.andExpect(status().isOk());

    }
    
    @Test
    public void testWebHookListener() throws Exception {
        ResultActions result = this.mockMvc.perform(post("/webHookListener")
        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")));
        result.andExpect(status().isOk());
        result.andExpect(content().contentType("application/json"));
    }
    
    @After
    public void tearDown() {
    	this.mockMvc = null;
    }
}

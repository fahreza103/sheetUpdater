package com.sepulsa.sheetUpdater.test.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.sepulsa.sheetUpdater.constant.AppConstant;
import com.sepulsa.sheetUpdater.object.ApiResponse;
import com.sepulsa.sheetUpdater.object.WebHook;
import com.sepulsa.sheetUpdater.service.JsonService;
import com.sepulsa.sheetUpdater.service.SheetService;
import com.sepulsa.sheetUpdater.util.FileTool;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure=false)
@ComponentScan("com.sepulsa")
public class SheetUpdaterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JsonService jsonService;
	
	@MockBean
	private SheetService sheetService;
	
	public WebHook webHook;
	public String jsonRequest;
	public String jsonSheetMapping;
	
	@Before
	public void setUp() {
		this.jsonRequest = FileTool.getStrFileContent("dummyRequest.json");
		this.jsonSheetMapping = FileTool.getStrFileContent("sheetMapping.json");
		
		WebHook webHook = jsonService.convertToObject(jsonRequest, WebHook.class);
		this.webHook = webHook;
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull(mockMvc);
		assertNotNull(sheetService);
	}

    @Test
    public void testCallback() throws Exception {
        ResultActions result = this.mockMvc.perform(get("/Callback"));
        result.andExpect(status().isOk());

    }
    
    @Test
    public void testWebHookListenerAddStory() throws Exception {
    	executeAddUpdate(jsonRequest);
    }
    
    @Test
    public void testWebHookListenerUpdateStory() throws Exception {
    	String jsonRequestUpdate = jsonRequest.replace(AppConstant.ACTIVITY_CREATE,AppConstant.ACTIVITY_UPDATE);
    	executeAddUpdate(jsonRequestUpdate);
    }
    
    public void executeAddUpdate(String jsonRequest) throws Exception {
        ResultActions result = this.mockMvc.perform(post("/webHookListener")
        .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonRequest));
        
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(sheetService).getCurrentSheetDefinition(argument.capture());
        assertNotNull(argument.getValue());
        
        ApiResponse apiResponse = new ApiResponse();
        when(sheetService.addUpdateStory(any(), any())).thenReturn(apiResponse);
        doReturn(apiResponse).when(sheetService).addUpdateStory(any(), any());
        verify(sheetService,times(1)).addUpdateStory(any(),any());

        
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        result.andReturn();
    }
    
    @Test
    public void testWebHookListenerMoveStory() throws Exception {
    	String jsonRequestMove = jsonRequest.replace(AppConstant.ACTIVITY_CREATE,AppConstant.ACTIVITY_MOVE);
    	
        ResultActions result = this.mockMvc.perform(post("/webHookListener")
        .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonRequestMove));
        
        ApiResponse apiResponse = new ApiResponse();
        when(sheetService.moveStory(any(), any())).thenReturn(apiResponse);
        doReturn(apiResponse).when(sheetService).addUpdateStory(any(), any());
        verify(sheetService,times(1)).moveStory(any(), any());
        
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        result.andReturn();
    }
    
    @After
    public void tearDown() {
    	this.mockMvc = null;
    	this.jsonRequest = null;
    	this.jsonService = null;
    	this.sheetService = null;
    }
}

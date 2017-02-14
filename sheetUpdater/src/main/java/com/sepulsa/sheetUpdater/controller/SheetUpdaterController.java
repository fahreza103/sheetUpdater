package com.sepulsa.sheetUpdater.controller;

import java.io.File;
import java.io.IOException;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sepulsa.sheetUpdater.Object.SheetDefinition;
import com.sepulsa.sheetUpdater.Object.WebHook;
import com.sepulsa.sheetUpdater.Service.JsonService;
import com.sepulsa.sheetUpdater.Service.SheetService;
import com.sepulsa.sheetUpdater.util.FileTool;

@RestController
@EnableAutoConfiguration
public class SheetUpdaterController {

	Logger log = Logger.getLogger(SheetUpdaterController.class);
	
    private static final String SHEET_MAPPING_FILE = "sheetMapping.json";
	private static final String ACTIVITY_CREATE = "story_create_activity";
	private static final String ACTIVITY_MOVE = "story_move_activity";
	private static final String ACTIVITY_UPDATE = "story_update_activity";
	
	@Autowired
	private SheetService sheetService;
	@Autowired
	private JsonService jsonService;
	
	
	@RequestMapping("/Callback")
	public String callBack () throws IOException {
		return "OK";
	}
	
	@RequestMapping(value = "/webHookListener")
	public String webHookListener (@RequestBody String json) throws IOException {
		// Convert to JSON from string requestBody
		WebHook webHook = jsonService.convertToObject(json,WebHook.class);
		// Read sheetMapping.json (mapping column configuration)
		String sheetMappingJson = FileTool.getStrFileContent(SHEET_MAPPING_FILE);
		// Convert to JSON
		SheetDefinition sheetDefinition = jsonService.convertToObject(sheetMappingJson,SheetDefinition.class);
		
		// Get kind (activity by user in pivotal)
		String kind = webHook.getKind();
		
		if(ACTIVITY_CREATE.equals(kind)) {
			sheetService.addStory(webHook,sheetDefinition);
		} else if (ACTIVITY_MOVE.equals(kind)) {
			sheetService.moveStory(webHook);
		} else if (ACTIVITY_UPDATE.equals(kind)) {
			sheetService.updateStory(webHook);
		}
	  
		return json;
	}

}

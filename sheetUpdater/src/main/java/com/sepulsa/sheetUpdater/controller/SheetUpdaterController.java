package com.sepulsa.sheetUpdater.controller;

import java.io.IOException;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sepulsa.sheetUpdater.Object.WebHook;
import com.sepulsa.sheetUpdater.Service.JsonService;
import com.sepulsa.sheetUpdater.Service.SheetService;

@RestController
@EnableAutoConfiguration
public class SheetUpdaterController {

	Logger log = Logger.getLogger(SheetUpdaterController.class);
	
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
		log.info("JSON String : "+json);
		WebHook webHook = jsonService.convertToObject(json);
		
		// Get kind (activity by user in pivotal)
		String kind = webHook.getKind();
		
		if(ACTIVITY_CREATE.equals(kind)) {
			sheetService.addStory(webHook);
		} else if (ACTIVITY_MOVE.equals(kind)) {
			sheetService.moveStory(webHook);
		} else if (ACTIVITY_UPDATE.equals(kind)) {
			sheetService.updateStory(webHook);
		}
	  
		return json;
	}

}

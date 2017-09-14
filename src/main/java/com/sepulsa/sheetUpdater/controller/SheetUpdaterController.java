package com.sepulsa.sheetUpdater.controller;

import java.io.IOException;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sepulsa.sheetUpdater.constant.AppConstant;
import com.sepulsa.sheetUpdater.object.ApiResponse;
import com.sepulsa.sheetUpdater.object.GlobalResponse;
import com.sepulsa.sheetUpdater.object.SheetDefinition;
import com.sepulsa.sheetUpdater.object.WebHook;
import com.sepulsa.sheetUpdater.service.JsonService;
import com.sepulsa.sheetUpdater.service.SheetService;
import com.sepulsa.sheetUpdater.util.FileTool;

/**
 * Controller for endpoint, listen to webhook trigger
 * @author Fahreza Tamara
 *
 */
@RestController
@EnableAutoConfiguration
public class SheetUpdaterController  extends Controller {

	Logger log = Logger.getLogger(SheetUpdaterController.class);
		
	@RequestMapping("/Callback")
	public GlobalResponse callBack () throws IOException {
		log.info("callback");
		ApiResponse response = new ApiResponse();
		response.setMessage("SUCCESS");
		return super.goodResponse(response, true, "Success", "");
	}
	
	
	@RequestMapping(value = "/webHookListener")
	public GlobalResponse webHookListener (@RequestBody String json) throws IOException {
		// Convert to JSON from string requestBody
		WebHook webHook = jsonService.convertToObject(json,WebHook.class);
		// Read sheetMapping.json (mapping column configuration)
		String sheetMappingJson = FileTool.getStrFileContent(AppConstant.SHEET_MAPPING_FILE);
		// create response
		ApiResponse apiResponse = new ApiResponse();
		// Convert to JSON
		SheetDefinition sheetDefinition = sheetService.getCurrentSheetDefinition(sheetMappingJson);
		// Get kind (activity by user in pivotal)
		String kind = webHook.getKind();
		if (AppConstant.ACTIVITY_CREATE.equals(kind) || AppConstant.ACTIVITY_UPDATE.equals(kind)) {
			log.info("Perform add / update sheet");
			apiResponse = sheetService.addUpdateStory(webHook, sheetDefinition);
		} else if (AppConstant.ACTIVITY_MOVE.equals(kind)) {
			log.info("Perform move position");
			apiResponse = sheetService.moveStory(webHook, sheetDefinition);
		}	
		return super.goodResponse(apiResponse, apiResponse.getStatus(), apiResponse.getMessage(),"");
	}

}

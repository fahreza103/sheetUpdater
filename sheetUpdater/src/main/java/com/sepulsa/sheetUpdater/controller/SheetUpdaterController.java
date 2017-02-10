package com.sepulsa.sheetUpdater.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
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
	
	private Sheets sheet;
	
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
			
		} else if (ACTIVITY_UPDATE.equals(kind)) {
			
		}
	  
		return json;
	}
	
	
	@RequestMapping("/updateSheet")
	public String updateSheet (HttpServletRequest request) throws IOException {
        // Build a new authorized API client service.
		if(sheet == null) {
			sheet = sheetService.getSheetsService();
		}
        String spreadsheetId = "1fwCkPcAN2ZnsY-ytczC-L-IrKBm_kg-oJAPZMoekqbI";
        String range = "TestSheet!A2:E";
        ValueRange response = sheet.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        List<List<Object>> values = response.getValues();
        
        String text = "";
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {
          System.out.println("Name, Major");
          for (List row : values) {
            // Print columns A and E, which correspond to indices 0 and 4.
            System.out.printf("%s, %s\n", row.get(0), row.get(4));
            text += row.get(0);
          }
        }
		return text;
	}
}

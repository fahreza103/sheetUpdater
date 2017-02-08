package com.sepulsa.sheetUpdater.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.sepulsa.sheetUpdater.Service.SheetService;

@RestController
@EnableAutoConfiguration
public class SheetUpdaterController {

	@Autowired
	private SheetService sheetService;
	
	private Sheets sheet;
	
	@RequestMapping("/Callback")
	public String callBack () throws IOException {
		return "OK";
	}
	
	@RequestMapping(value = "/webHookListener",
					method = RequestMethod.POST,
					consumes = "text/plain")
	public String webHookListener (@RequestBody String activity) {
		return activity;
	}
	
	
	@RequestMapping("/updateSheet")
	public String updateSheet (HttpServletRequest request) throws IOException {
        // Build a new authorized API client service.
		sheet = sheetService.getSheetsService();
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

package com.sepulsa.sheetUpdater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

import com.sepulsa.sheetUpdater.object.GlobalResponse;
import com.sepulsa.sheetUpdater.object.Meta;
import com.sepulsa.sheetUpdater.service.JsonService;
import com.sepulsa.sheetUpdater.service.SheetService;

@RestController
@EnableAutoConfiguration
public class Controller {
	
	@Autowired
	public SheetService sheetService;
	@Autowired
	public JsonService jsonService;
	
	public GlobalResponse goodResponse(Object resp, Boolean status, String message, String user) {
		GlobalResponse gr = new GlobalResponse();
		
		Meta meta = new Meta();
		meta.setMessage(message);
		meta.setStatus(status);
		meta.setUser(user);
		
		gr.setMeta(meta);
		gr.setResult(resp);
		
		return gr;
	}
}

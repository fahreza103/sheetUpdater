package com.sepulsa.sheetUpdater;

import java.io.IOException;

import com.google.api.services.sheets.v4.Sheets;
import com.sepulsa.sheetUpdater.Service.SheetService;

public class AuthorizePermissionApplication {
	
	public static void main(String[] args) throws IOException {
		// Build a new authorized API client service.
		SheetService sheetService = new SheetService();
		Sheets service = sheetService.getSheetsService();
		
		System.out.println("service :" +service);
	}
}

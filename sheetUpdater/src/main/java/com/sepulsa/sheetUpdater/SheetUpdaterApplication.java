package com.sepulsa.sheetUpdater;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.google.api.services.sheets.v4.Sheets;
import com.sepulsa.sheetUpdater.Service.SheetService;

@SpringBootApplication
@ComponentScan("com.sepulsa")
public class SheetUpdaterApplication {

	public static void main(String[] args) {
		// Build a new authorized API client service.
		try {
			SheetService sheetService = new SheetService();
			Sheets service = sheetService.getSheetsService();
			SpringApplication.run(SheetUpdaterApplication.class, args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
	
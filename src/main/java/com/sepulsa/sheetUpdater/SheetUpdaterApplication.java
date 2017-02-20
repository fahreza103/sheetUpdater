package com.sepulsa.sheetUpdater;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import com.sepulsa.sheetUpdater.service.SheetService;

/**
 * Run the application here
 * @author Fahreza Tamara
 *
 */
@SpringBootApplication
@ComponentScan("com.sepulsa")
public class SheetUpdaterApplication {

	public static void main(String[] args) {
		// Build a new authorized API client service.
		try {
			SheetService sheetService = new SheetService();
			sheetService.getSheetsService();
			SpringApplication.run(SheetUpdaterApplication.class, args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
	
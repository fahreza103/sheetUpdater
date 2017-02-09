package com.sepulsa.sheetUpdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sepulsa")
@EnableOAuth2Sso
public class SheetUpdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SheetUpdaterApplication.class, args);
	}
}
	
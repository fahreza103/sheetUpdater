package com.sepulsa.sheetUpdater.test.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.sepulsa.sheetUpdater.object.WebHook;
import com.sepulsa.sheetUpdater.service.JsonService;

@RunWith(SpringRunner.class)
public class JsonServiceTest {

	
	@Test
	public void testConvertJsonStringToObject() {
		JsonService jsonService = new JsonService();
		// Test with WebHook class
		String testJsonStr = "{\"kind\" : \"test_activity\",\"message\" : \"test message\"}";
		WebHook webhook = jsonService.convertToObject(testJsonStr, WebHook.class);
		assertNotNull("WebHook object should not null", webhook);
		assertEquals("test_activity", webhook.getKind());
		assertEquals("test message", webhook.getMessage());
		
		jsonService = null;
	}
	
	@Test
	public void testConvertJsonStringToObjectErrorShouldNull() {
		JsonService jsonService = new JsonService();
		String testJsonStr = "invalid json";
		WebHook webhook = jsonService.convertToObject(testJsonStr, WebHook.class);
		assertNull("WebHook object should be null",webhook);
		jsonService = null;
	}
}

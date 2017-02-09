package com.sepulsa.sheetUpdater.Service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sepulsa.sheetUpdater.Object.WebHook;

/**
 * 
 * Provide functionality to handle JSON operation, converting json string to object
 * or object to json string
 */
@Component
public class JsonService {
	
	private Logger log = Logger.getLogger(JsonService.class);
	
	/**
	 * Convert json string into WebHook Pojo 
	 * @param json string
	 * @return WebHook Object
	 */
	public WebHook convertToObject (String json) {
		log.debug("Convert json to object, json string :"+json);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			WebHook webHook = mapper.readValue(json, WebHook.class);
			
			// Pretty print, log the values in String from the object
			String webHookStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(webHook);
			log.info(webHookStr);
			return webHook;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

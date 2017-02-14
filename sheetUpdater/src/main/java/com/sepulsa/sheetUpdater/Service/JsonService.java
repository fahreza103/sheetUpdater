package com.sepulsa.sheetUpdater.Service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	 * @param <T> 
	 * @param json string
	 * @return WebHook Object
	 */
	@SuppressWarnings("unchecked")
	public <T> T convertToObject (String json, Class<?> clazz) {
		try {
			// Replace null values with empty String, it means something changed to empty
			json = json.replaceAll("null", "\"\"");
			log.info("Convert json to object, json string :"+json);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			T jsobObj = (T) mapper.readValue(json, clazz);
			
			// Pretty print, log the values in String from the object
			String webHookStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsobObj);
			log.info(webHookStr);
			return (T) jsobObj;
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

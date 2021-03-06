package com.sepulsa.sheetUpdater.service;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Provide functionality to handle JSON operation, converting JSON string to object
 * or object to json string
 * @Author Fahreza Tamara
 */
@Component
public class JsonService {
	
	private Logger log = Logger.getLogger(JsonService.class);
	
	/**
	 * Convert json string into Java object
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

			return (T) jsobObj;
		} catch (Exception e) {
			log.debug("ERROR CONVERTING TO OBJECT : "+e.getMessage());
		}
		return null;
	}

}

package com.sepulsa.sheetUpdater.util;

import com.sepulsa.sheetUpdater.Object.SheetDefinition;
import com.sepulsa.sheetUpdater.Service.JsonService;

public class StringTool {

	public static boolean isEmpty(String text) {
		if(text == null || "".equals(text)) {
			return true;
		} 
		return false;
		
	}
	
	public static String replaceEmpty(String text, String replacement) {
		if(isEmpty(text)) {
			return replacement;
		} 
		return text;		
	}
	
	public static String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
	}
	
	public static void main (String[] args) {
		JsonService js = new JsonService();
		String sheetMappingJson = FileTool.getStrFileContent("sheetMapping.json");
		// Convert to JSON
		SheetDefinition sheetDefinition = js.convertToObject(sheetMappingJson,SheetDefinition.class);
		System.out.println(sheetDefinition);
	}
}

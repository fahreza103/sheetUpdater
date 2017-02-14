package com.sepulsa.sheetUpdater.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sepulsa.sheetUpdater.Object.Content;
import com.sepulsa.sheetUpdater.Object.SheetDefinition;
import com.sepulsa.sheetUpdater.Object.SheetDefinitionDetail;
import com.sepulsa.sheetUpdater.Object.Values;
import com.sepulsa.sheetUpdater.Object.WebHook;
import com.sepulsa.sheetUpdater.Service.JsonService;

public class StringTool {

	public static boolean isEmpty(Object text) {
		if(text == null || "".equals(text)) {
			return true;
		} 
		return false;
		
	}
	
	public static Object replaceEmpty(Object text, String replacement) {
		if(isEmpty(text)) {
			return replacement;
		} 
		return text;		
	}
	
	public static String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
	}
	
	public static void main (String[] args) {
		Values values = new Values();
		String fldName = new ReflectionUtil(values).getFieldNameFromAnnotation(values, JsonProperty.class, "current_state");
		System.out.println(fldName);
	}
}

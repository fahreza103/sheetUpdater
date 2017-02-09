package com.sepulsa.sheetUpdater.util;

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
}

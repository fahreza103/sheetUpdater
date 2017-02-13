package com.sepulsa.sheetUpdater.util;

import java.util.ArrayList;
import java.util.List;

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
	
	public static void main (String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		
		list.remove(1);
		System.out.print(list);
	}
}

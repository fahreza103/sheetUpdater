package com.sepulsa.sheetUpdater.util;

import org.apache.commons.lang.ArrayUtils;



/**
 * String operations defined here
 * @author Fahreza Tamara
 *
 */
public class StringTool {

	/**
	 * Check if the string is empty
	 * @param text
	 * @return true if the string is null or empty string, false otherwise
	 */
	public static boolean isEmpty(Object text) {
		if(text == null || "".equals(text)) {
			return true;
		} 
		return false;
		
	}
	
	/**
	 * Replace the string, if empty or null
	 * @param text
	 * @param replacement
	 * @return replacement string as specified in parameter, if the string is null
	 */
	public static Object replaceEmpty(Object text, String replacement) {
		if(isEmpty(text)) {
			return replacement;
		} 
		return text;		
	}
	
	/**
	 * Change the specified integer into letter, for example 1 => A , 2 => B until 26 => Z
	 * @param i
	 * @return String letter
	 */
	public static String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
	}
	
	/**
	 * Check the string contains in array
	 * @param text
	 * @param arrayStr
	 * @return true if the string exist, false otherwise
	 */
	public static Boolean inArray (String text, Object[] arrayStr) {
		if(ArrayUtils.contains(arrayStr, text)) {
			return true;
		}
		return false;
	}
	
	public static void main (String[] args) {
		System.out.println(new String[]{"a","b","c"});
	}
}

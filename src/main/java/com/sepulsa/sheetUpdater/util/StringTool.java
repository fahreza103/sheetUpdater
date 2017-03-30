package com.sepulsa.sheetUpdater.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.sepulsa.sheetUpdater.object.SheetDefinition;
import com.sepulsa.sheetUpdater.object.SheetDefinitionDetail;



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
	
	/**
	 * Group alphabet and return it as list string
	 * @param sheetDefinition
	 * @return
	 */
	public static List<String> getGroupColumn (SheetDefinition sheetDefinition) {
		List<String> groupAlphabet = new ArrayList<String>();
		List<String> group = new ArrayList<String>();
		int lastValue=0;
		int index = 0;
		for(SheetDefinitionDetail sdd : sheetDefinition.getSheetDefinitionDetailListSorted()) {
			int charValue = sdd.getColumn().charAt(0);
			
			if((charValue - lastValue)>1 && lastValue !=0) {
				groupAlphabet.add(StringUtils.join(group,"|"));
				group.clear();
			}
			
			group.add(sdd.getColumn());
			if (index == sheetDefinition.getSheetDefinitionDetailListSorted().size()-1) {
				groupAlphabet.add(StringUtils.join(group,"|"));
			}

			
			lastValue = charValue;
			index++;
		}
		return groupAlphabet;
	}
	
	public static Integer getAlphabetPosition(char startAlphabet, char endAlphabet) {
		int startAlphabetIndex = startAlphabet;
		int endAlphabetIndex =  endAlphabet;
		return endAlphabetIndex-startAlphabetIndex;
	}
	
	
	/**
	 * Convert collection object into single string with delimiter
	 * @param collection
	 * @param delimiter
	 * @return string with delimiter 
	 */
	public static String joinListDelimited(Collection<?> collection, String delimiter) {
		return StringUtils.join(collection,delimiter);
	}
	
	
	
}

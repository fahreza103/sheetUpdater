package com.sepulsa.sheetUpdater.util;

import java.util.ArrayList;
import java.util.List;

import com.sepulsa.sheetUpdater.Object.Content;
import com.sepulsa.sheetUpdater.Object.SheetDefinition;
import com.sepulsa.sheetUpdater.Object.SheetDefinitionDetail;
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
		JsonService js = new JsonService();
		String dummyJson = FileTool.getStrFileContent("dummyRequest.json");
		String sheetMappingJson = FileTool.getStrFileContent("sheetMapping.json");
		// Convert to JSON
		SheetDefinition sheetDefinition = js.convertToObject(sheetMappingJson,SheetDefinition.class);
		WebHook webHook = js.convertToObject(dummyJson,WebHook.class);
		ReflectionUtil rf = new ReflectionUtil(webHook);
		
		List<Object> colList = new ArrayList<Object>();
		for(SheetDefinitionDetail sdd : sheetDefinition.getSheetDefinitionDetailListSorted()) {
			if("id".equals(sdd.getFieldName()) || "name".equals(sdd.getFieldName())) {
				rf.setObject(webHook.getPrimaryResources().get(0));
				colList.add(rf.getFieldValue(sdd.getFieldName()));
			}  else {
				Content content = webHook.getChanges().get(1);
				rf.setObject(content.getNewValues());
				colList.add(rf.getFieldValue(sdd.getFieldName()));
			}
		}
		
		System.out.println(colList);
	}
}

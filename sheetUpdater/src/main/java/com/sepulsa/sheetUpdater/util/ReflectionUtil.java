package com.sepulsa.sheetUpdater.util;

import java.lang.reflect.Field;


public class ReflectionUtil {
	
	private  Object object;
	
	public ReflectionUtil(Object object) {
		this.object = object;
	}
	
	public Object getFieldValue (String fieldName) {
		Object value = null;
		try {
			Field field = object.getClass().getDeclaredField(fieldName);    
			field.setAccessible(true);
			value = field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public void setFieldValue (String fieldName, Object value) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);    
			field.setAccessible(true);
			field.set(object,value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	
}

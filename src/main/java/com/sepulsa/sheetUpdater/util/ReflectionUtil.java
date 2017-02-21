package com.sepulsa.sheetUpdater.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * Provide functionality to get class content, get the field, method, etc
 * @author Fahreza tamara
 *
 */
public class ReflectionUtil {
	
	private Logger log = Logger.getLogger(ReflectionUtil.class);
	
	private  Object object;
	
	public ReflectionUtil(Object object) {
		this.object = object;
	}
	
	public String getFieldNameFromAnnotation(Object object, Class<? extends Annotation> annotationClass, String annotationValue) {
		try {
			for(Field f:object.getClass().getDeclaredFields()) {
				if(f.isAnnotationPresent(annotationClass)) {
					Object value = "";
					Annotation annotation = f.getAnnotation(annotationClass);
					Method method = annotationClass.getDeclaredMethod("value");
		            value = method.invoke(annotation, (Object[])null);           

				    if(annotationValue.equals(value)) {
				    	return f.getName();
				    }
				} 
			}
		} catch (Exception e) {
			log.debug("####ERROR : "+e.getMessage()+" caused :"+e.getCause());
		} 
		return null;
	}
	
	public Object getFieldValue (String fieldName) {
		Object value = null;
		try {
			Field field = object.getClass().getDeclaredField(fieldName);    
			field.setAccessible(true);
			value = field.get(object);
		} catch (Exception e) {
			log.debug("####ERROR : "+e.getMessage()+" caused :"+e.getCause());
		}
		return value;
	}
	
	public Object setFieldValue (String fieldName, Object value) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);    
			field.setAccessible(true);
			field.set(object,value);
			return field.getName();
		} catch (Exception e) {
			log.debug("####ERROR : "+e.getMessage()+" caused :"+e.getCause());
		}
		return null;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	
}

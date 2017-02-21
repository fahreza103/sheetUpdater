package com.sepulsa.sheetUpdater.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sepulsa.sheetUpdater.object.Values;
import com.sepulsa.sheetUpdater.object.WebHook;
import com.sepulsa.sheetUpdater.util.ReflectionUtil;

@RunWith(SpringRunner.class)
public class ReflectionUtilTest {

	private WebHook webHook;
	private ReflectionUtil rf;
	
	@Before
	public void setUp() {
		this.webHook = new WebHook();
		this.rf = new ReflectionUtil(webHook);
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(rf);
		assertNotNull(rf.getObject());
	}
	
	@Test
	public void testGetFieldAnnotationShouldReturnFieldNameByAnnotationValue() {
		assertEquals("occuredAt", rf.getFieldNameFromAnnotation(webHook, JsonProperty.class, "occurred_at"));
	}
	
	@Test
	public void testGetFieldAnnotationShouldReturnNullIfAnnotationClassNotFound() {
		assertNull("should return null because annotation with class Test.class not found",
				rf.getFieldNameFromAnnotation(webHook, Test.class, "occurred_at"));
	}
	
	@Test
	public void testGetFieldAnnotationShouldReturnNullIfAnnotationValueNotFound() {
		assertNull("should return null because annotation with value field_not_found not found",
				rf.getFieldNameFromAnnotation(webHook, JsonProperty.class, "field_not_found"));
	}
	
	@Test
	public void testGetFieldAnnotationShouldReturnNullIfExceptionOccured() {
		assertNull("should return null because exception occured",
				rf.getFieldNameFromAnnotation(null, null, "exception"));
	}
	
	@Test
	public void testSetGetFieldShouldReturnFieldValue() {
		webHook.setKind("Test");
		assertEquals("Test",rf.getFieldValue("kind"));
		
		rf.setFieldValue("guid", "999_xx");
		assertEquals("999_xx", webHook.getGuid());
	}
	
	@Test
	public void testSetGetFieldShouldReturnNull() {
		assertNull("should return null, field not found",rf.getFieldValue("iAmFuckedUp"));
		
		assertNull(rf.setFieldValue("haiBaby", "not found"));
	}
	
	@Test 
	public void testChangeObject() {
		rf.setObject(new Values());
		assertEquals(Values.class, rf.getObject().getClass());
	}
	
	@After
	public void tearDown() {
		this.webHook = null;
		this.rf = null;
	}
}

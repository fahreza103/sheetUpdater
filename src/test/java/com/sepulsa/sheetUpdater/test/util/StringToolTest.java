package com.sepulsa.sheetUpdater.test.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.sepulsa.sheetUpdater.util.StringTool;

@RunWith(SpringRunner.class)
public class StringToolTest {

	@Test
	public void testIsEmpty() {
		String a = null;
		String b = "";
		String c = "content";
		
		assertTrue(StringTool.isEmpty(a));
		assertTrue(StringTool.isEmpty(b));
		assertFalse(StringTool.isEmpty(c));
		
	}
	
	@Test
	public void testReplaceEmpty() {
		String a = "this is text";
		String b = null;
		String replacement = "<Replace>";
		
		assertEquals(a, StringTool.replaceEmpty(a, replacement));
		assertEquals(replacement, StringTool.replaceEmpty(b, replacement));
	}
	
	@Test
	public void testGetCharForNumber() {
		int a = -1;
		int b = 1;
		int c = 27;
		
		assertNull(StringTool.getCharForNumber(a));
		assertEquals("A", StringTool.getCharForNumber(b));
		assertNull(StringTool.getCharForNumber(c));
	}
	
	@Test
	public void testInArray() {
		String[] arrayStr = new String[] {"pen","pinapple","apple","pie"};
		String textIn = "apple";
		String textIn2 = "ppap";
		
		assertTrue(StringTool.inArray(textIn, arrayStr));
		assertFalse(StringTool.inArray(textIn2, arrayStr));
	}
	
	@Test
	public void testJoinStringDelimited() {
		String[] arrayStr = new String[] {"oh","yeah","baby"};
		assertEquals("oh,yeah,baby", StringTool.joinListDelimited(Arrays.asList(arrayStr), ","));
	}
}

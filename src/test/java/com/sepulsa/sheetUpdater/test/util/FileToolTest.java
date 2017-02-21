package com.sepulsa.sheetUpdater.test.util;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.sepulsa.sheetUpdater.util.FileTool;

@RunWith(SpringRunner.class)
public class FileToolTest {
	
	@Test
	public void testConstructor() {
		FileTool tool = new FileTool();
		assertNotNull(tool);
	}

	@Test
	public void testExtractFileContent () { 
		// File found
		String fileContent = FileTool.getStrFileContent("dummyRequest.json");
		assertNotNull("File content should not null", fileContent);
	}
	
	@Test
	public void testShouldReturnEmptyWhenGotException() {
		// File not found
		String fileContent = FileTool.getStrFileContent("noFile");
		assertEquals("", fileContent);
	}
}

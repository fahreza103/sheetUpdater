package com.sepulsa.sheetUpdater.test.util;

import static org.junit.Assert.*;

import java.io.File;

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
	
	@Test
	public void testWriteFile() {
		String content = "test";
		FileTool.writeFile("test.txt", content);
		
		File f = new File("test.txt");
		assertTrue("file test.txt should exist", f.exists());
		
		f.delete();
	}
	
	@Test
	public void testFileNotExistIfExceptionOccured() {
		String content = "test";
		FileTool.writeFile(null, content);
		
		File f = new File("test.txt");
		assertFalse("file test.txt shouldn't exist", f.exists());
	}
}

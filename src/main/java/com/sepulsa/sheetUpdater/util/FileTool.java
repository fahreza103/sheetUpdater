package com.sepulsa.sheetUpdater.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileTool {
	
	private static Logger log = Logger.getLogger(FileTool.class);

	public static String getStrFileContent (String fileName) {
		String currentDirectory = new File(fileName).getAbsolutePath();
		log.info(currentDirectory);
		
		String content = "";
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				content+=sCurrentLine;
			}

		} catch (IOException e) {
			log.debug("#######ERROR :"+e.getMessage()+" \nCAUSED : "+e.getCause());
		}
		return content;
	}
}

package com.sepulsa.sheetUpdater.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;

/**
 * Provide functionality to run file opration
 * @author Fahreza Tamara
 *
 */
public class FileTool {
	
	private static Logger log = Logger.getLogger(FileTool.class);

	/**
	 * Extract text inside file and return it as String
	 * @param fileName
	 * @return String that contains inside the file
	 */
	public static String getStrFileContent (String fileName) {
		String currentDirectory = new File(fileName).getAbsolutePath();
		log.info(currentDirectory);
		
		String content = "";
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				content+=sCurrentLine;
			}

		} catch (Exception e) {
			log.debug("#######ERROR :"+e.getMessage()+" \nCAUSED : "+e.getCause());
		}
		return content;
	}
}

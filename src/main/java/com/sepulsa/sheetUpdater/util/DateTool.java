package com.sepulsa.sheetUpdater.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provide functionality to operate Date object, formatting, setting time, etc
 * @author Fahreza Tamara
 *
 */
public class DateTool {
	
	
	/**
	 * Format date object into string dd/MM/yyyy
	 * @param date
	 * @return String format of dd/MM/yyyy
	 */
	public static String getDateDMY (Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	/**
	 * Format date object into String dd/MM/yyyy HH:mm with time
	 * @param date
	 * @return String format date time dd/MM/yyyy HH:mm 
	 */
	public static String getDateDMYHHMM(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}

}

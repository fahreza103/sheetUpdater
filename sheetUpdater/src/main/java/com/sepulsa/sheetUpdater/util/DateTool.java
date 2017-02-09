package com.sepulsa.sheetUpdater.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
	
	
	public static String getDateDMY (Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

}

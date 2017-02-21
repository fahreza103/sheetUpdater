package com.sepulsa.sheetUpdater.test.util;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.sepulsa.sheetUpdater.util.DateTool;


@RunWith(SpringRunner.class)
public class DateToolTest {
	
	Calendar calendar = new GregorianCalendar(2017, 1, 15);
	
	@Test
	public void testFormatDMY() {
		DateTool tool = new DateTool();
		assertNotNull(tool);
		assertEquals("15/02/2017",DateTool.getDateDMY(calendar.getTime()));
	}
	
	@Test
	public void testFormatDMYHHMM() {
		calendar.set(Calendar.HOUR_OF_DAY,16);
		calendar.set(Calendar.MINUTE,30);
		assertEquals("15/02/2017 16:30",DateTool.getDateDMYHHMM(calendar.getTime()));
	}
	
	@After
	public void tearDown() {
		this.calendar = null;
	}

}

package com.sepulsa.sheetUpdater.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheetRowValues implements Serializable {

	private static final long serialVersionUID = -9064869522755421612L;

	private long rowNum;
	
	private List<Object> colListValues = new ArrayList<Object>();

	public long getRowNum() {
		return rowNum;
	}

	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	public List<Object> getColListValues() {
		return colListValues;
	}

	public void setColListValues(List<Object> colListValues) {
		this.colListValues = colListValues;
	}

	
	
	
}

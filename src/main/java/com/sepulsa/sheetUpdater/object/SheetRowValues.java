package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SheetRowValues implements Serializable {

	private static final long serialVersionUID = -9064869522755421612L;

	private Integer rowNum;
	
	private List<Object> colListValues = new ArrayList<Object>();

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public List<Object> getColListValues() {
		return colListValues;
	}

	public void setColListValues(List<Object> colListValues) {
		this.colListValues = colListValues;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}

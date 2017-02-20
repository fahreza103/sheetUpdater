package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;

public class SheetColValues implements Serializable {

	private static final long serialVersionUID = -5947863924722979072L;
	private String value;
	private String cell;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	
	

}

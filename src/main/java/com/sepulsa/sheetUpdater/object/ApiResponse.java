package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;

public class ApiResponse implements Serializable {

	private static final long serialVersionUID = -1627184577200154586L;
	
	private Integer status;
	private String message;
	private String updatedRange;
	private String spreadsheetId;
	private Integer updatedColumns;
	private Integer updatedCells;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUpdatedRange() {
		return updatedRange;
	}
	public void setUpdatedRange(String updatedRange) {
		this.updatedRange = updatedRange;
	}
	public String getSpreadsheetId() {
		return spreadsheetId;
	}
	public void setSpreadsheetId(String spreadsheetId) {
		this.spreadsheetId = spreadsheetId;
	}
	public Integer getUpdatedColumns() {
		return updatedColumns;
	}
	public void setUpdatedColumns(Integer updatedColumns) {
		this.updatedColumns = updatedColumns;
	}
	public Integer getUpdatedCells() {
		return updatedCells;
	}
	public void setUpdatedCells(Integer updatedCells) {
		this.updatedCells = updatedCells;
	}
	
	
}

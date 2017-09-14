package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ApiResponse implements Serializable {

	private static final long serialVersionUID = -1627184577200154586L;
	
	private Boolean status;
	private String message;
	private String updatedRange;
	private String spreadsheetId;
	private Integer updatedColumns;
	private Integer updatedCells;
	private Integer updatedRows;
	private Integer moveRowBefore;
	private Integer moveRowAfter;
	private List<List<Object>> updatedData = new ArrayList<List<Object>>();
	

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
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
	public Integer getUpdatedRows() {
		return updatedRows;
	}
	public void setUpdatedRows(Integer updatedRows) {
		this.updatedRows = updatedRows;
	}
	public List<List<Object>> getUpdatedData() {
		return updatedData;
	}
	public void setUpdatedData(List<List<Object>> updatedData) {
		this.updatedData = updatedData;
	}
	public Integer getMoveRowBefore() {
		return moveRowBefore;
	}
	public void setMoveRowBefore(Integer moveRowBefore) {
		this.moveRowBefore = moveRowBefore;
	}
	public Integer getMoveRowAfter() {
		return moveRowAfter;
	}
	public void setMoveRowAfter(Integer moveRowAfter) {
		this.moveRowAfter = moveRowAfter;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}

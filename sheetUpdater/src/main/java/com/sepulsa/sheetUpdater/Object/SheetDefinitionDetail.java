package com.sepulsa.sheetUpdater.Object;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SheetDefinitionDetail implements Serializable {

	private static final long serialVersionUID = -3747636510033763541L;

	@JsonProperty("fieldName")
	private String fieldName;
	@JsonProperty("viewName")
	private String viewName;
	@JsonProperty("column")
	private String column;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}

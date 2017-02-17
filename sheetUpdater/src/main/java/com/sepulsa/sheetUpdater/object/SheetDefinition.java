package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SheetDefinition implements Serializable {

	private static final long serialVersionUID = 7554929770692931112L;
	
	@JsonProperty("spreadSheetId")
	private String spreadSheetId;
	@JsonProperty("spreadSheetName")
	private String spreadSheetName;
	@JsonProperty("mapping")
	private List<SheetDefinitionDetail> sheetDefinitionDetailList = new ArrayList<SheetDefinitionDetail>();
	private Boolean sheetIsEmpty = false;
	

	public String getSpreadSheetId() {
		return spreadSheetId;
	}

	public void setSpreadSheetId(String spreadSheetId) {
		this.spreadSheetId = spreadSheetId;
	}

	public String getSpreadSheetName() {
		return spreadSheetName;
	}

	public void setSpreadSheetName(String spreadSheetName) {
		this.spreadSheetName = spreadSheetName;
	}

	public List<SheetDefinitionDetail> getSheetDefinitionDetailList() {
		return sheetDefinitionDetailList;
	}
	
	public List<SheetDefinitionDetail> getSheetDefinitionDetailListSorted() {
		sortByColumn();
		return sheetDefinitionDetailList;
	}
	
	public SheetDefinition setSheetDefinitionDetailList(List<SheetDefinitionDetail> sheetDefinitionDetailList) {
		this.sheetDefinitionDetailList = sheetDefinitionDetailList;
		return this;
	}
	
	public Boolean getSheetIsEmpty() {
		return sheetIsEmpty;
	}
	public void setSheetIsEmpty(Boolean sheetIsEmpty) {
		this.sheetIsEmpty = sheetIsEmpty;
	}
	
	public List<SheetDefinitionDetail> sortByColumn() {
		Collections.sort(sheetDefinitionDetailList,new Comparator<SheetDefinitionDetail>() {

			@Override
			public int compare(SheetDefinitionDetail o1, SheetDefinitionDetail o2) {
                return ((SheetDefinitionDetail)o1).getColumn()
                        .compareTo(((SheetDefinitionDetail)o2).getColumn());
			}
			
		});
		return sheetDefinitionDetailList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}

package com.sepulsa.sheetUpdater.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SheetDefinition implements Serializable {

	private static final long serialVersionUID = 7554929770692931112L;
	
	@JsonProperty("idField")
	private String id;
	@JsonProperty("mapping")
	private List<SheetDefinitionDetail> sheetDefinitionDetailList = new ArrayList<SheetDefinitionDetail>();
	
	public String getId() {
		return id;
	}
	public SheetDefinition setId(String id) {
		this.id = id;
		return this;
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

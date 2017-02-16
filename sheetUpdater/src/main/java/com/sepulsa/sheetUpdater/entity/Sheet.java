package com.sepulsa.sheetUpdater.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Sheet implements Serializable {
	
	private static final long serialVersionUID = -4406206484324207819L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
	@Column(name="SHEET_ID",length=255)
    private String sheetId;
	@Column(name="STRUCTURE")
	@Lob
    private String structure;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSheetId() {
		return sheetId;
	}
	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    
}

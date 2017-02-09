package com.sepulsa.sheetUpdater.Object;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Values implements Serializable {

	private static final long serialVersionUID = -2290819477971877049L;
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("story_id")
	private String storyId;
	@JsonProperty("text")
	private String text;
	@JsonProperty("person_id")
	private String personId;
	@JsonProperty("counts")
	private Counts counts;
	@JsonProperty("name")
	private String name;
	@JsonProperty("estimate")
	private long estimate;
	@JsonProperty("created_at")
	private long createdAt;
	@JsonProperty("updated_at")
	private long updatedAt;
	@JsonProperty("before_id")
	private String beforeId;
	@JsonProperty("after_id")
	private String afterId;
	
	public Counts getCounts() {
		return counts;
	}
	public void setCounts(Counts counts) {
		this.counts = counts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getEstimate() {
		return estimate;
	}
	public void setEstimate(long estimate) {
		this.estimate = estimate;
	}
	public long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	

}

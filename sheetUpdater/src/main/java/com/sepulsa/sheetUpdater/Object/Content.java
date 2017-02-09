package com.sepulsa.sheetUpdater.Object;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Content implements Serializable {

	private static final long serialVersionUID = 6110135639324912675L;
	
	@JsonProperty("kind")
	private String kind;
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("change_type")
	private String changeType;
	@JsonProperty("story_type")
	private String storyType;
	@JsonProperty("url")
	private String url;
	@JsonProperty("initials")
	private String initials;
	@JsonProperty("original_values")
	private Values originalValues;
	@JsonProperty("new_values")
	private Values newValues;
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStoryType() {
		return storyType;
	}
	public void setStoryType(String storyType) {
		this.storyType = storyType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public Values getOriginalValues() {
		return originalValues;
	}
	public void setOriginalValues(Values originalValues) {
		this.originalValues = originalValues;
	}
	public Values getNewValues() {
		return newValues;
	}
	public void setNewValues(Values newValues) {
		this.newValues = newValues;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	
}

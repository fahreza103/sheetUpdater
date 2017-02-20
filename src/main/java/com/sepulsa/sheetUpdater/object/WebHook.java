package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class WebHook implements Serializable {

	private static final long serialVersionUID = 8809465181914306427L;
	
	@JsonProperty("kind")
	private String kind;
	@JsonProperty("guid")
	private String guid;
	@JsonProperty("project_version")
	private String projectVersion;
	@JsonProperty("message")
	private String message;
	@JsonProperty("highlight")
	private String highlight;
	@JsonProperty("changes")
	private List<Content> changes;
	@JsonProperty("primary_resources")
	private List<Content> primaryResources;
	@JsonProperty("project")
	private Content project;
	@JsonProperty("performed_by")
	private Content performedBy;
	@JsonProperty("occurred_at")
	private long occuredAt;
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getProjectVersion() {
		return projectVersion;
	}
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	public List<Content> getChanges() {
		return changes;
	}
	public void setChanges(List<Content> changes) {
		this.changes = changes;
	}
	public List<Content> getPrimaryResources() {
		return primaryResources;
	}
	public void setPrimaryResources(List<Content> primaryResources) {
		this.primaryResources = primaryResources;
	}
	public Content getProject() {
		return project;
	}
	public void setProject(Content project) {
		this.project = project;
	}
	public Content getPerformedBy() {
		return performedBy;
	}
	public void setPerformedBy(Content performedBy) {
		this.performedBy = performedBy;
	}
	public long getOccuredAt() {
		return occuredAt;
	}
	public void setOccuredAt(long occuredAt) {
		this.occuredAt = occuredAt;
	}	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

package com.sepulsa.sheetUpdater.Object;

import java.io.Serializable;
import java.util.List;

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
	


}

package com.sepulsa.sheetUpdater.Object;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Values implements Serializable {

	private static final long serialVersionUID = -2290819477971877049L;
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("story_type")
	private String storyType;
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
	private Long estimate;
	@JsonProperty("created_at")
	private Long createdAt;
	@JsonProperty("updated_at")
	private Long updatedAt;
	@JsonProperty("before_id")
	private String beforeId;
	@JsonProperty("after_id")
	private String afterId;
	@JsonProperty("current_state")
	private String currentState;
	@JsonProperty("requested_by_id")
	private String requestedById;
	@JsonProperty("owned_by_id")
	private String ownedById;
	@JsonProperty("owner_ids")
	private List<String> ownerIds;
	@JsonProperty("complete")
	private String complete;
	@JsonProperty("filename")
	private String fileName;
	@JsonProperty("uploader_id")
	private String uploaderId;
	@JsonProperty("thumbnailable")
	private String thumbnailable;
	@JsonProperty("size")
	private Long size;
	@JsonProperty("description")
	private String description;
	@JsonProperty("download_url")
	private String downloadUrl;
	@JsonProperty("content_type")
	private String contentType;
	@JsonProperty("uploaded")
	private String uploaded;
	@JsonProperty("big_url")
	private String bigUrl;
	@JsonProperty("thumbnail_url")
	private String thumbnailUrl;
	@JsonProperty("position")
	private Long position;
	@JsonProperty("label_ids")
	private List<String> labelIds;
	@JsonProperty("labels")
	private List<String> labels;
	@JsonProperty("follower_ids")
	private List<String> followerIds;
	@JsonProperty("file_attachment_ids")
	private List<String> fileAttachementIds;
	@JsonProperty("google_attachment_ids")
	private List<String> googleAttachementIds;
	@JsonProperty("file_attachments")
	private List<String> fileAttachements;
	@JsonProperty("google_attachments")
	private List<String> googleAttachments;
	
	
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
	public Long getEstimate() {
		return estimate;
	}
	public void setEstimate(Long estimate) {
		this.estimate = estimate;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public Long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	public String getBeforeId() {
		return beforeId;
	}
	public void setBeforeId(String beforeId) {
		this.beforeId = beforeId;
	}
	public String getAfterId() {
		return afterId;
	}
	public void setAfterId(String afterId) {
		this.afterId = afterId;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getOwnedById() {
		return ownedById;
	}
	public void setOwnedById(String ownedById) {
		this.ownedById = ownedById;
	}
	public List<String> getOwnerIds() {
		return ownerIds;
	}
	public void setOwnerIds(List<String> ownerIds) {
		this.ownerIds = ownerIds;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUploaderId() {
		return uploaderId;
	}
	public void setUploaderId(String uploaderId) {
		this.uploaderId = uploaderId;
	}
	public String getThumbnailable() {
		return thumbnailable;
	}
	public void setThumbnailable(String thumbnailable) {
		this.thumbnailable = thumbnailable;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getUploaded() {
		return uploaded;
	}
	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}
	public String getBigUrl() {
		return bigUrl;
	}
	public void setBigUrl(String bigUrl) {
		this.bigUrl = bigUrl;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public List<String> getFileAttachementIds() {
		return fileAttachementIds;
	}
	public void setFileAttachementIds(List<String> fileAttachementIds) {
		this.fileAttachementIds = fileAttachementIds;
	}
	public List<String> getGoogleAttachementIds() {
		return googleAttachementIds;
	}
	public void setGoogleAttachementIds(List<String> googleAttachementIds) {
		this.googleAttachementIds = googleAttachementIds;
	}
	public List<String> getFileAttachements() {
		return fileAttachements;
	}
	public void setFileAttachements(List<String> fileAttachements) {
		this.fileAttachements = fileAttachements;
	}
	public List<String> getGoogleAttachments() {
		return googleAttachments;
	}
	public void setGoogleAttachments(List<String> googleAttachments) {
		this.googleAttachments = googleAttachments;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPosition() {
		return position;
	}
	public void setPosition(Long position) {
		this.position = position;
	}
	public List<String> getLabelIds() {
		return labelIds;
	}
	public void setLabelIds(List<String> labelIds) {
		this.labelIds = labelIds;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<String> getFollowerIds() {
		return followerIds;
	}
	public void setFollowerIds(List<String> followerIds) {
		this.followerIds = followerIds;
	}
	public String getRequestedById() {
		return requestedById;
	}
	public void setRequestedById(String requestedById) {
		this.requestedById = requestedById;
	}
	public String getStoryType() {
		return storyType;
	}
	public void setStoryType(String storyType) {
		this.storyType = storyType;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

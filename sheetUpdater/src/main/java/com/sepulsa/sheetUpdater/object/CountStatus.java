package com.sepulsa.sheetUpdater.object;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CountStatus {
	
	@JsonProperty("accepted")
	private Long accepted;
	@JsonProperty("started")
	private Long started;
	@JsonProperty("finished")
	private Long finished;
	@JsonProperty("unstarted")
	private Long unstarted;
	@JsonProperty("planned")
	private Long planned;
	@JsonProperty("delivered")
	private Long delivered;
	@JsonProperty("unscheduled")
	private Long unsceduled;
	@JsonProperty("rejected")
	private Long rejected;
	@JsonProperty("kind")
	private String kind;
	
	public Long getAccepted() {
		return accepted;
	}
	public void setAccepted(Long accepted) {
		this.accepted = accepted;
	}
	public Long getStarted() {
		return started;
	}
	public void setStarted(Long started) {
		this.started = started;
	}
	public Long getFinished() {
		return finished;
	}
	public void setFinished(Long finished) {
		this.finished = finished;
	}
	public Long getUnstarted() {
		return unstarted;
	}
	public void setUnstarted(Long unstarted) {
		this.unstarted = unstarted;
	}
	public Long getPlanned() {
		return planned;
	}
	public void setPlanned(Long planned) {
		this.planned = planned;
	}
	public Long getDelivered() {
		return delivered;
	}
	public void setDelivered(Long delivered) {
		this.delivered = delivered;
	}
	public Long getUnsceduled() {
		return unsceduled;
	}
	public void setUnsceduled(Long unsceduled) {
		this.unsceduled = unsceduled;
	}
	public Long getRejected() {
		return rejected;
	}
	public void setRejected(Long rejected) {
		this.rejected = rejected;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

package com.sepulsa.sheetUpdater.Object;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CountStatus {
	
	@JsonProperty("accepted")
	private int accepted;
	@JsonProperty("started")
	private int started;
	@JsonProperty("finished")
	private int finished;
	@JsonProperty("unstarted")
	private int unstarted;
	@JsonProperty("planned")
	private int planned;
	@JsonProperty("delivered")
	private int delivered;
	@JsonProperty("unscheduled")
	private int unsceduled;
	@JsonProperty("rejected")
	private int rejected;
	@JsonProperty("kind")
	private String kind;
	
	public int getAccepted() {
		return accepted;
	}
	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}
	public int getStarted() {
		return started;
	}
	public void setStarted(int started) {
		this.started = started;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
	public int getUnstarted() {
		return unstarted;
	}
	public void setUnstarted(int unstarted) {
		this.unstarted = unstarted;
	}
	public int getPlanned() {
		return planned;
	}
	public void setPlanned(int planned) {
		this.planned = planned;
	}
	public int getDelivered() {
		return delivered;
	}
	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}
	public int getUnsceduled() {
		return unsceduled;
	}
	public void setUnsceduled(int unsceduled) {
		this.unsceduled = unsceduled;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	
}

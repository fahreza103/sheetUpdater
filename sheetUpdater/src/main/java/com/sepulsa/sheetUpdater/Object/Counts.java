package com.sepulsa.sheetUpdater.Object;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Counts {

	@JsonProperty("number_of_zero_point_stories_by_state")
	private CountStatus numZeroPointByState;
	@JsonProperty("sum_of_story_estimates_by_state")
	private CountStatus sumStoryByState;
	@JsonProperty("number_of_stories_by_state")
	private CountStatus numStoryByState;
	
	
	public CountStatus getNumZeroPointByState() {
		return numZeroPointByState;
	}
	public void setNumZeroPointByState(CountStatus numZeroPointByState) {
		this.numZeroPointByState = numZeroPointByState;
	}
	public CountStatus getSumStoryByState() {
		return sumStoryByState;
	}
	public void setSumStoryByState(CountStatus sumStoryByState) {
		this.sumStoryByState = sumStoryByState;
	}
	public CountStatus getNumStoryByState() {
		return numStoryByState;
	}
	public void setNumStoryByState(CountStatus numStoryByState) {
		this.numStoryByState = numStoryByState;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

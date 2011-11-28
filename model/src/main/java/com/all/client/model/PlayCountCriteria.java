package com.all.client.model;

import java.util.Map;

public class PlayCountCriteria {
	
	private Map<Integer,PlayCountCriteria> playCountCriteriaMap;
	
	public final int minSeconds;
	public final double porcentage;
	
	public PlayCountCriteria() {
		minSeconds=0;
		porcentage=0.0;
	}

	public PlayCountCriteria(int minSeconds, double porcentage) {
		this.minSeconds = minSeconds;
		this.porcentage = porcentage;
	}
	
	public void setPlayCountCriteriaMap(Map<Integer, PlayCountCriteria> playCountCriteriaMap) {
		this.playCountCriteriaMap= playCountCriteriaMap;
	}
	
	public Map<Integer,PlayCountCriteria> getPlayCountCriteriaMap() {
		return playCountCriteriaMap;
	}
	
	
}

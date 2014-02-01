package com.baker.david.irishwhalespotting.domain;

public class KeyIdFeatures {

	private String maxLengthWeight;
	private String averageLengthWeight;
	private String averageLengthWeightAtBirth;
	private String head;
	private String dorsalFin;
	private String colouration;
	private String markings;
	
	public KeyIdFeatures(String maxLengthWeight,
			String averageLengthWeight, String averageLengthWeightAtBirth,
			String head, String dorsalFin, String colouration, String markings) {
		
		this.maxLengthWeight = maxLengthWeight;
		this.averageLengthWeight = averageLengthWeight;
		this.averageLengthWeightAtBirth = averageLengthWeightAtBirth;
		this.head = head;
		this.dorsalFin = dorsalFin;
		this.colouration = colouration;
		this.markings = markings;
	}

	public String getMaxLengthWeight() {
		return maxLengthWeight;
	}
	public void setMaxLengthWeight(String maxLengthWeight) {
		this.maxLengthWeight = maxLengthWeight;
	}
	public String getAverageLengthWeight() {
		return averageLengthWeight;
	}
	public void setAverageLengthWeight(String averageLengthWeight) {
		this.averageLengthWeight = averageLengthWeight;
	}
	public String getAverageLengthWeightAtBirth() {
		return averageLengthWeightAtBirth;
	}
	public void setAverageLengthWeightAtBirth(String averageLengthWeightAtBirth) {
		this.averageLengthWeightAtBirth = averageLengthWeightAtBirth;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getDorsalFin() {
		return dorsalFin;
	}
	public void setDorsalFin(String dorsalFin) {
		this.dorsalFin = dorsalFin;
	}
	public String getColouration() {
		return colouration;
	}
	public void setColouration(String colouration) {
		this.colouration = colouration;
	}
	public String getMarkings() {
		return markings;
	}
	public void setMarkings(String markings) {
		this.markings = markings;
	}
}

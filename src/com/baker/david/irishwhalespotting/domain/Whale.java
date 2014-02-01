package com.baker.david.irishwhalespotting.domain;

public class Whale {

	private String id;
	private String name;
	Classification classification;
	KeyIdentificationFeatures keyIdentificationFeatures;
	private String imgFileName;
	
	public Whale(){}
	
	public Whale(String id, String name) {
		this.id=id;
		this.name=name;
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

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public KeyIdentificationFeatures getKeyIdentificationFeatures() {
		return keyIdentificationFeatures;
	}

	public void setKeyIdentificationFeatures(
			KeyIdentificationFeatures keyIdentificationFeatures) {
		this.keyIdentificationFeatures = keyIdentificationFeatures;
	}
	
	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	@Override
	public String toString() {
		return name;
	}

}
package com.baker.david.irishwhalespotting.domain;

public class Classification {

	private String whaleClass;
	private String order;
	private String subOrder;
	private String family;
	private String genus;
	private String species;
	private String commonName;
	private String irishName;
	
	public Classification(String whaleClass, String order, String subOrder, String family, String genus,
			String species, String commonName, String irishName){
		
		this.whaleClass = whaleClass;
		this.order = order;
		this.subOrder = subOrder;
		this.family = family;
		this.genus = genus;
		this.species = species;
		this.commonName = commonName;
		this.irishName = irishName;
	}
	
	public String getWhaleClass() {
		return whaleClass;
	}
	public void setWhaleClass(String whaleClass) {
		this.whaleClass = whaleClass;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSubOrder() {
		return subOrder;
	}
	public void setSubOrder(String subOrder) {
		this.subOrder = subOrder;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getGenus() {
		return genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getIrishName() {
		return irishName;
	}
	public void setIrishName(String irishName) {
		this.irishName = irishName;
	}
	
	
}

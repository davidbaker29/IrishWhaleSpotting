package com.baker.david.irishwhalespotting.domain;

public class LatestSightingItem {

	private String title;
	private String link;
	private String publicationDate;
	
	public LatestSightingItem() {}
	
	public LatestSightingItem(LatestSightingItem currentSightingsItem) {
		this.title = currentSightingsItem.getTitle();
		this.link = currentSightingsItem.getLink();
		this.publicationDate = currentSightingsItem.getPublicationDate();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}



}

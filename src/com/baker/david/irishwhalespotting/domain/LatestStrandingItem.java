package com.baker.david.irishwhalespotting.domain;


public class LatestStrandingItem {
	
	private String title;
	private String link;
	private String description;
	private String publicationDate;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {	
	    
		int start = description.indexOf("http", 0);
		
		String theLinkHtml = description.substring(start, description.length());
		
		description = description.replace(theLinkHtml, "<a href=\"" + theLinkHtml + "\">" + 
				theLinkHtml + "</a>");
		
		this.description = description;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		if(publicationDate.contains("+0000")){
			publicationDate = publicationDate.replace("+0000", "");
		}
		
		this.publicationDate = publicationDate;
	}
	
	// TODO Auto-generated constructor stub
	public LatestStrandingItem() {}

	public LatestStrandingItem(LatestStrandingItem otherStrandingItem) {
		
		this.title = otherStrandingItem.getTitle();
		this.link = otherStrandingItem.getLink();
		this.description = otherStrandingItem.getDescription();
		this.publicationDate = otherStrandingItem.getPublicationDate();
	}

}

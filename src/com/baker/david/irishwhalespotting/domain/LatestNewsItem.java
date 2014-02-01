package com.baker.david.irishwhalespotting.domain;

public class LatestNewsItem {

	private String title;
	private String link;
	private String description;
	private String author;
	private String category;
	private String publicationDate;
	
	public LatestNewsItem(){}
	
	public LatestNewsItem(LatestNewsItem otherNewsItem) {
		this.title = otherNewsItem.getTitle();
		this.link = otherNewsItem.getLink();
		this.description = otherNewsItem.getDescription();
		this.author = otherNewsItem.getAuthor();
		this.category = otherNewsItem.getCategory();
		this.publicationDate = otherNewsItem.getPublicationDate();
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

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {

		int start = description.indexOf("<div class=\"K2FeedImage\"><img src=", 0);
		
		int end = description.indexOf("<div class=\"K2FeedIntroText\">", 0);
		
		String theImgHtml = description.substring(start, end);
		
		description = description.replace(theImgHtml, "");
		
		this.description = description;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	
}

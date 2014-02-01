package com.baker.david.irishwhalespotting.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baker.david.irishwhalespotting.domain.LatestNewsItem;

public class LatestNewsManager {
	
	public static Map<String, LatestNewsItem> LATEST_NEWS_ITEMS = 
			new HashMap<String, LatestNewsItem>();
	
	public static List<LatestNewsItem> LATEST_NEWS_ITEMS_LIST = 
			new ArrayList<LatestNewsItem>();
	
	public static void addLatestNewsItem(LatestNewsItem latestNewsItem){
		LATEST_NEWS_ITEMS.put(latestNewsItem.getTitle(),latestNewsItem);
	}
	
	public static LatestNewsItem getNewsItem(String title){
		return LATEST_NEWS_ITEMS.get(title);
	}
	
	public static void addLatestNewsItems(List<LatestNewsItem> latestNewsItems){
		LATEST_NEWS_ITEMS_LIST.addAll(latestNewsItems);
	}
	
	public static List<LatestNewsItem> getLatestNewsItems(){
		return LATEST_NEWS_ITEMS_LIST;
	}
}

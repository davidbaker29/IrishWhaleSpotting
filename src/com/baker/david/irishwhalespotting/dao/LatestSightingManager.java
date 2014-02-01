package com.baker.david.irishwhalespotting.dao;

import java.util.HashMap;
import java.util.Map;

import com.baker.david.irishwhalespotting.domain.LatestSightingItem;

public class LatestSightingManager {

	public static Map<String, LatestSightingItem> LATEST_SIGHTING_ITEMS = 
			new HashMap<String, LatestSightingItem>();
	
	public static void addLatestSightingItem(LatestSightingItem latestSightingItem){
		LATEST_SIGHTING_ITEMS.put(latestSightingItem.getTitle(),latestSightingItem);
	}
	
	public static LatestSightingItem getSightingItem(String title){
		return LATEST_SIGHTING_ITEMS.get(title);
	}

}

package com.baker.david.irishwhalespotting.dao;

import java.util.HashMap;
import java.util.Map;

import com.baker.david.irishwhalespotting.domain.LatestStrandingItem;

public class LatestStrandingManager {

	public static Map<String, LatestStrandingItem> LATEST_STRANDING_ITEMS = 
			new HashMap<String, LatestStrandingItem>();
	
	public static void addLatestStrandingItem(LatestStrandingItem latestStrandingItem){
		LATEST_STRANDING_ITEMS.put(latestStrandingItem.getTitle(),latestStrandingItem);
	}
	
	public static LatestStrandingItem getStrandingItem(String title){
		return LATEST_STRANDING_ITEMS.get(title);
	}

}

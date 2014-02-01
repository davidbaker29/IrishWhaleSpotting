package com.baker.david.irishwhalespotting.dao;

import com.baker.david.irishwhalespotting.domain.Whale;
import com.baker.david.irishwhalespotting.domain.whaletypes.AtlanticWhiteSidedDolphin;
import com.baker.david.irishwhalespotting.domain.whaletypes.FinWhale;
import com.baker.david.irishwhalespotting.domain.whaletypes.WhaleNames;

public class WhaleFactory {

	public static Whale buildWhale(String whaleName){
		
		if (whaleName.equalsIgnoreCase(WhaleNames.FIN_WHALE)){
			return new FinWhale();
		}
		else if (whaleName.equalsIgnoreCase(WhaleNames.ATLANTIC_DOLPHIN)){
			return new AtlanticWhiteSidedDolphin();
		}
		return null;
	}
}

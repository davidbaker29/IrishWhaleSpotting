package com.baker.david.irishwhalespotting.parser;

import java.util.List;

import com.baker.david.irishwhalespotting.domain.LatestStrandingItem;

public interface LatestStrandingsFeedParser {
	 List<LatestStrandingItem> parse();
}

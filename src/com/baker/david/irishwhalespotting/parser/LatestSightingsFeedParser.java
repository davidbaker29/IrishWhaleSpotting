package com.baker.david.irishwhalespotting.parser;

import java.util.List;

import com.baker.david.irishwhalespotting.domain.LatestSightingItem;

public interface LatestSightingsFeedParser {
    List<LatestSightingItem> parse();
}
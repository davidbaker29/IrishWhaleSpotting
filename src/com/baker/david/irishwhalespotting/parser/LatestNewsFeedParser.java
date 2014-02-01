package com.baker.david.irishwhalespotting.parser;

import java.util.List;

import com.baker.david.irishwhalespotting.domain.LatestNewsItem;

public interface LatestNewsFeedParser {
    List<LatestNewsItem> parse();
}
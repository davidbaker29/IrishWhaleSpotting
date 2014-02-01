package com.baker.david.irishwhalespotting.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.baker.david.irishwhalespotting.domain.Category;

/**
 * TODO: See if this really has to be static
 */
public class CategoryManager {

	public static List<Category> ALL_CATEGORIES = new ArrayList<Category>();
	
	public static Map<String, Category> CATEGORY_MAP = new LinkedHashMap<String, Category>();
	
	static {
		addCategory(new Category("1", "Classification", "Fin whale Classification"));
		addCategory(new Category("2", "Key Identification Features", "Second biggest whale"));
		addCategory(new Category("3", "Field Identification", "Small dorsal fin"));
	}

	private static void addCategory(Category category) {
		ALL_CATEGORIES.add(category);
		CATEGORY_MAP.put(category.getId(), category);
	}

}

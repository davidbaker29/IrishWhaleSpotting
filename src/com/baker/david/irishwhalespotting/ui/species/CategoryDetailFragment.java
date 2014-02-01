package com.baker.david.irishwhalespotting.ui.species;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.dao.CategoryManager;
import com.baker.david.irishwhalespotting.dao.WhaleManager;
import com.baker.david.irishwhalespotting.domain.Category;

/**
 * A fragment representing a single Whale detail screen. This fragment is either
 * contained in a {@link CategoryListActivity} in two-pane mode (on tablets) or a
 * {@link CategoryDetailActivity} on handsets.
 */
public class CategoryDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Map<String, String> contentToDisplay;
	private Category selectedCategory;
	
	private String selectedWhale;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CategoryDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			this.selectedCategory = CategoryManager.CATEGORY_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
			
			//this.selectedWhale = savedInstanceState.getString("selectedWhale");
			
			this.selectedWhale = getArguments().getString("selectedWhale");
			
			this.contentToDisplay = WhaleManager.getContent(selectedWhale,selectedCategory.getName());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_category_detail,
				container, false);
		
		if (selectedWhale != null) {
			//for (int i = 0; i < this.contentToDisplay.size(); i++){
				
				Set<String> headings = this.contentToDisplay.keySet();
				Collection<String> values = this.contentToDisplay.values();
				
				Iterator<String> iterHeadings = headings.iterator();
				Iterator<String> iterValues = values.iterator();

				LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.categoryDetailLayout);
				
				while (iterHeadings.hasNext() && iterValues.hasNext()){
	
					TextView textViewHeading = new TextView(getActivity());
				    textViewHeading.setText(iterHeadings.next());
				    
				    int grey = getResources().getColor(R.color.grey);		    
				    textViewHeading.setBackgroundColor(grey);
				    
				    //int royalBlue = getResources().getColor(R.color.royal_blue);
				    //textViewHeading.setTextColor(royalBlue);
				    //textViewHeading.setTextColor(getResources().getColor(R.color.royal_blue));
				    		    
				    textViewHeading.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
				    
				    LayoutParams headingLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				    		LayoutParams.WRAP_CONTENT);
			
				    textViewHeading.setLayoutParams(headingLayoutParams);
				    
				    int paddingInDp = 8;  // 6 dps
				    final float scale = getResources().getDisplayMetrics().density;
				    int paddingInPx = (int) (paddingInDp * scale + 0.5f);
				    textViewHeading.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
				    
				    ll.addView(textViewHeading);
				    
				    
				    
					TextView textViewValue = new TextView(getActivity());
					textViewValue.setText(iterValues.next());
					
					textViewHeading.setTextColor(getResources().getColor(R.color.royal_blue));
				    
				    LayoutParams valueLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				    		LayoutParams.WRAP_CONTENT);
			
				    textViewValue.setLayoutParams(valueLayoutParams);
				    
				    textViewValue.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
				    
				    textViewValue.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);

				    ll.addView(textViewValue);
				    
				}
		}

		return rootView;
	}
	
}

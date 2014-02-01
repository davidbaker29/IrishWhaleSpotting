package com.baker.david.irishwhalespotting.ui.species;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.dao.WhaleManager;
import com.baker.david.irishwhalespotting.domain.Constants;
import com.baker.david.irishwhalespotting.domain.Whale;

public class SpeciesFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
  
    public static final String WEB_ACCESS = "webAccess";
    
    private static final List<String> WHALES = WhaleManager.getWhaleNames();
    
    private static final List<Whale> ALL_WHALES = WhaleManager.ALL_WHALES;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
//        View rootView = inflater.inflate(R.layout.fragment_section_latest_news, container, false);
//        
//        List<LatestNewsItem> latestNewsItems = 
//        		LatestNewsManager.getLatestNewsItems();
//		
//    	ListView listView = (ListView) rootView.findViewById(R.id.latestNewsList);
//    	
//        if (latestNewsItems.size() == 0){
//        	LatestNewsItem dummyItem = new LatestNewsItem();
//        	dummyItem.setTitle("Latest News is currently unavailable " +
//        			"as there is no internet connection.");
//        	dummyItem.setPublicationDate("");
//        	latestNewsItems.add(dummyItem);
//        }
//        	
//        listView.setAdapter(new LatestNewsAdapter
//			(getActivity(), R.layout.latest_news_item, latestNewsItems));
//        
        	
        View rootView = inflater.inflate(R.layout.fragment_section_species, container, false);
		
    	ListView listView = (ListView) rootView.findViewById(R.id.whaleList);
        	
        listView.setAdapter(new SpeciesAdapter
			(getActivity(), R.layout.whale_list_item, SpeciesFragment.ALL_WHALES));
		
		final int tabId = getArguments().getInt(ARG_SECTION_NUMBER);
		final boolean webAccess = getArguments().getBoolean(WEB_ACCESS);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    
				Intent categoryList = new Intent(getActivity(), CategoryListActivity.class);    
				String selectedWhale = ((TextView) view.findViewById(R.id.whaleName)).getText().toString();
				categoryList.putExtra("selectedWhale",selectedWhale); //Put your id to your next Intent
				categoryList.putExtra(Constants.WEB_ACCESS,webAccess); 
				categoryList.putExtra(Constants.ARG_SECTION_NUMBER,tabId); 
				
				startActivity(categoryList);
			}
		});
        return rootView;
    } 	
}

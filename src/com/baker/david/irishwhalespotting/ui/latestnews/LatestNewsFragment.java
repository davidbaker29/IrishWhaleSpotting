package com.baker.david.irishwhalespotting.ui.latestnews;

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
import com.baker.david.irishwhalespotting.dao.LatestNewsManager;
import com.baker.david.irishwhalespotting.domain.Constants;
import com.baker.david.irishwhalespotting.domain.LatestNewsItem;

/**
 * A fragment that launches other parts of the demo application.
 */
public class LatestNewsFragment extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_section_latest_news, container, false);
        
        List<LatestNewsItem> latestNewsItems = 
        		LatestNewsManager.getLatestNewsItems();
		
    	ListView listView = (ListView) rootView.findViewById(R.id.latestNewsList);
    	
    	final boolean webAccess = getArguments().getBoolean(Constants.WEB_ACCESS);
    	
//        if (latestNewsItems.size() == 0){
    	if (webAccess == false){
        	LatestNewsItem dummyItem = new LatestNewsItem();
        	dummyItem.setTitle("Latest News is currently unavailable " +
        			"as there is no internet connection.");
        	dummyItem.setPublicationDate("");
        	latestNewsItems.add(dummyItem);
        }
    	else{
	        listView.setAdapter(new LatestNewsAdapter
				(getActivity(), R.layout.latest_news_item, latestNewsItems));
	        
			final int tabId = getArguments().getInt(Constants.ARG_SECTION_NUMBER);
	    		
			listView.setTextFilterEnabled(true);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
				    
					Intent newsItemDetails = new Intent(getActivity(), LatestNewsItemDetailsActivity.class);    
					String selectedNewsItem = ((TextView) view.findViewById(R.id.latestNewsTitle)).getText().toString();
					newsItemDetails.putExtra(Constants.WEB_ACCESS,webAccess); 
					newsItemDetails.putExtra(Constants.ARG_SECTION_NUMBER,tabId); 
					
					newsItemDetails.putExtra(Constants.SELECTED_NEWS_ITEM,selectedNewsItem); //Put your id to your next Intent
	                startActivity(newsItemDetails);
				}
			});
    	}
    	
        return rootView;
   }
}
package com.baker.david.irishwhalespotting.ui.lateststrandings;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.domain.Constants;
import com.baker.david.irishwhalespotting.domain.LatestStrandingItem;
import com.baker.david.irishwhalespotting.parser.LatestStrandingsSaxFeedParser;
import com.baker.david.irishwhalespotting.parser.NoWebAccessException;

public class LatestStrandingsFragment extends Fragment {
    
	private static final String LATEST_STRANDINGS_RSS_URL =
			"http://www.iwdg.ie/_customphp/iscope/rss_strandings.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_section_latest_strandings, container, false);

		List<LatestStrandingItem> latestStrandingItems = null;
		
		try {
			latestStrandingItems = new LatestStrandingsSaxFeedParser().execute(LATEST_STRANDINGS_RSS_URL).get();
		
			ListView listView = (ListView) rootView.findViewById(R.id.latestStrandingsList);
	        
			final boolean webAccess = getArguments().getBoolean(Constants.WEB_ACCESS);
			
	        //if (latestStrandingItems.size() == 0){
			if (webAccess == false){
	        	LatestStrandingItem dummyItem = new LatestStrandingItem();
	        	dummyItem.setTitle("Latest strandings are currently unavailable " +
	        			"as there is no internet connection.");
	        	dummyItem.setPublicationDate("");
	        	latestStrandingItems.add(dummyItem);
	        }
			
	        listView.setAdapter(new LatestStrandingAdapter
					(getActivity(), R.layout.latest_stranding_item, latestStrandingItems));
			
			listView.setTextFilterEnabled(true);

			final int tabId = getArguments().getInt(Constants.ARG_SECTION_NUMBER);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
				    
					Intent strandingItemDetails = new Intent(getActivity(), LatestStrandingItemDetailsActivity.class);    
					String selectedStrandingItem = ((TextView) view.findViewById(R.id.latestStrandingTitle)).getText().toString();
					strandingItemDetails.putExtra(Constants.SELECTED_STRANDING_ITEM,selectedStrandingItem); //Put your id to your next Intent
					strandingItemDetails.putExtra(Constants.WEB_ACCESS,webAccess); 
					strandingItemDetails.putExtra(Constants.ARG_SECTION_NUMBER,tabId); 
					startActivity(strandingItemDetails);
				}
			});
		} 
		catch (InterruptedException e) {
			Log.e("LatestStrandingsFragment", e.getMessage());
		} 
		catch (ExecutionException e) {
			Log.e("LatestStrandingsFragment", e.getMessage());
		}
		catch (NoWebAccessException e) {
			Log.e("LatestStrandingsFragment", "NoWebAccessException");
		}
    
        return rootView;
   }
}
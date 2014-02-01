package com.baker.david.irishwhalespotting.ui.latestsightings;

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
import com.baker.david.irishwhalespotting.domain.LatestSightingItem;
import com.baker.david.irishwhalespotting.parser.LatestSightingsSaxFeedParser;
import com.baker.david.irishwhalespotting.parser.NoWebAccessException;

public class LatestSightingsFragment extends Fragment {

	private static final String LATEST_STRANDINGS_RSS_URL =
			"http://www.iwdg.ie/_customphp/iscope/rss_sightings.php";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_section_latest_sightings, container, false);

		List<LatestSightingItem> latestSightingItems = null;
		
		try {
			latestSightingItems = new LatestSightingsSaxFeedParser().execute(LATEST_STRANDINGS_RSS_URL).get();
		
			ListView listView = (ListView) rootView.findViewById(R.id.latestSightingsList);
			
			final boolean webAccess = getArguments().getBoolean(Constants.WEB_ACCESS);
			
	        //if (latestSightingItems.size() == 0){
			if (webAccess == false){
	        	LatestSightingItem dummyItem = new LatestSightingItem();
	        	dummyItem.setTitle("Latest sightings are currently unavailable " +
	        			"as there is no internet connection.");
	        	dummyItem.setPublicationDate("");
	        	latestSightingItems.add(dummyItem);
	        }
	        
	        listView.setAdapter(new LatestSightingAdapter
					(getActivity(), R.layout.latest_sighting_item, latestSightingItems));
			
			listView.setTextFilterEnabled(true);
			
			final int tabId = getArguments().getInt(Constants.ARG_SECTION_NUMBER);
			
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
				    
					Intent sightingItemDetails = new Intent(getActivity(), LatestSightingItemDetailsActivity.class);    
					String selectedSightingItem = ((TextView) view.findViewById(R.id.latestSightingTitle)).getText().toString();
					
					sightingItemDetails.putExtra(Constants.WEB_ACCESS,webAccess); 
					sightingItemDetails.putExtra(Constants.ARG_SECTION_NUMBER,tabId); 
					sightingItemDetails.putExtra(Constants.SELECTED_SIGHTING_ITEM,selectedSightingItem); //Put your id to your next Intent
	                startActivity(sightingItemDetails);
				}
			});
		} 
		catch (InterruptedException e) {
			Log.e("LatestSightingsFragment", e.getMessage());
		} 
		catch (ExecutionException e) {
			Log.e("LatestSightingsFragment", e.getMessage());
		}
		catch (NoWebAccessException e) {
			Log.e("LatestSightingsFragment", "NoWebAccessException");
		}
    
        return rootView;
   }
}
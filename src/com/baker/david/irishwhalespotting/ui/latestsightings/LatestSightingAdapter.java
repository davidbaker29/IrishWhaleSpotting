package com.baker.david.irishwhalespotting.ui.latestsightings;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.domain.LatestSightingItem;

public class LatestSightingAdapter extends BaseAdapter {

	private List<LatestSightingItem> latestSightingItems;
	private Context context;
	private int layoutResourceId;

	public LatestSightingAdapter(Context context, int layoutResourceId, 
			List<LatestSightingItem> latestSightingItems){
		super();
	    this.context = context;
	    this.layoutResourceId = layoutResourceId;
	    this.latestSightingItems = latestSightingItems;
	}
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.latestSightingItems.size();
	}

	@Override
	public LatestSightingItem getItem(int position) {
		// TODO Auto-generated method stub
		return this.latestSightingItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(
	        		Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layoutResourceId, parent, false);
        }
		
		LatestSightingItem latestSightingItem = this.getItem(position);
		String title = latestSightingItem.getTitle();
        String pubDate = latestSightingItem.getPublicationDate();
       
        // get the reference of textViews
        TextView titleView= (TextView) convertView.findViewById(R.id.latestSightingTitle);
        TextView dateView=(TextView)convertView.findViewById(R.id.latestSightingDate);
        
        titleView.setText(title);
        dateView.setText(pubDate);

        return convertView;
	}

}

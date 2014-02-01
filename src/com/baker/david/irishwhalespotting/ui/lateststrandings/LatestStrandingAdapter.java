package com.baker.david.irishwhalespotting.ui.lateststrandings;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.domain.LatestStrandingItem;

public class LatestStrandingAdapter extends BaseAdapter {

	private List<LatestStrandingItem> latestStrandingItems;
	private Context context;
	private int layoutResourceId;

	public LatestStrandingAdapter(Context context, int layoutResourceId, 
			List<LatestStrandingItem> latestStrandingItems){
		super();
	    this.context = context;
	    this.layoutResourceId = layoutResourceId;
	    this.latestStrandingItems = latestStrandingItems;
	}
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.latestStrandingItems.size();
	}

	@Override
	public LatestStrandingItem getItem(int position) {
		// TODO Auto-generated method stub
		return this.latestStrandingItems.get(position);
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
		
		LatestStrandingItem latestStrandingItem = this.getItem(position);
		String title = latestStrandingItem.getTitle();
        String pubDate = latestStrandingItem.getPublicationDate();
       
        // get the reference of textViews
        TextView titleView= (TextView) convertView.findViewById(R.id.latestStrandingTitle);
        TextView dateView=(TextView)convertView.findViewById(R.id.latestStrandingDate);
        
        titleView.setText(title);
        dateView.setText(pubDate);

        return convertView;
	}

}

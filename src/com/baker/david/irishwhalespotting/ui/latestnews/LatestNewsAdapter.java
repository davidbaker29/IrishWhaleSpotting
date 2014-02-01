package com.baker.david.irishwhalespotting.ui.latestnews;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.domain.LatestNewsItem;

public class LatestNewsAdapter extends BaseAdapter {

	private List<LatestNewsItem> latestNewsItems;
	private Context context;
	private int layoutResourceId;

	public LatestNewsAdapter(Context context, int layoutResourceId, 
			List<LatestNewsItem> latestNewsItems){
		super();
	    this.context = context;
	    this.layoutResourceId = layoutResourceId;
	    this.latestNewsItems = latestNewsItems;
	}
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.latestNewsItems.size();
	}

	@Override
	public LatestNewsItem getItem(int position) {
		// TODO Auto-generated method stub
		return this.latestNewsItems.get(position);
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

		//Below here was previously outside the if statement so the view
		//was having duplicate items added. An alternative methid is to 
		//clear the adapter and redraw. Trade-off between the extra effort involved in that
		//vs the risk now that a new item added to the RSS feed won't appear in the app straight
		//away. But new items are only asdded infrequently. If it was an app
		//like RTE News it would be different
		LatestNewsItem latestNewsItem = this.getItem(position);
		String title = latestNewsItem.getTitle();
        String pubDate = latestNewsItem.getPublicationDate();
       
        // get the reference of textViews
        TextView titleView= (TextView) convertView.findViewById(R.id.latestNewsTitle);
        TextView dateView=(TextView)convertView.findViewById(R.id.latestNewsDate);
        
        titleView.setText(title);
        dateView.setText(pubDate);

        return convertView;
	}

}

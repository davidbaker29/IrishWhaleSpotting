package com.baker.david.irishwhalespotting.ui.species;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.domain.Whale;

public class SpeciesAdapter extends BaseAdapter {

	private List<Whale> whales;
	private Context context;
	private int layoutResourceId;

	public SpeciesAdapter(Context context, int layoutResourceId, 
			List<Whale> whales){
		super();
	    this.context = context;
	    this.layoutResourceId = layoutResourceId;
	    this.whales = whales;
	}
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.whales.size();
	}

	@Override
	public Whale getItem(int position) {
		// TODO Auto-generated method stub
		return this.whales.get(position);
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
		Whale whale = this.getItem(position);
       
        TextView nameView= (TextView) convertView.findViewById(R.id.whaleName);
	        
        nameView.setText(whale.getName());
        
        //TODO - just replace this string with the correct one for each whale
        
        int whaleImageId =  context.getResources().getIdentifier(whale.getImgFileName(),
                "drawable", context.getPackageName());
        
        nameView.setCompoundDrawablesWithIntrinsicBounds(whaleImageId,0,0,0);

        return convertView;
	}

}

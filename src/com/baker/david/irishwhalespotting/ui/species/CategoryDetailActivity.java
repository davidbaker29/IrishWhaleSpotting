package com.baker.david.irishwhalespotting.ui.species;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.baker.david.irishwhalespotting.R;

/**
 * An activity representing a single Whale detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link CategoryListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link CategoryDetailFragment}.
 */
public class CategoryDetailActivity extends FragmentActivity {
	
	private String selectedWhale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_detail);
		
        // Set up action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			
			arguments.putString(CategoryDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(CategoryDetailFragment.ARG_ITEM_ID));
			
			this.selectedWhale = getIntent()
					.getStringExtra("selectedWhale");
			
			arguments.putString("selectedWhale", selectedWhale);
			
			CategoryDetailFragment fragment = new CategoryDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.whale_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
        case android.R.id.home:
            // This is called when the Home (Up) button is pressed in the action bar.
            // Create a simple intent that starts the hierarchical parent activity and
            // use NavUtils in the Support Package to ensure proper handling of Up.
        	
            Intent upIntent = new Intent(this, CategoryListActivity.class);
            upIntent.putExtra("selectedWhale", this.selectedWhale);
   
            // This activity is part of the application's task, so simply
            // navigate up to the hierarchical parent activity.
            NavUtils.navigateUpTo(this, upIntent);
            return true;
		}
		return super.onOptionsItemSelected(item);
		}
}

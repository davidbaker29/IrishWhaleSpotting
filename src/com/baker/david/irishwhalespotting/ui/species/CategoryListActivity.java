package com.baker.david.irishwhalespotting.ui.species;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.baker.david.irishwhalespotting.MainActivity;
import com.baker.david.irishwhalespotting.R;

/**
 * 
 * 
 * An activity representing a list of Whale categories. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link CategoryDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CategoryListFragment} and the item details (if present) is a
 * {@link CategoryDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link CategoryListFragment.Callbacks} interface to listen for item selections.
 */
public class CategoryListActivity extends FragmentActivity implements
		CategoryListFragment.Callbacks {

	public static final String ARG_SECTION_NUMBER = "section_number";

	private static final String WEB_ACCESS = "webAccess";

	private static final String TAB_ID = "tabId";
	
	private boolean webAccess;
	private int tabId;
	
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private String selectedWhale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		
        // Set up action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		Bundle b = getIntent().getExtras();
		this.selectedWhale = b.getString("selectedWhale");
		
		if (findViewById(R.id.whale_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched. The below code also
			//sets the first item n the list to look like it's selected
			((CategoryListFragment) getSupportFragmentManager().findFragmentById(
					R.id.whale_list)).setActivateOnItemClick(true);
			
			((CategoryListFragment) getSupportFragmentManager().findFragmentById(
					R.id.whale_list)).getListView().requestFocusFromTouch();
			
			((CategoryListFragment) getSupportFragmentManager().findFragmentById(
					R.id.whale_list)).getListView().setSelection(0);
			
			//Simulate a click on the first item in the list so as to auto-populate 
			//the details pane on a tablet with the first details section
			((CategoryListFragment) getSupportFragmentManager().findFragmentById(
					R.id.whale_list)).getListView().performItemClick(((CategoryListFragment) getSupportFragmentManager().findFragmentById(
							R.id.whale_list)).getListView().getAdapter().getView(0, null, null), 0, 0);
		}

	}

	/**
	 * Callback method from {@link CategoryListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(CategoryDetailFragment.ARG_ITEM_ID, id);
			arguments.putString("selectedWhale", this.selectedWhale);
			CategoryDetailFragment fragment = new CategoryDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.whale_detail_container, fragment)
					.commit();
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CategoryDetailActivity.class);
			detailIntent.putExtra(CategoryDetailFragment.ARG_ITEM_ID, id);
			detailIntent.putExtra("selectedWhale", this.selectedWhale);
			startActivity(detailIntent);
		}
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	
                Intent upIntent = new Intent(this, MainActivity.class);
                upIntent.putExtra(WEB_ACCESS,this.webAccess); 
                upIntent.putExtra(TAB_ID,this.tabId); 
                NavUtils.navigateUpTo(this, upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

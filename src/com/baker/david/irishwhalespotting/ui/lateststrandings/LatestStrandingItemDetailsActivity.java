package com.baker.david.irishwhalespotting.ui.lateststrandings;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.baker.david.irishwhalespotting.MainActivity;
import com.baker.david.irishwhalespotting.R;
import com.baker.david.irishwhalespotting.dao.LatestStrandingManager;
import com.baker.david.irishwhalespotting.domain.Constants;
import com.baker.david.irishwhalespotting.domain.LatestStrandingItem;

public class LatestStrandingItemDetailsActivity extends Activity {

	private boolean webAccess;
	private int tabId;
	private ShareActionProvider shareActionProvider;
	private LatestStrandingItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_latest_stranding_item_details);
		
        // Set up action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		Bundle b = getIntent().getExtras();
		String title = b.getString(Constants.SELECTED_STRANDING_ITEM);
		this.webAccess = b.getBoolean(Constants.WEB_ACCESS);
		this.tabId = b.getInt(Constants.ARG_SECTION_NUMBER);
		this.item = LatestStrandingManager.getStrandingItem(title);

		LinearLayout ll = (LinearLayout) findViewById(R.id.latestStrandingItemDetails);
		
		TextView tv1 = new TextView(this);
	    tv1.setText(item.getTitle() + "\n");
	    tv1.setTextSize(16);
	    int royalBlue = getResources().getColor(R.color.royal_blue);
	    tv1.setTextColor(ColorStateList.valueOf(royalBlue));
	    ll.addView(tv1);
	    
		TextView tv2 = new TextView(this);
	    tv2.setText(item.getPublicationDate() + "\n");
	    ll.addView(tv2);
	    
		TextView tv3 = new TextView(this);
		tv3.setMovementMethod(LinkMovementMethod.getInstance());
	    tv3.setText(Html.fromHtml("<a href=\"" + item.getLink()
	    		+ "\">" + "View Full Details</a>"));
	    tv3.setAutoLinkMask(Linkify.ALL);
	    tv3.setLinksClickable(true);
	    ll.addView(tv3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.latest_stranding_item_details, menu);
		
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share_stranding_item);

        // Fetch and store ShareActionProvider
        this.shareActionProvider = (ShareActionProvider) item.getActionProvider();
		
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
	    shareIntent.setType("text/plain");
	    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "IWDG Stranding Item");
	    
	    if (shareIntent != null) {
	    	shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi \n\nI'd like to share " +
	    			"the following stranding details " +
				"from the IWDG website with you: \n\n" +
				this.item.getLink());
	    	
	    	this.setShareIntent(shareIntent);
	    } 
		
		return true;
	}
	

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (this.shareActionProvider != null) {
            this.shareActionProvider.setShareIntent(shareIntent);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, MainActivity.class);
                upIntent.putExtra(Constants.WEB_ACCESS,this.webAccess); 
                upIntent.putExtra(Constants.TAB_ID,this.tabId); 
                NavUtils.navigateUpTo(this, upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
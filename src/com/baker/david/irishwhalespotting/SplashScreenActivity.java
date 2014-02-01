package com.baker.david.irishwhalespotting;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baker.david.irishwhalespotting.dao.LatestNewsManager;
import com.baker.david.irishwhalespotting.domain.Constants;
import com.baker.david.irishwhalespotting.domain.LatestNewsItem;
import com.baker.david.irishwhalespotting.parser.LatestNewsSaxFeedParser;
import com.baker.david.irishwhalespotting.parser.NoWebAccessException;

public class SplashScreenActivity extends Activity {
	
	static ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		SplashScreenActivity.progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
		
		/**
         * Showing splashscreen while making network calls to download necessary
         * data before launching the app Will use AsyncTask to make http call
         */
        new PrefetchData().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
	
	/**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {
    	
    	List<LatestNewsItem> latestNewsItems = null;
    	private boolean webAccess = true;
    	
        @Override
        protected void onPreExecute() {
        	//Toast.makeText(SplashScreenActivity.this, "Loading data...", Toast.LENGTH_SHORT).show();
        	SplashScreenActivity.progressBar.setVisibility(ProgressBar.VISIBLE);
            super.onPreExecute();     
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	 
    		try {
    			latestNewsItems = new LatestNewsSaxFeedParser().parse();
    			LatestNewsManager.addLatestNewsItems(latestNewsItems);
    		}
    		catch (NoWebAccessException e) {
    			this.webAccess = false;
    		} 
			return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
        	super.onPostExecute(result);
        	
        	SplashScreenActivity.progressBar.setVisibility(ProgressBar.GONE);
        	
        	if (!this.webAccess){
        		Toast.makeText(SplashScreenActivity.this, "No Internet connection available. Only " +
           		 		"limited functionality can be provided",
     	                Toast.LENGTH_LONG).show();
        	}
        	
            // After completing http call
            // will close this activity and lauch main activity
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            i.putExtra(Constants.WEB_ACCESS, webAccess);
            startActivity(i);
 
            // close this activity
            finish();
        }
    }
}

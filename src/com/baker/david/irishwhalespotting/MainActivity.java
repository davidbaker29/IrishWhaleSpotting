/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baker.david.irishwhalespotting;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baker.david.irishwhalespotting.domain.Constants;
import com.baker.david.irishwhalespotting.ui.latestnews.LatestNewsFragment;
import com.baker.david.irishwhalespotting.ui.latestsightings.LatestSightingsFragment;
import com.baker.david.irishwhalespotting.ui.lateststrandings.LatestStrandingsFragment;
import com.baker.david.irishwhalespotting.ui.report.ReportSightingsFragment;
import com.baker.david.irishwhalespotting.ui.report.ReportStrandingsFragment;
import com.baker.david.irishwhalespotting.ui.species.SpeciesFragment;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
	
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

	private DrawerLayout drawerLayout;

	private ListView drawerList;

	private static boolean webAccess = true;
	private static int preSelectedTabId = 0;
	
	String[] mainSections = new String[6];

	private DrawerListener drawerToggle;

	private ActionBar actionBar;

    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.setUpNavigationDrawer();
        
     // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        
        // Set up the action bar.
        this.actionBar = getActionBar();
        
        actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle b = getIntent().getExtras();

		MainActivity.webAccess = b.getBoolean(Constants.WEB_ACCESS);
		MainActivity.preSelectedTabId = b.getInt(Constants.TAB_ID);
		
        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i <= mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        
        mViewPager.setCurrentItem(preSelectedTabId);
        
        if (!webAccess){
        	mViewPager.setCurrentItem(5);
        }

    }
    
	private void setUpNavigationDrawer() {
		this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    this.drawerList = (ListView) findViewById(R.id.left_drawer);
	    
	    mainSections[0] = getString(R.string.section_latest_news_heading);
	    mainSections[1] = getString(R.string.section_latest_sightings_heading);
	    mainSections[2] = getString(R.string.section_latest_strandings_heading);
	    mainSections[3] = getString(R.string.section_report_sighting_heading);
	    mainSections[4] = getString(R.string.section_report_stranding_heading);
	    mainSections[5] = getString(R.string.section_species_heading);
	    
	    // Set the adapter for the list view
	    this.drawerList.setAdapter(new ArrayAdapter<String>(this,
	            R.layout.drawer_list_item, mainSections));
	    // Set the list's click listener
	    this.drawerList.setOnItemClickListener(new DrawerItemClickListener());
	    
	    //this.title = drawerTitle = getTitle();
	    this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    
	    this.drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
	            R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
	
			/** Called when a drawer has settled in a completely closed state. */
	        public void onDrawerClosed(View view) {
	            super.onDrawerClosed(view);
	            //getActionBar().setTitle("Close Drawer");
	            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	        }
	
	        /** Called when a drawer has settled in a completely open state. */
	        public void onDrawerOpened(View drawerView) {
	            super.onDrawerOpened(drawerView);
	            invalidateOptionsMenu(); 
	        }
	        
	    };
	
	    // Set the drawer toggle as the DrawerListener
	    this.drawerLayout.setDrawerListener(this.drawerToggle);
		
	}

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public static boolean isWebAccess() {
		return webAccess;
	}


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        ((ActionBarDrawerToggle) this.drawerToggle).syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((ActionBarDrawerToggle) this.drawerToggle).onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (((ActionBarDrawerToggle) this.drawerToggle).onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
    	
    	this.actionBar.setSelectedNavigationItem(position);

        // Highlight the selected item, update the title, and close the drawer
        this.drawerList.setItemChecked(position, true);
        //setTitle(mainSections[position]);
        this.drawerLayout.closeDrawer(this.drawerList);
    }
     
    
    /********************
     * Inner classes
     ******************/
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
	/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
               
        @Override
        public Fragment getItem(int i) {
        	
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    Fragment fragment0 = new LatestNewsFragment();
                    Bundle args0 = new Bundle();
                    args0.putInt(Constants.ARG_SECTION_NUMBER, i);
                    args0.putBoolean(Constants.WEB_ACCESS, MainActivity.isWebAccess());
                    fragment0.setArguments(args0);
                    return fragment0;
                case 1:
                	Fragment fragment1 = new LatestSightingsFragment();
                    Bundle args1 = new Bundle();
                    args1.putInt(Constants.ARG_SECTION_NUMBER, i);
                    args1.putBoolean(Constants.WEB_ACCESS, MainActivity.isWebAccess());
                    fragment1.setArguments(args1);
                    return fragment1;
                case 2:
                	Fragment fragment2 = new LatestStrandingsFragment();
                    Bundle args2 = new Bundle();
                    args2.putInt(Constants.ARG_SECTION_NUMBER, i);
                    args2.putBoolean(Constants.WEB_ACCESS, MainActivity.isWebAccess());
                    fragment2.setArguments(args2);
                    return fragment2;
                case 3:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment = new ReportSightingsFragment();
                    Bundle args = new Bundle();
                    args.putInt(Constants.ARG_SECTION_NUMBER, i);
                    args.putBoolean(Constants.WEB_ACCESS, MainActivity.isWebAccess());
                    fragment.setArguments(args);
                    return fragment;
                case 4:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment4 = new ReportStrandingsFragment();
                    Bundle args4 = new Bundle();
                    args4.putInt(Constants.ARG_SECTION_NUMBER, i);
                    args4.putBoolean(Constants.WEB_ACCESS, MainActivity.isWebAccess());
                    fragment4.setArguments(args4);
                    return fragment4;
                case 5:
                	Fragment fragment3 = new SpeciesFragment();
                    Bundle args3 = new Bundle();
                    args3.putInt(Constants.ARG_SECTION_NUMBER, i);
                    args3.putBoolean(Constants.WEB_ACCESS, MainActivity.isWebAccess());
                    fragment3.setArguments(args3);
                    return fragment3;               	
            }
			return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch(position){
        		case 0:	
        			return "Latest News";
                case 1:
                    return "Latest Sightings";
                case 2:
                	return "Latest Strandings";
                case 3:
                    return "Report Sighting";
                case 4:
                	return "Report Stranding";
                case 5:
                	return "Species";
        	}
        return "";  
        }
     }
}

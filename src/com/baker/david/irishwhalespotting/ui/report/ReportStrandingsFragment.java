package com.baker.david.irishwhalespotting.ui.report;

import com.baker.david.irishwhalespotting.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportStrandingsFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    
		View rootView = inflater.inflate(R.layout.fragment_section_report_strandings, 
				container, false);
        
	    return rootView;
	}

}
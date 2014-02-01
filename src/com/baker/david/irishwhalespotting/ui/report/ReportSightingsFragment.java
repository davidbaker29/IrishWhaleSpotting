package com.baker.david.irishwhalespotting.ui.report;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baker.david.irishwhalespotting.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;


public class ReportSightingsFragment extends Fragment implements 
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener,
	LocationListener,
	OnClickListener {

	private static final int GALLERY_ACTIVITY_CODE = 321;
	private LocationClient locationClient;
	LocationRequest locationRequest;
	private Location currentLocation;
	private double latitude = 0.0;
	private double longitude = 0.0;
	private String selectedWhaleImage = "";
	private Uri selectedWhaleImageUri;
	
	private static final String PROVIDER = "flp";
	private static final double LAT = 37.377166;
	private static final double LNG = -122.086966;
	private static final float ACCURACY = 3.0f;
	
	 // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 10;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 5;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	
    // Global constants
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    
	private static final String USER_VALIDATION_ERROR = "Validation Error";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    
		View rootView = inflater.inflate(R.layout.fragment_section_report_sightings, container, false);
		
		Button btnSubmitSighting = (Button) rootView.findViewById(R.id.btnSubmitSighting);
		btnSubmitSighting.setOnClickListener(this);
        
		Button btnDate = (Button) rootView.findViewById(R.id.btnDate);
		btnDate.setOnClickListener(this);
		
		Button btnPhoto = (Button) rootView.findViewById(R.id.btnPhoto);
		btnPhoto.setOnClickListener(this);
		
		this.populateDateBox(rootView);
		this.tickNonIwdgMemberBox(rootView);
		this.populateOpticsSpinner(rootView);
		this.populateCountySpinner(rootView);
		this.setupGps();
        
	    return rootView;
	}
	
	private void populateDateBox(View rootView) {
		
		Calendar c = Calendar.getInstance();

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df.format(c.getTime());
		
		TextView sightingDate = (TextView) rootView.findViewById(R.id.sightingDate);
		sightingDate.setText(formattedDate);
	}

	private void tickNonIwdgMemberBox(View rootView) {
		// TODO Auto-generated method stub
		RadioGroup iwdgMember = (RadioGroup) rootView.findViewById(R.id.iwdg_member);
		iwdgMember.check(R.id.iwdg_member_no);
	}
	
	private void populateCountySpinner(View rootView) {
		Spinner spinner = (Spinner) rootView.findViewById(R.id.county_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.county_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);	
	}

	private void populateOpticsSpinner(View rootView) {
		Spinner spinner = (Spinner) rootView.findViewById(R.id.optics_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.optics_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);	
	}
	
	private void populateLatitudeAndLongitudeText() {
		//Needs to be after the setupGos method so we have populated lat & lon
        EditText latitudeText = (EditText) getActivity().findViewById(R.id.latitude);
        latitudeText.setText(String.valueOf(this.latitude));
        
        EditText longitudeText = (EditText) getActivity().findViewById(R.id.longitude);
        longitudeText.setText(String.valueOf(this.longitude));
	}

	private String gatherUserInput(){
 
		String phone = ((EditText) getActivity().findViewById(R.id.phoneNumber)).getText().toString();
		
		RadioGroup iwdgMemberRadioGroup = 
				(RadioGroup) getActivity().findViewById(R.id.iwdg_member);
		
		String iwdgMember = "";
		
		if(iwdgMemberRadioGroup.getCheckedRadioButtonId() != -1){
		    int selectedButtonId = iwdgMemberRadioGroup.getCheckedRadioButtonId();
		    View selectedRadioButton = iwdgMemberRadioGroup.findViewById(selectedButtonId);
		    int yesOrNoId = iwdgMemberRadioGroup.indexOfChild(selectedRadioButton);
		    iwdgMember = ((RadioButton) iwdgMemberRadioGroup.getChildAt(yesOrNoId))
		    		.getText().toString();
		}
		
		//String personName = ((EditText) getActivity().findViewById(R.id.personName)).getText().toString();
		
		TextView sightingDateWidget = ((TextView) getActivity().findViewById(R.id.sightingDate));
		String sightingDate = sightingDateWidget.getText().toString();
		
		EditText locationWidget = ((EditText) getActivity().findViewById(R.id.location));
		String sightingLocation = locationWidget.getText().toString();
		if ((sightingLocation == null) || (sightingLocation.equalsIgnoreCase(""))){
			locationWidget.setError("Sighting location required");
			locationWidget.requestFocus();
			return USER_VALIDATION_ERROR;
		}
		
		Spinner countyWidget = ((Spinner) getActivity().findViewById(R.id.county_spinner));
		String sightingCounty = countyWidget.getSelectedItem().toString();
		if ((sightingCounty == null) || (sightingCounty.equalsIgnoreCase(""))){
			countyWidget.setFocusable(true); 
			countyWidget.setFocusableInTouchMode(true);
			countyWidget.requestFocus();
			DialogFragment countyFragment = new NoCountySelectedFragment();
			countyFragment.show(getFragmentManager(), "countyPicker");
			return USER_VALIDATION_ERROR;
		}
		
		//String email = ((EditText) getActivity().findViewById(R.id.email)).getText().toString();
		
		EditText speciesWidget = ((EditText) getActivity().findViewById(R.id.species));
		String species = speciesWidget.getText().toString();
		if ((species == null) || (species.equalsIgnoreCase(""))){
			speciesWidget.setError("Species required");
			speciesWidget.requestFocus();
			return USER_VALIDATION_ERROR;
		}
		
		EditText numberSeenWidget = ((EditText) getActivity().findViewById(R.id.numberSeen));
		String numberSeen = numberSeenWidget.getText().toString();
		if ((numberSeen == null) || (numberSeen.equalsIgnoreCase(""))){
			numberSeenWidget.setError("No. seen required");
			numberSeenWidget.requestFocus();
			return USER_VALIDATION_ERROR;
		}
		
		String optics = ((Spinner) getActivity().findViewById(R.id.optics_spinner)).getSelectedItem().toString();
		String notes = ((EditText) getActivity().findViewById(R.id.notes)).getText().toString();
		
		RadioGroup submissionTypeRadioGroup = 
				(RadioGroup) getActivity().findViewById(R.id.radio_submission_type);
		
		String submissionType = "";
		
		if(submissionTypeRadioGroup.getCheckedRadioButtonId() != -1){
		    int selectedButtonId = submissionTypeRadioGroup.getCheckedRadioButtonId();
		    View selectedRadioButton = submissionTypeRadioGroup.findViewById(selectedButtonId);
		    int radioId = submissionTypeRadioGroup.indexOfChild(selectedRadioButton);
		    submissionType = ((RadioButton) submissionTypeRadioGroup.getChildAt(radioId))
		    		.getText().toString();
		}
		
		String allInput = 
				"Phone: " + phone + "\n" +
				"IWDG member: " + iwdgMember + "\n" +
				"Sighting date: " + sightingDate + "\n" +
				"Sighting location: " + sightingLocation + "\n" +
				"Sighting county: " + sightingCounty + "\n" +
				"Sighting latitude: " + String.valueOf(this.currentLocation.getLatitude()) + "\n" +
				"Sighting longitude: " + String.valueOf(this.currentLocation.getLongitude()) + "\n" +
				"Submission Type: " + submissionType + "\n" +
				"Species seen: " + species + "\n" +
				"Number seen: " + numberSeen + "\n" +
				"Optics: " + optics + "\n" +				
				"Notes: " + notes;
				
		
		return allInput;
	}
	
	private void sendEmail(String userInput) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"davidbaker29@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Whale Sighting");
		i.putExtra(Intent.EXTRA_TEXT, userInput);
		
		//File imageFile = new File(this.selectedWhaleImage);
		//Uri imageFileUri = Uri.fromFile(imageFile);
		
		Uri imageFileUri = this.selectedWhaleImageUri;
		i.putExtra(Intent.EXTRA_STREAM, imageFileUri);
		
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} 
		catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnDate:				
			    Location testLocation = createLocation(LAT, LNG, ACCURACY);
			    this.locationClient.setMockLocation(testLocation);
			    
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getFragmentManager(), "datePicker");
			    // Example of creating a new Location from test data
				break;
			case R.id.btnSubmitSighting:
				//save to a file, send a text or email
				String userInput = this.gatherUserInput();
				if (!userInput.equalsIgnoreCase(USER_VALIDATION_ERROR)){
					this.sendEmail(userInput);
				}
				break;
			case R.id.btnPhoto:
				//save to a file, send a text or email
				// TODO Auto-generated method stub
//				Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//				galleryIntent.setType("image/*");
//				galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
//				
				Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				 
				try {
				    //startActivity(Intent.createChooser(galleryIntent, "Choose photo..."));
				    startActivityForResult(galleryIntent, GALLERY_ACTIVITY_CODE);
				} 
				catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getActivity(), "There are no photo selection apps installed.", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
	
	@Override
	public void onActivityResult (int requestCode, int resultCode, Intent data){
		if (requestCode == GALLERY_ACTIVITY_CODE && resultCode == Activity.RESULT_OK
	            && data != null) {
	        Uri selectedImage = data.getData();
	        
	        this.selectedWhaleImageUri = selectedImage;

	        this.selectedWhaleImage  = selectedImage.getPath();
	        Toast.makeText(getActivity(), "Photo has been selected", Toast.LENGTH_SHORT).show();
	    }
	}
	
	public static class DatePickerFragment extends DialogFragment
    	implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
			}
			
			public void onDateSet(DatePicker view, int year, int month, int day) {
				TextView sightingDate = (TextView) getActivity().findViewById(R.id.sightingDate);
				int theActualMonth = month + 1;
				String strMonth = "";
				if (theActualMonth < 10){
					strMonth = "0" + String.valueOf(theActualMonth);
				}
				else {
					strMonth = String.valueOf(theActualMonth);
				}
				
				sightingDate.setText(day + "-" + strMonth + "-" + year);
			}
	}
	
	public static class NoCountySelectedFragment extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Create a new instance of DatePickerDialog and return it
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());
					
			// set title
			alertDialogBuilder.setTitle("Select County");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Please select a county!")
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						dialog.dismiss();
					}
				  });
 
				// create alert dialog
				AlertDialog countyDialog = alertDialogBuilder.create();
				
				return countyDialog;
		}
	}
	
	/***********************
	 * GPS Stuff from here
	 ***********************/
	
	private void setupGps() {
		
		Log.d("ReportSightingsFragment", "Setting up GPS");
		 /*
	     * Create a new location client, using the enclosing class to
	     * handle callbacks.
	     */
	    this.locationClient = new LocationClient(getActivity(),this,this);		
	    
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {	
	    this.currentLocation = locationClient.getLastLocation();
	    
	    this.locationClient.setMockMode(true);
	    
	    if (this.currentLocation == null){
	    	//try a second time - it's possible, but rare, to get a null pointer
	    	// from getLastLocation
	    	Log.d("**ReportSightingsFragment****", "currentLocation is null!");
	    	this.currentLocation = locationClient.getLastLocation();
	    }
	    
	    if (this.currentLocation != null){
	    	this.latitude = this.currentLocation.getLatitude();
	    	this.longitude = this.currentLocation.getLongitude();
	    }
	    this.populateLatitudeAndLongitudeText();
	    
	    this.locationRequest = LocationRequest.create();
	    // Use high accuracy
	    this.locationRequest.setPriority(
	            LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
	    // Set the update interval to 5 seconds
	    this.locationRequest.setInterval(UPDATE_INTERVAL);
	    // Set the fastest update interval to 1 second
	    this.locationRequest.setFastestInterval(FASTEST_INTERVAL);
	    
	    this.locationClient.requestLocationUpdates(locationRequest, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		
		// Report to the UI that the location was updated
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
		
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
	}
	
	public Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
	
	/*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
	@Override
	public void onDisconnected() {
		 Toast.makeText(getActivity(), "Disconnected GPS. Please re-connect.",
	                Toast.LENGTH_SHORT).show();
		
	}
	
	
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
	     * Google Play services can resolve some errors it detects.
	     * If the error has a resolution, try sending an Intent to
	     * start a Google Play services activity that can resolve
	     * error.
	     */
		Log.d("**ReportSightingsFragment****", "In onConnectionFailed!");
		
	    if (connectionResult.hasResolution()) {
	        try {
	        	Log.d("**ReportSightingsFragment****", "In connectionResult.hasResolution()!");
	            // Start an Activity that tries to resolve the error
	            connectionResult.startResolutionForResult(
	            		getActivity(),
	                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
	            /*
	             * Thrown if Google Play services canceled the original
	             * PendingIntent
	             */
	        } catch (IntentSender.SendIntentException e) {
	            // Log the error
	            e.printStackTrace();
	        }
	    } else {
	        /*
	         * If no resolution is available, display a dialog to the
	         * user with the error.
	         */
	        GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST).show();
	    }
		
	}
	
	/*
     * Called when the Activity becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Connect the client.
        this.locationClient.connect();
    }
    
//    @Override
//    public void onPause() {
//        super.onStart();
//        this.locationClient.removeLocationUpdates(this);
//    }
//    
//    @Override
//    public void onResume() {
//        super.onStart();
//        // Connect the client.
//        if (!this.locationClient.isConnected()){
//        	this.locationClient.connect();
//        }
//        this.locationClient.requestLocationUpdates(this.locationRequest, this);
//    }
    
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
    	
    	Log.d("**ReportSightingsFragment****", "In onStop!");
        
        /*
         * After disconnect() is called, the client is
         * considered "dead".
         */
        this.locationClient.disconnect();
        
        super.onStop();
    }

}
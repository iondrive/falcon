package com.simiansoft.falcon;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private INetworkCommunicationProvider net;
	private ILocalStorageProvider data;
	private LocationManager locationManager;
	private Route route;
	private Run run;
	private LoggingStatus status;
	private final String TAG = "Falcon";
	
	private Button logButton;
	private Button startButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // TODO am i overwriting stuff here?
        status = LoggingStatus.Stopped;
        
        logButton = (Button)findViewById(R.id.button4);
        logButton.setEnabled(false);
        startButton = (Button)findViewById(R.id.toggleButton1);
        startButton.setEnabled(false);
        
        net = new RetrofitDataProvider();
        data = new SQLiteLocalStorageManager(this);
               
        
        route = new Route();
        run = new Run();
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
        	public void onLocationChanged(Location location) {
        		if (!startButton.isEnabled()) {
        			startButton.setEnabled(true);
        		}
        		if (status != LoggingStatus.Stopped) {
	        		run.AddPoint(location.getLongitude(), location.getLatitude(), location.getAltitude(), location.getTime(), status);
	            	Log.d(TAG, run.Data.get(run.Data.size() -1).toString());
        		}
        	}
        	
        	public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {
            	Log.d(TAG, "onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
            	Log.d(TAG, "onProviderDisabled");
            }
        	
        };
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
        	startButton.setEnabled(true);
        }
    }
    
    public void onLogRun(View v) {
    	// TODO: handle logic surrounding creating new runs and changing UI elements to reflect like 'START NEW' or something
    	if (v.getId() == R.id.button4) {
    		Button logButton = (Button)findViewById(R.id.button4);
    		logButton.setEnabled(false);
    		Log.d(TAG, run.toJSON());
    		
    		// Async post the run
    		new AsyncTask<Run, Void, Void>() {
    			@Override
    			protected Void doInBackground(Run... runs) {
    				INetworkCommunicationProvider http = new RetrofitDataProvider();
    	    		http.postRun(runs[0]);
    				return null;
    			}
    		}.execute(run);
    		run = new Run();
    		status = LoggingStatus.Stopped;
    	}
    }
    
    public void onToggleClicked(View view) {
    	LoggingStatus clickStatus;
    	if (((ToggleButton)view).isChecked()) {
    		status = LoggingStatus.Active;
    		clickStatus = LoggingStatus.ClickedStart;
    	} else {
    		status = LoggingStatus.Paused;
    		clickStatus = LoggingStatus.ClickedStop;
    		Button logButton = (Button)findViewById(R.id.button4);
    		logButton.setEnabled(true);
    	}
		Location last = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		run.AddPoint(last.getLongitude(), last.getLatitude(), last.getAltitude(), new Date().getTime(), clickStatus);
		run.Route = RouteMatchAlgorithm.MatchRoute(run);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
}

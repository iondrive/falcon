package com.simiansoft.falcon;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private IDataProvider data;
	private Route route;
	private final String TAG = "Falcon";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        data = new RetrofitDataProvider();
        route = new Route();
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
        	public void onLocationChanged(Location location) {
        		// log the point
        		route.AddPoint(location.getLongitude(), location.getLatitude(), location.getTime());
            	Log.d(TAG, route.toString());
        	}
        	
        	public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {
            	Log.d(TAG, "onProviderEnabled");
            	Log.d(TAG, route.toString());
            }

            public void onProviderDisabled(String provider) {
            	Log.d(TAG, "onProviderDisabled");
            	Log.d(TAG, route.toString());
            }
        	
        };
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        
    }
    
    public void onLogRun(View v) {
    	if (v.getId() == R.id.button4) {
    		data.createRoute(route);
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
}

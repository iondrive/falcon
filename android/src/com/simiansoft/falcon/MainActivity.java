package com.simiansoft.falcon;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private LocationManager locationManager;
	private LocationListener locationListener;
	private StateManager _stateManager;
	private RefDataManager _refDataManager;
	private final String TAG = "Falcon";
	private int _loggingInterval;
	private List<Route> _routes;
	private Context _context;
	
	private Button _logButton;
	private Button _startButton;
	private SeekBar _seekBar;
	private Spinner _spinner;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _stateManager = new StateManager();
        _refDataManager = new RefDataManager(this);
        _context = this;
        
        
        // TODO am i overwriting stuff here?
        _stateManager.Status = LoggingStatus.Stopped;
        _loggingInterval = 3000;
        
        _logButton = (Button)findViewById(R.id.button4);
        _logButton.setEnabled(false);
        _startButton = (Button)findViewById(R.id.toggleButton1);
        _startButton.setEnabled(false);
        _seekBar = (SeekBar)findViewById(R.id.seekBar1);
        _seekBar.setMax(20000);
        _seekBar.setProgress(_loggingInterval);
        _spinner = (Spinner)findViewById(R.id.spinner1);
        
        // Async task to get data and bind to spinner (consider prepopulating with available data)
        new AsyncTask<Void, Void, List<Route>>() {
        	protected List<Route> doInBackground(Void... args) {
        		return _refDataManager.UpdateAndGetRoutes();
        	}
        	protected void onPostExecute(List<Route> routes) {
        		// Bind the spinner to the routes
        		_routes = routes;
        		_spinner.setAdapter(new RouteAdapter(_context, R.layout.route_item, routes));
        	}
        }.execute();
        
        
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
        	public void onLocationChanged(Location location) {
        		if (!_startButton.isEnabled()) {
        			_startButton.setEnabled(true);
        		}
        		if (_stateManager.Status != LoggingStatus.Stopped) {
	        		_stateManager.logPoint(location.getLongitude(), location.getLatitude(), location.getAltitude(), location.getTime());
        		}
        	}
        	
        	public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        	
        };
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, _loggingInterval, 0, locationListener);
        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
        	_startButton.setEnabled(true);
        }
        

        _seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int arg1, boolean arg2) {
				_loggingInterval = arg1;
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, _loggingInterval, 0, locationListener);
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {}
        	@Override
        	public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
    
    public void onLogRun(View v) {
    	// TODO: handle logic surrounding creating new runs and changing UI elements to reflect like 'START NEW' or something
    	if (v.getId() == R.id.button4) {
    		_logButton.setEnabled(false);
    		
    		// Async post the run
    		new AsyncTask<Run, Void, Void>() {
    			@Override
    			protected Void doInBackground(Run... runs) {
    				INetworkCommunicationProvider http = new RetrofitDataProvider();
    	    		http.postRun(runs[0]);
    				return null;
    			}
    		}.execute(_stateManager.calculateRun());
    		_stateManager.Status = LoggingStatus.Stopped;
    	}
    }
    
    public void onToggleClicked(View view) {
    	LoggingStatus clickStatus;
    	if (((ToggleButton)view).isChecked()) {
    		_stateManager.Status = LoggingStatus.Active;
    		clickStatus = LoggingStatus.ClickedStart;
    	} else {
    		_stateManager.Status = LoggingStatus.Paused;
    		clickStatus = LoggingStatus.ClickedStop;
    		_logButton.setEnabled(true);
    	}
		Location last = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		_stateManager.logPoint(last.getLongitude(), last.getLatitude(), last.getAltitude(), new Date().getTime(), clickStatus);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
}

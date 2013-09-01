package com.simiansoft.falcon;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

public class RefDataManager {
	private ILocalStorageProvider _data;
	private INetworkCommunicationProvider _net;
	
	public RefDataManager(Context context) {
		_data = new SQLiteLocalStorageManager(context);
		_net = new RetrofitDataProvider();
	}
	
	public List<Route> UpdateAndGetRoutes() {
		List<Route> routes = _net.getRoutes(_data.GetMostRecentTimestamp());
		Route r;
		for (Iterator<Route> i = routes.iterator(); i.hasNext();) {
			r = i.next();
			_data.SaveRoute(r);
		}
		return _data.GetRoutes();
		
	}
	
	public List<Route> GetRoutes() {
		return _data.GetRoutes();
	}
}

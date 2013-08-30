package com.simiansoft.falcon;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import android.os.AsyncTask;

public class RetrofitDataProvider implements INetworkCommunicationProvider{
	private static String REST_URL = "http://68.173.39.116:3000";
	
	interface FalconApi {
		@GET("/api/routes?timestamp={timestamp}")
		List<SimpleRoute> getRouteMetadata(@Path("timestamp") long timestamp);
		
		@GET("/api/routes/{id}")
		Route getRoute(@Path("id") String id);
		
		@POST("/api/runs")
		String postRun(@Body Run run);
	}
	
	RestAdapter _restAdapter;
	private FalconApi _api;
	
	public RetrofitDataProvider() {
		_restAdapter = new RestAdapter.Builder().setServer(REST_URL).build();
		_api = _restAdapter.create(FalconApi.class);
	}
	
	@Override
	public List<SimpleRoute> getRouteMetadata(long timestamp) {
		return _api.getRouteMetadata(timestamp);
	}

	@Override
	public Route getRoute(String id) {
		return _api.getRoute(id);
	}

	@Override
	public void postRun(Run run) {
		
		String res = _api.postRun(run);
	}
}

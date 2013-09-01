package com.simiansoft.falcon;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public class RetrofitDataProvider implements INetworkCommunicationProvider{
	private static String REST_URL = "http://68.173.39.116:3000";
	private String _usertoken;
	
	interface FalconApi {
		@GET("/api/routes")
		List<Route> getRoutes(@Query("timestamp") long timestamp, @Header("usertoken") String usertoken);
		
		@GET("/api/routes/{id}")
		Route getRoute(@Path("id") String id, @Header("usertoken") String usertoken);
		
		@POST("/api/runs")
		String postRun(@Body Run run, @Header("usertoken") String usertoken);
	}
	
	RestAdapter _restAdapter;
	private FalconApi _api;
	
	public RetrofitDataProvider() {
		_restAdapter = new RestAdapter.Builder().setServer(REST_URL).build();
		_api = _restAdapter.create(FalconApi.class);
		_usertoken = "peter.m.brandt@gmail.com";
	}
	
	@Override
	public List<Route> getRoutes(long timestamp) {
		return _api.getRoutes(timestamp, _usertoken);
	}

	@Override
	public void postRun(Run run) {
		_api.postRun(run, _usertoken);
	}
}

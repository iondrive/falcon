package com.simiansoft.falcon;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public class RetrofitDataProvider implements INetworkCommunicationProvider{
	private static String REST_URL = "http://68.173.39.116:3000";
	
	interface FalconApi {
		@GET("/api/routes?timestamp={timestamp}")
		List<SimpleRoute> getRouteMetadata(@Path("timestamp") long timestamp);
		
		@GET("/api/routes/{id}")
		Route getRoute(@Path("id") String id);
		
		@POST("/api/runs")
		void postRun(@Body String JSON);
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
	
//	private class GetRoutesDataTask extends AsyncTask<long, Void, List<SimpleRoute>> {
//		protected List<SimpleRoute> doInBackground(long... p) {
//			getRoutesHelper();
//			return null;
//		}
//	}
//	
//	private void getRoutesHelper() {
//		RestAdapter restAdapter = new RestAdapter.Builder().setServer(REST_URL).build();
//		
//		FalconeApi api = restAdapter.create(FalconeApi.class);
//		List<Route> routes = _api.getRouteMetadata()
//	}
	

	@Override
	public Route getRoute(String id) {
		return _api.getRoute(id);
	}

	@Override
	public void postRun(Run run) {
		_api.postRun(run.toString());
	}
}

package com.simiansoft.falcon;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import android.os.AsyncTask;

public class RetrofitDataProvider implements IDataProvider{
	private static String REST_URL = "http://68.173.39.116:3000";
	
	interface RouteApi {
		@POST("/collectionapi/routes")
		Route postRoute(@Body Route route);
		
		@GET("/collectionapi/routes")
		List<Route> getRoutes();
		
		@POST("/collectionapi/runs")
		Route postRun(@Body String JSON);
	}

	@Override
	public void createRoute(Route route) {
		new CreateRouteDataTask().execute(route);
	}
	
	private class CreateRouteDataTask extends AsyncTask<Route, Void, Void> {
		protected Void doInBackground(Route... routes) {
			// just do the first one
			createRouteHelper(routes[0]);
			return null;
		}
	}
	
	private void createRouteHelper(Route route) {
		// TODO Auto-generated method stub
		RestAdapter restAdapter = new RestAdapter.Builder().setServer(REST_URL).build();
		
		RouteApi api = restAdapter.create(RouteApi.class);
		Route r = api.postRoute(route);
	}

	
	
	@Override
	public List<Route> GetRoutes() {
		new GetRoutesDataTask().execute();
		return null;
	}
	
	private class GetRoutesDataTask extends AsyncTask<Void, Void, List<Route>> {
		protected List<Route> doInBackground(Void... p) {
			// just do the first one
			getRoutesHelper();
			return null;
		}
	}
	
	private void getRoutesHelper() {
		// TODO Auto-generated method stub
		RestAdapter restAdapter = new RestAdapter.Builder().setServer(REST_URL).build();
		
		RouteApi api = restAdapter.create(RouteApi.class);
		List<Route> routes = api.getRoutes();
	}
	
	

	@Override
	public void logRun(Route route) {
		// TODO Auto-generated method stub
		new LogRunDataTask().execute(route);
	}
	
	private class LogRunDataTask extends AsyncTask<Route, Void, Void> {
		protected Void doInBackground(Route... routes) {
			// just do the first one
			logRunHelper(routes[0]);
			return null;
		}
	}
	
	private void logRunHelper(Route route) {
		// TODO Auto-generated method stub
		RestAdapter restAdapter = new RestAdapter.Builder().setServer(REST_URL).build();
		
		RouteApi api = restAdapter.create(RouteApi.class);
		Route r = api.postRun(route.toString());
	}


}

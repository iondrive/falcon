package com.simiansoft.falcon;

public class RouteMatchAlgorithm {
	public static SimpleRoute MatchRoute(Run run) {
		SimpleRoute route = new SimpleRoute();
		route.Id = "TEST";
		route.Name = "TEST ROUTE";
		route.Distance = 13.1;
		route.StartingPoint = new LonLatPoint(12, 13);
		
		return route;
	}
}

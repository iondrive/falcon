package com.simiansoft.falcon;

import java.util.List;

import android.content.Context;

public class RouteMatchAlgorithm {
		
	public static SimpleRoute MatchRoute(Run run, Context context) {
		RefDataManager _refData = new RefDataManager(context);
		SimpleRoute justRan = new SimpleRoute();
		justRan.Distance = getDistance(run);
		justRan.StartingPoint = run.Data.get(0);
		
		// First match starting points
		List<Route> routes = _refData.GetRoutes();
		
		
		return justRan;
	}
	
	/**
	 * Gets the distance logged by the run assuming short distances between log points
	 * ds = R*sqrt((dlon)^2*cos^2(lon1) + (dlat)^2)
	 * @param run
	 * @return
	 */
	private static double getDistance(Run run) {
		double distance = 0;
		LonLatPoint p1;
		LonLatPoint p2;
		for (int i = 1; i < run.Data.size(); ++i) {
			p1 = run.Data.get(i-1);
			p2 = run.Data.get(i);
			// Equirectangular projection approximation (fastest)
//			distance += Math.sqrt(
//					Math.pow((p2.getLongitudeRad() - p1.getLongitudeRad())*Math.cos(p2.getLatitudeRad()), 2)
//					+ Math.pow(p2.getLatitudeRad() - p1.getLatitudeRad(), 2));
			
			// Spherical law of cosines
			distance += Math.acos(
					Math.sin(p1.getLatitudeRad())*Math.sin(p2.getLatitudeRad())
					+ Math.cos(p1.getLatitudeRad())*Math.cos(p2.getLatitudeRad())*Math.cos(p2.getLongitudeRad() - p1.getLongitudeRad()));
		}
		double a = 6378.1370; // equatorial radius km
		double b = 6356.7523; // polar radius km
		double phi = run.Data.get(0).getLatitudeRad(); // starting latitude (rad)
		double R = Math.sqrt(
				(Math.pow(a*a*Math.cos(phi), 2) + Math.pow(b*b*Math.sin(phi), 2))
				/(Math.pow(a*Math.cos(phi), 2) + Math.pow(b*Math.sin(phi), 2)));
		return distance*R*0.621371;
	}
}

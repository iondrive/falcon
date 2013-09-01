package com.simiansoft.falcon;

import java.util.List;

public interface ILocalStorageProvider {
	/**
	 * Gets all the routes
	 * @return
	 */
	List<Route> GetRoutes();
	
	/**
	 * Gets the most recent timestamp of any route
	 * @return timestamp
	 */
	long GetMostRecentTimestamp();
	
	/**
	 * Saves or updates a route
	 * @param route
	 */
	void SaveRoute(Route route);
	
	/**
	 * Removes all routes matching the specified id
	 * @param id
	 */
	void DeleteRouteById(String id);
}

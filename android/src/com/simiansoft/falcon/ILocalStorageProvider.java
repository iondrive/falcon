package com.simiansoft.falcon;

import java.util.List;

public interface ILocalStorageProvider {
	List<SimpleRoute> GetRouteMetadata();
	long GetMostRecentTimestamp();
	void SaveRoute(Route route);
	void DeleteRouteById(String id);
}

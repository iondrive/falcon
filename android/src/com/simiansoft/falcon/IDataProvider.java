package com.simiansoft.falcon;

import java.util.List;

public interface IDataProvider {
	void createRoute(Route route);
	void logRun(Route route);
	List<Route> GetRoutes();
}

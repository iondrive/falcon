package com.simiansoft.falcon;

import java.util.List;

public interface INetworkCommunicationProvider {
	List<SimpleRoute> getRouteMetadata(long timestamp);
	Route getRoute(String id);
	void postRun(Run run);
}

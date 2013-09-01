package com.simiansoft.falcon;

import java.util.List;

public interface INetworkCommunicationProvider {
	List<Route> getRoutes(long timestamp);
	void postRun(Run run);
}

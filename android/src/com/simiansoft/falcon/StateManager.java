package com.simiansoft.falcon;

import java.util.ArrayList;
import java.util.List;

public class StateManager {
	private List<LogPoint> _data;
	private List<Route> _compatibleRoutes;
	private Route _activeRoute;
	public LoggingStatus Status;
	
	public StateManager() {
		_data = new ArrayList<LogPoint>();
	}
	
	public void logPoint(double lon, double lat, double alt, long date) {
		logPoint(lon, lat, alt, date, Status);
	}

	public void logPoint(double lon, double lat, double alt, long date, LoggingStatus status) {
		_data.add(new LogPoint(lon, lat, alt, date, status));
	}
	
	public Run calculateRun() {
		Run run = new Run();
		run.Data = _data;
		return run;
	}
}

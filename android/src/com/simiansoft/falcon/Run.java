package com.simiansoft.falcon;

import java.util.ArrayList;
import java.util.List;

public class Run {
	public List<LogPoint> Data;
	public SimpleRoute Route;
	
	public Run() {
		Data = new ArrayList<LogPoint>();
	}
	
	public void AddPoint(double lon, double lat, long timestamp) {
		Data.add(new LogPoint(lon, lat, timestamp));
	}
}

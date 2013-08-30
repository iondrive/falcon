package com.simiansoft.falcon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Run {
	public List<LogPoint> Data;
	public SimpleRoute Route;
	
	public Run() {
		Data = new ArrayList<LogPoint>();
	}
	
	public void AddPoint(double lon, double lat, long timestamp, LoggingStatus status) {
		Data.add(new LogPoint(lon, lat, timestamp, status));
	}
	
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"data\":[");
		for (Iterator<LogPoint> i = Data.iterator(); i.hasNext(); ) {
			sb.append(i.next().toString());
			if (i.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("], \"route\":").append(Route.toJSON()).append("}");
		
		return sb.toString();
	}
}

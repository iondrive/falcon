package com.simiansoft.falcon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Route {
	private String name;
	private int id;
	private List<LogPoint> loggedPoints;
	private List<LonLatPoint> nominalRoutePoints;
	
	public Route()
	{
		loggedPoints = new ArrayList<LogPoint>();
		id = -1;
	}
	
	public void AddPoint(double lon, double lat, long date)
	{
		loggedPoints.add(new LogPoint(lon, lat, date));
	}
	
	private Boolean validate()
	{
		return true;
	}
	
	private String GetJSON()
	{
		StringBuilder sb = new StringBuilder();
		if (!validate()) {
			return "{}";
		}		
		// Saved routes have id's only, new routes have names only
		if (id != -1) {
			sb.append("{id:").append(id);
		} else if (name != null) {
			sb.append("{name:").append(name);
		}
		sb.append(",d:[");
		sb.append(loggedPoints.get(0));
		for (int i=1; i<loggedPoints.size(); ++i)
		{
			sb.append(",").append(loggedPoints.get(i));
		}
		sb.append("]}");
		
		return sb.toString();
	}
}

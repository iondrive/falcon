package com.simiansoft.falcon;


public class LogPoint extends LonLatPoint {
	public long DateUTC;
	
	public LogPoint(double lon, double lat, long date)
	{
		super(lon, lat);
		this.DateUTC = date;
	}
	
	@Override public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{a:")
		.append(Longitude)
		.append(",b:")
		.append(Latitude)
		.append(",t:")
		.append(DateUTC)
		.append("}");
		return sb.toString();
	}
}
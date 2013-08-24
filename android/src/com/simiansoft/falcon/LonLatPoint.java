package com.simiansoft.falcon;

public class LonLatPoint {
	public double Longitude;
	public double Latitude;
	
	public LonLatPoint(double lon, double lat)
	{
		this.Longitude = lon;
		this.Latitude = lat;
	}
	
	@Override public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{a:").append(Longitude).append(",b:").append(Latitude).append("}");
		return sb.toString();
	}
}
package com.simiansoft.falcon;

/**
 * @author peter
 *
 */
public class LonLatPoint {
	/**
	 * Longitude
	 */
	private double x;
	
	/**
	 * Latitude
	 */
	private double y;
	
	public LonLatPoint(double lon, double lat)
	{
		this.x = lon;
		this.y = lat;
	}
	
	public double getLatitude() {
		return y;
	}
	
	public double getLongitude() {
		return x;
	}
	
	@Override public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\"a\":").append(x).append(",\"b\":").append(y).append("}");
		return sb.toString();
	}
}
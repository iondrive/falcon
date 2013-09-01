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
	
	/**
	 * Altitude
	 */
	private double a;
	
	public LonLatPoint(double lon, double lat, double alt)
	{
		this.x = lon;
		this.y = lat;
		this.a = alt;
	}
	
	public double getLatitude() {
		return y;
	}
	
	public double getLatitudeRad() {
		return Math.toRadians(y);
	}
	
	public double getLongitude() {
		return x;
	}
	
	public double getLongitudeRad() {
		return Math.toRadians(x);
	}
	
	public double getAltitude() {
		return a;
	}
	
	@Override public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\"x\":").append(x).append(",\"y\":").append(y).append("}");
		return sb.toString();
	}
}
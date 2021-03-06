package com.simiansoft.falcon;


/**
 * @author peter
 *
 */
public class LogPoint extends LonLatPoint {
	
	/**
	 * Date, number of milliseconds since midnight Jan 1 1970 GMT
	 * HOPEFULLY the same as javascript's Date.getTime() function
	 */
	private long d;
	
	/**
	 * LoggingStatus: Stopped, Active, Paused, etc
	 */
	private int s;
	
	/**
	 * 
	 * @return number of milliseconds since midnight Jan 1 1970 GMT
	 */
	public long getDateMilliseconds() {
		return d;
	}
	
	/**
	 * 
	 * @param lon Longitude
	 * @param lat Latitude
	 * @param alt Altitude
	 * @param date Number of milliseconds since Jan 1 1970, ex new Date().getTime()
	 * @param status active, paused, clickedStart etc
	 */
	public LogPoint(double lon, double lat, double alt, long date, LoggingStatus status)
	{
		super(lon, lat, alt);
		this.d = date;
		this.s = status.toInt();
	}
	
	/**
	 * Returns a compact JSON representation
	 */
	@Override public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\"x\":")
		.append(getLongitude())
		.append(",\"y\":")
		.append(getLatitude())
		.append(",\"a\":")
		.append(getAltitude())
		.append(",\"t\":")
		.append(d)
		.append(",\"s\":")
		.append(s)
		.append("}");
		return sb.toString();
	}
}
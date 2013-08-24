package com.simiansoft.falcon;

/**
 * Class just for serializing data with GSON and sending it over the wire to the app.
 * 
 * @author peter
 *
 */
public class CreateRunModel {
	public String routeId;
	public int durationSec;
	public String duration;
	public String date;
	public String userId;
	public double actualDistance;
	
	/**
	 * 
	 * @param r
	 */
	public CreateRunModel(Route r) {
		
	}
}

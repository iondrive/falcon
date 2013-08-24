package com.simiansoft.falcon;

/**
 * Class just for serializing data with GSON and sending it over the wire to the app.
 * 
 * @author peter
 *
 */
public class CreateRouteModel {
	public double distance;
	public String name;
	public String userId;
	
	public CreateRouteModel(Route r) {
		
	}
}

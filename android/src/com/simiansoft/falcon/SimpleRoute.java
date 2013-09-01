package com.simiansoft.falcon;

/**
 * Make sure to modify /meteor/server/api.js when you modify the properties here
 * @author peter
 *
 */
public class SimpleRoute {
	public String Id;
	public int AndroidId;
	public String Name;
	public LonLatPoint StartingPoint;
	public double Distance;
	public long Timestamp;
	public boolean Active;
	
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":\"")
			.append(Id)
			.append("\", \"name\":\"")
			.append(Name)
			.append("\", \"startingPoint\":")
			.append(StartingPoint.toString())
			.append(", \"distance\":")
			.append(Distance)
			.append("}");
		return sb.toString();
	}
}

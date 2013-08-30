package com.simiansoft.falcon;

/**
 * @author peter
 *
 */
public class SimpleRoute {
	public String Id;
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

package com.simiansoft.falcon;

import java.util.List;

/**
 * Make sure to modify /meteor/server/api.js when you modify the properties here
 * @author peter
 *
 */
public class Route {
	public String Id;
	public String Name;
	public double Distance;
	public List<LonLatPoint> MapPoints;
	public List<LonLatPoint> SparseControlPoints;
	public boolean Active;
	public long Timestamp;
}

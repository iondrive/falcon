package com.simiansoft.falcon;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RouteAdapter extends ArrayAdapter{

	private Context _mainContext;
	private List<Route> _routes;
	
	public RouteAdapter(Context context, int resource, List<Route> routes) {
		super(context, resource);
		_mainContext = context;
		_routes = routes;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = ((Activity)_mainContext).getLayoutInflater();
			row = inflater.inflate(R.layout.route_item, parent, false);
			((TextView)row.findViewById(R.id.textView1)).setText(_routes.get(position).Name);
			((TextView)row.findViewById(R.id.timeElapsedText)).setText(String.format(" [%f.2] miles", _routes.get(position).Distance));
		}
		return row;
	}
	
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = ((Activity)_mainContext).getLayoutInflater();
			row = inflater.inflate(R.layout.route_item, parent, false);
			((TextView)row.findViewById(R.id.textView1)).setText(_routes.get(position).Name);
			((TextView)row.findViewById(R.id.timeElapsedText)).setText(String.format(" [%f.2] miles", _routes.get(position).Distance));
		}
		return row;
	}
}

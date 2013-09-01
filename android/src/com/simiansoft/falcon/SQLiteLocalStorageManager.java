package com.simiansoft.falcon;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author peter
 *
 */
public class SQLiteLocalStorageManager extends SQLiteOpenHelper implements
		ILocalStorageProvider {
	
	private static final String DATABASE_NAME = "falcon.db";
	private static final int DATABASE_VERSION = 2;
	
	public SQLiteLocalStorageManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public List<SimpleRoute> GetRouteMetadata() {
		ArrayList<SimpleRoute> results = new ArrayList<SimpleRoute>();
		String sql = "select _id, name, distance, startingLon, startingLat, timestamp from routes";
		Cursor cursor = this.getWritableDatabase().rawQuery(sql,  null);
		while (cursor.moveToNext()) {
			LonLatPoint startingPoint = new LonLatPoint(cursor.getDouble(3), cursor.getDouble(4), 0);
			SimpleRoute route = new SimpleRoute();
			route.Id = cursor.getString(0);
			route.Name = cursor.getString(1);
			route.StartingPoint = startingPoint;
			route.Timestamp = cursor.getLong(5);
			route.Distance = cursor.getDouble(2);
			results.add(route);			
		}
		return null;
	}
	
	@Override
	public long GetMostRecentTimestamp() {
		String sql = "select timestamp from routes order by timestamp desc limit 1";
		Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);
		if (cursor.getCount() != 1) {
			return 0;
		} else {
			cursor.moveToFirst();
			return cursor.getLong(0);
		}
	}

	/**
	 * Saves or updates a route to the database
	 */
	@Override
	public void SaveRoute(Route route) {
		ContentValues values = new ContentValues();
		values.put("id",  route.Id);
		values.put("name",  route.Name);
		values.put("distance", route.Distance);
		values.put("timestamp", route.Timestamp);
		values.put("active", route.Active);
		if (this.getReadableDatabase().rawQuery(String.format("select 1 from routes where id = '%s' limit 1",  route.Id), null).getCount() > 0) {
			// This route already exists, so we will update it
			this.getWritableDatabase().update("routes", values, "id = '?'", new String[] {DatabaseUtils.sqlEscapeString(route.Id)});
		} else {
			// The route does not exist, so we need to insert it
			this.getWritableDatabase().insert("routes", null, values);
		}
	}

	@Override
	public void DeleteRouteById(String id) {
		ContentValues values = new ContentValues();
		values.put("active", false);
		this.getWritableDatabase().update("routes", values, "id = '?'", new String[] {DatabaseUtils.sqlEscapeString(id)});
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists routes (id text primary key, name text, distance real, timestamp integer, active integer);\n");
		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO no upgrades for initial release
	}

	@Override
	public List<Route> GetRoutes() {
		ArrayList<Route> results = new ArrayList<Route>();
		String sql = "select id, name, distance, timestamp from routes where active = 1";
		Cursor cursor = this.getWritableDatabase().rawQuery(sql,  null);
		while (cursor.moveToNext()) {
			Route route = new Route();
			route.Id = cursor.getString(0);
			route.Name = cursor.getString(1);
			route.Distance = cursor.getDouble(2);
			route.Timestamp = cursor.getLong(3);
			results.add(route);			
		}
		return results;
		
	}
}

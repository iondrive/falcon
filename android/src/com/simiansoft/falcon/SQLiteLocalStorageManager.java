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

	@Override
	public List<SimpleRoute> GetRouteMetadata() {
		ArrayList<SimpleRoute> results = new ArrayList<SimpleRoute>();
		String sql = "select _id, name, distance, startingLon, startingLat, timestamp from routes";
		Cursor cursor = this.getWritableDatabase().rawQuery(sql,  null);
		while (cursor.moveToNext()) {
			LonLatPoint startingPoint = new LonLatPoint(cursor.getDouble(3), cursor.getDouble(4));
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
		String sql = "select top 1 timestamp from routes order by timestamp";
		Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);
		if (cursor.getCount() != 1) {
			return 0;
		} else {
			return cursor.getLong(0);
		}
	}

	@Override
	public void SaveRoute(Route route) {
		ContentValues insert = new ContentValues();
		insert.put("_id",  route.Id);
		insert.put("name",  route.Name);
		insert.put("distance", route.Distance);
		insert.put("startingLon",  route.StartingPoint.Longitude);
		insert.put("startingLat", route.StartingPoint.Latitude);
		insert.put("timestamp", route.Timestamp);
		this.getWritableDatabase().insert("routes", null, insert);
	}

	@Override
	public void DeleteRouteById(String id) {
		this.getWritableDatabase().delete("routes", "_id = ?", new String[] {DatabaseUtils.sqlEscapeString(id)});
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists routes (_id text primary key, name text, distance real, startingLon real, startingLat real, timestamp integer);\n");
		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO no upgrades for initial release
	}
}

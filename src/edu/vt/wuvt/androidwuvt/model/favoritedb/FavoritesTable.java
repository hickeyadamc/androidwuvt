package edu.vt.wuvt.androidwuvt.model.favoritedb;

import android.database.sqlite.SQLiteDatabase;

public class FavoritesTable {
	
	//name
	public static final String TABLE_NAME = "favorites";
	
	//columns
	private static final String COLUMN_ID = "_id";
	public static final String COLUMN_TRACK = "track";
	public static final String COLUMN_TIME_STAMP = "timestamp";
	public static final String COLUMN_ALBUM = "album";
	public static final String COLUMN_DJ = "dj";
	public static final String COLUMN_NEW = "new";
	public static final String COLUMN_REQUEST = "request";
	public static final String COLUMN_VINYL = "vinyl";
	
	public static final String ALL_COLUMNS[] = {
		COLUMN_ID, COLUMN_TRACK, COLUMN_TIME_STAMP, COLUMN_ALBUM,COLUMN_DJ,COLUMN_NEW,COLUMN_REQUEST,COLUMN_VINYL
	};
	
	//db creation statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_TRACK + " text"
			+ COLUMN_TIME_STAMP + " text"
			+ COLUMN_ALBUM + " text"
			+ COLUMN_DJ + " text"
			+ COLUMN_NEW + " boolean"
			+ COLUMN_REQUEST + " boolean"
			+ COLUMN_VINYL + " boolean";
	
	protected static void onCreate(SQLiteDatabase database) {
		
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub
		
	}

}

package com.example.gamified_learning_app.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class FeedReaderContract {
	// To prevent someone from accidentally instantiating the contract class,
	// make the constructor private.
	private FeedReaderContract() {}
	
	/* Inner class that defines the table contents */
	public static class FeedEntry implements BaseColumns {
		public static final String TABLE_NAME = "entry";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_SUBTITLE = "subtitle";
	}
	
	public class FeedReaderDbHelper extends SQLiteOpenHelper {
		// If you change the database schema, you must increment the database version.
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "FeedReader.db";
		
		private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
				FeedEntry._ID + " INTEGER PRIMARY KEY," +
				FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
				FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";
		
		private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
		
		public FeedReaderDbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_ENTRIES);
		}
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// This database is only a cache for online data, so its upgrade policy is
			// to simply to discard the data and start over
			db.execSQL(SQL_DELETE_ENTRIES);
			onCreate(db);
		}
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onUpgrade(db, oldVersion, newVersion);
		}
	}
}

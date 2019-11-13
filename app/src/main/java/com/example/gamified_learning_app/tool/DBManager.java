package com.example.gamified_learning_app.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
public class DBManager extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";
	
	public static final Table tables[] = {
		new UserTable()
	};
	
	public static enum TableRef {
		USER(0);
		public int index;
		TableRef(int index) {
			this.index = index;
		}
	}
	
	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
		for (Table table : tables)
			db.execSQL(table.onCreate());
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		for (Table table : tables)
			db.execSQL("DROP TABLE IF EXISTS " + table.TABLE_NAME);
		onCreate(db);
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	public void insert(TableRef ref, ContentValues entry, boolean ignoreConflict) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (ignoreConflict)
			db.insertWithOnConflict(tables[ref.index].TABLE_NAME, null, entry,
				SQLiteDatabase.CONFLICT_IGNORE);
		else
			db.insert(tables[ref.index].TABLE_NAME, null, entry);
	}
	
	public void update(TableRef ref, ContentValues entry) {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = tables[ref.index].FIELD_NAME[0] + " = '" + entry.getAsString(tables[ref.index].FIELD_NAME[0]) + "'";
		db.updateWithOnConflict(tables[ref.index].TABLE_NAME, entry,
			query, null, SQLiteDatabase.CONFLICT_IGNORE);
	}
	
	public void delete(TableRef ref, ContentValues entry) {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = tables[ref.index].FIELD_NAME[0] + " = '" + entry.getAsString(tables[ref.index].FIELD_NAME[0]) + "'";
		// Delete by email
		db.delete(tables[ref.index].TABLE_NAME, query,null);
	}
	
	// Leave selector to null if you want everything
	public List<Map<String, String>> get(TableRef ref, String selector) {
		ArrayList<Map<String, String>> objs = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + tables[ref.index].TABLE_NAME;
		if (selector != null)
			query += " WHERE " + selector;
		query += ";";
		Cursor c = db.rawQuery(query, null);
		if (c.moveToFirst()) {
			do {
				Map<String, String> obj = new HashMap<>();
				for (int i = 0; i < tables[ref.index].FIELD_NAME.length; i++) {
					String name = tables[ref.index].FIELD_NAME[i],
						type = tables[ref.index].FIELD_TYPE[i];
					if (type.startsWith("TEXT"))
						obj.put(name, c.getString(c.getColumnIndex(name)));
					else if (type.startsWith("INTEGER"))
						obj.put(name, Integer.toString(c.getInt(c.getColumnIndex(name))));
					else if (type.startsWith("REAL"))
						obj.put(name, Double.toString(c.getDouble(c.getColumnIndex(name))));
				}
				objs.add(obj);
			} while (c.moveToNext());
		}
		return objs;
	}
}

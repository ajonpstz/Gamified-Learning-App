package com.example.gamified_learning_app.tool;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

//
public class DBManager extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";
	private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
	public FirebaseInteractor interactor;
	
	
	public static final Table tables[] = {
		new UserTable()
	};
	
	public static enum TableRef {
		USER(0);
		private int index;
		TableRef(int index) {
			this.index = index;
		}
	}
	
	class FirebaseInteractor {
		public static final String TAG = "loginLogout";
		FirebaseUser getCurrentUser() {
			return mAuth.getCurrentUser();
		}
		/*  How to use:
			createAccount(email, username, password, this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						getCurrentUser() -> will now have what you need
					} else {
						oops
					}
				}
			});
		 */
		String createAccount(String email, String username, String password,
		                     OnCompleteListener<AuthResult> listener) {
			if (valid(email) && valid(username) && valid(password)) {
				Log.d(TAG, "create account:" + email);
				
				// Check if email or username already exist
				if (!get(TableRef.USER, "username = " + username).isEmpty())
					return "username " + username + " has been claimed";
				if (!get(TableRef.USER, "email = " + email).isEmpty())
					return "email " + email + " has already registered";
				
				mAuth.createUserWithEmailAndPassword(email, password).
					addOnCompleteListener(listener).addOnCompleteListener(
						new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						ContentValues values = new ContentValues();
						values.put("username", username);
						values.put("email", email);
						values.put("dateJoined", (new Date()).toString());
						values.put("description","");
						insert(TableRef.USER, values, false);
					}
				});
				// NULL is success
				return null;
			} else
				return "failed";
		}
		void signIn(String email, String password,
		                    Activity act, OnCompleteListener<AuthResult> listener) {
			if (valid(email) && valid(password)) {
				Log.d(TAG, "login:" + email);
				mAuth.signInWithEmailAndPassword(email, password).
					addOnCompleteListener(act, listener);
			}
		}
		void signOut() { mAuth.signOut(); }
		boolean valid(String str) {  return TextUtils.isEmpty(str); }
	}
	
	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		interactor = new FirebaseInteractor();
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
		db.update(tables[ref.index].TABLE_NAME, entry,
			"? = ?", new String[]{tables[ref.index].FIELD_NAME[0],
				entry.getAsString(tables[ref.index].FIELD_NAME[0])});
	}
	
	public void delete(TableRef ref, ContentValues entry) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tables[ref.index].TABLE_NAME, "? = ?",
			new String[]{tables[ref.index].FIELD_NAME[0],
				entry.getAsString(tables[ref.index].FIELD_NAME[0])});
	}
	
	// Leave selector to null if you want everything
	public List<Map<String, String>> get(TableRef ref, String selector) {
		ArrayList<Map<String, String>> objs = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + tables[ref.index].TABLE_NAME;
		if (selector != null)
			query += " WHERE " + selector;
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

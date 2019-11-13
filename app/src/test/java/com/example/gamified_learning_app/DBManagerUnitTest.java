package com.example.gamified_learning_app;

import android.content.ContentValues;
import android.content.Context;

import com.example.gamified_learning_app.tool.DBManager;
import com.google.firebase.FirebaseApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(RobolectricTestRunner.class)
public class DBManagerUnitTest {
	
	static final String TEST_NAME = "admin",
						TEST_EMAIL = "aqn180001@utdallas.edu",
						TEST_PASSWORD = "sda982dslahsjhkadlshalk201h*@",
						TEST_DESCRIPTION = "this is admin";
	
	
	@Test
	public void DBFirebaseInteractorCorrect() throws Exception {
		Context mockContext = InstrumentationRegistry.getInstrumentation().getContext();
		FirebaseApp.initializeApp(mockContext);
		DBManager manager = new DBManager(mockContext);
		ContentValues user = new ContentValues();
		user.put("email", TEST_EMAIL);
		user.put("username", TEST_NAME);
		user.put("description", TEST_DESCRIPTION);
		user.put("dateJoined", (new Date()).toString());
		manager.insert(DBManager.TableRef.USER, user, true);
		
		List<Map<String,String>> map = manager.get(DBManager.TableRef.USER, null);
		for (Map<String,String> entry : map) {
			System.out.println(entry.toString());
		}
		
		long t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < 2000);
		
		user.put("dateJoined", (new Date()).toString());
		manager.update(DBManager.TableRef.USER, user);
		
		assert(manager.getReadableDatabase().rawQuery(
			"SELECT * FROM users WHERE email='"+TEST_EMAIL+"'", null).moveToFirst());
		
		for (Map<String,String> entry : manager.get(DBManager.TableRef.USER, null)) {
			System.out.println(entry.toString());
		}
		
		manager.delete(DBManager.TableRef.USER, user);
		
		
		for (Map<String,String> entry : manager.get(DBManager.TableRef.USER, null)) {
			System.out.println(entry.toString());
		}
	}
}

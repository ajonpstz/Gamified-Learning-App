package com.example.gamified_learning_app;

import android.content.Context;

import com.example.gamified_learning_app.data.User;
import com.google.firebase.FirebaseApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(RobolectricTestRunner.class)
public class UserUnitTest {
	
	static final String emailUsed = "anquocnguyen@outlook.com";
	static final String username = "m1sch3f";
	static final String password = emailUsed;
	
	@Test
	public void constructorTest() {
		Context mockContext = InstrumentationRegistry.getInstrumentation().getContext();
		FirebaseApp.initializeApp(mockContext);
		System.out.println("BEGIN TEST");
		User user = new User(emailUsed, username, "HELLO THIS IS ME");
		user = new User(emailUsed, username);
		user = new User(emailUsed, username, "HELLO THIS IS ME", 5, 5);
	}
}

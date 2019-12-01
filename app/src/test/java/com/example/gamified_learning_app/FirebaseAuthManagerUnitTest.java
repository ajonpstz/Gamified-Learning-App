package com.example.gamified_learning_app;

import android.content.Context;
import android.util.Log;

import com.example.gamified_learning_app.tool.FirebaseAuthManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(RobolectricTestRunner.class)
public class FirebaseAuthManagerUnitTest {
	
	static final String emailUsed = "aqn180001@utdallas.edu";
	static final String username = "m1sch3f";
	static final String password = emailUsed;
	
	@Test
	public void FirebaseAuthInteractor() throws Exception {
		Context mockContext = InstrumentationRegistry.getInstrumentation().getContext();
		FirebaseApp.initializeApp(mockContext);
		
		System.out.println("START TEST");
		
		FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(
			authResultTask->{
				if (authResultTask.isSuccessful())
					System.out.println("SUCCESSFUL SIGN IN");
				else
					System.out.println("NO");
			}
		);
		FirebaseAuthManager.login(emailUsed, password, authResultTask->{
			Log.e("DEBUG", "IN");
			if (authResultTask.isSuccessful()) {
				System.out.println("Logged in correctly");
				FirebaseAuth.getInstance().signOut();
				System.out.println("Logged out");
			} else {
				System.out.println("Cannot log in: " + authResultTask.getException());
			}
		});
		FirebaseAuthManager.create(emailUsed, username, password, authResult->{
			System.out.println("Successfully created " + username);
			FirebaseAuthManager.login(emailUsed, password, authResultTask-> {
				if (authResultTask.isSuccessful()) {
					System.out.println("Logged in correctly");
					FirebaseAuth.getInstance().signOut();
					System.out.println("Logged out");
				} else
					System.out.println("Cannot log in: " + authResultTask.getException());
			});
		}, e->{
			System.out.println(e.getStackTrace());
		});
		
		long t = Long.MAX_VALUE;
		while (t-->0);
	}
}

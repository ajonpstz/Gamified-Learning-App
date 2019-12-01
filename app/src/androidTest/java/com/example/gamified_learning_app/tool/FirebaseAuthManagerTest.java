package com.example.gamified_learning_app.tool;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FirebaseAuthManagerTest {
	
	static final String emailUsed = "anquocnguyen@outlook.com";
	static final String username = "m1sch3f";
	static final String password = emailUsed;
	
	@Test
	public void login() {
		FirebaseAuth.getInstance().signInWithEmailAndPassword(emailUsed, password)
			.addOnCompleteListener(
				authResultTask->{
					if (!authResultTask.isSuccessful())
						System.out.println(authResultTask.getException().getLocalizedMessage());
					assertEquals(authResultTask.isSuccessful(), true);
				}
			);
	}
	
	@Test
	public void create() {
		FirebaseAuthManager.create(emailUsed, username, password, authResult->{
			System.out.println("Successfully created " + username);
			FirebaseAuthManager.login(emailUsed, password, authResultTask-> {
				if (authResultTask.isSuccessful()) {
					System.out.println("Logged in correctly");
					FirebaseAuth.getInstance().signOut();
					System.out.println("Logged out");
				} else
					System.out.println("Cannot log in: " + authResultTask.getException());
				assertEquals(authResultTask.isSuccessful(), true);
			});
		}, e->{
			System.out.println(e.getStackTrace());
			assert(false);
		});
	}
	
	@Test
	public void update_profile() {
		FirebaseAuth.getInstance().signInWithEmailAndPassword(emailUsed, password)
			.addOnCompleteListener(
				authResultTask->{
					if (!authResultTask.isSuccessful())
						System.out.println(authResultTask.getException().getLocalizedMessage());
					else {
						FirebaseAuthManager.update_profile(username);
						System.out.println("SUCESS");
					}
					assertEquals(authResultTask.isSuccessful(), true);
				}
			);
	}
}
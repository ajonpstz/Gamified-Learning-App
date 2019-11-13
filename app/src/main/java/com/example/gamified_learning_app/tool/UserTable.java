package com.example.gamified_learning_app.tool;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

public final class UserTable extends Table {
	private static final String TAG = "EmailPassword";
	private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
	
	UserTable() {
		this.TABLE_NAME = "users";
		this.FIELD_NAME = new String[]{
			"email",
			"username",
			"desciption",
			"dateJoined"
		};
		this.FIELD_TYPE = new String[]{
			"TEXT PRIMARY KEY",
			"TEXT NOT NULL UNIQUE",
			"TEXT",
			"TEXT"
		};
	}
	
	static FirebaseUser getCurrentUser() {
		return mAuth.getCurrentUser();
	}
	
	
	private void createAccount(String email, String username, String password,
	                             Activity act, OnCompleteListener<AuthResult> listener) {
		if (valid(email) && valid(username) && valid(password)) {
			Log.d(TAG, "create account:" + email);
			
			// Check if email or username already exist
			
			
			mAuth.createUserWithEmailAndPassword(email,password).
				addOnCompleteListener(act, listener);
		}
	}
	
	private void signIn(String email, String password,
	                      Activity act, OnCompleteListener<AuthResult> listener) {
		if (valid(email) && valid(password)) {
			Log.d(TAG, "login:" + email);
			// Check if email or username already exist
			
			
			mAuth.signInWithEmailAndPassword(email,password).
				addOnCompleteListener(act, listener);
		}
	}
	
	private void signOut() {
		mAuth.signOut();
	}
	
	/*
		Null if validated
		Otherwise an error msg
	 */
	private boolean valid(String str) {
		return TextUtils.isEmpty(str);
	}
}

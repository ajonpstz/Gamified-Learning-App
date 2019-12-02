package com.example.gamified_learning_app.tool;

import android.text.TextUtils;
import android.util.Log;

import com.example.gamified_learning_app.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseAuthManager {
	
	public static enum ERR_NO {
		NONE,
		EMPTY_PARAMETER,
		INVALID_PARAMETER;
	};
	
	public static ERR_NO login(String email, String password, OnCompleteListener<AuthResult> listener) {
		if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
			return ERR_NO.EMPTY_PARAMETER;
		FirebaseAuth auth = FirebaseAuth.getInstance();
		auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
		return ERR_NO.NONE;
	}
	
	public static ERR_NO create(String email, String username, String password,
	                            OnSuccessListener<AuthResult> onDelivery,
	                            OnFailureListener onFail) {
		if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username))
			return ERR_NO.EMPTY_PARAMETER;
		FirebaseDBManager.getUser(username, user -> {
			Log.e("DEBUG", "BAD CREATE");
			if (user != null && onFail != null)
				onFail.onFailure(new IllegalAccessException("Use with the same username exists"));
			else if (user == null) {
				FirebaseAuth auth = FirebaseAuth.getInstance();
				Task task = auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
					update_profile(username);
					FirebaseDBManager.createUser(new User(email, username), null, null);
				});
				if (onDelivery != null)
					task.addOnSuccessListener(onDelivery);
				if (onFail != null)
					task.addOnFailureListener(onFail);
			}
			return null;
		}, e->{
			Log.e("DEBUG", e);
			FirebaseAuth auth = FirebaseAuth.getInstance();
			Task task = auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
				update_profile(username);
				FirebaseDBManager.createUser(new User(email, username), null, null);
			});
			if (onDelivery != null)
				task.addOnSuccessListener(onDelivery);
			if (onFail != null)
				task.addOnFailureListener(onFail);
			return null;
		});
		return ERR_NO.NONE;
	}
	
	public static ERR_NO update_profile(String username) {
		if (TextUtils.isEmpty(username))
			return ERR_NO.EMPTY_PARAMETER;
		
		FirebaseAuth auth = FirebaseAuth.getInstance();
		UserProfileChangeRequest profileUpdates =
			new UserProfileChangeRequest.Builder().setDisplayName(username).build();
		auth.getCurrentUser().updateProfile(profileUpdates);
		return ERR_NO.NONE;
	}
}

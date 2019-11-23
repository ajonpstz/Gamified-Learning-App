package com.example.gamified_learning_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
	
	private FirebaseAuth mAuth = FirebaseAuth.getInstance();
	private boolean signIn = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);





	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user == null) {
				} else {
					UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName("Test123456").build();
					user.updateProfile(profileUpdates);
					signIn = true;
				}
			}
		});
		
	}

	private void signIn(String email, String password) {
		if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

			mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
				MainActivity.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();

							Intent intent = new Intent(MainActivity.this, Homepage.class);
							startActivity(intent);
							overridePendingTransition(R.anim.slide_in_south, R.anim.slide_out_south);
						} else {
							// failed to sign in
							Toast.makeText(MainActivity.this, "Incorrect Login", Toast.LENGTH_LONG).show();
						}
					}
				}
			);
		}
	}
	
	private void createUser(final String email, final String password) {
		if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
			mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
				MainActivity.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Toast.makeText(MainActivity.this, "Account Created!", Toast.LENGTH_LONG).show();
							signIn(email, password);
						} else {
							// failed create
						}
					}
				});
		}
	}
	
	private void signOut() {
		if (mAuth.getCurrentUser() != null)
			mAuth.signOut();
	}
	
	/*
		Actions
	 */
	public void submitLogin(View view) {
		TextView emailView = findViewById(R.id.email);
		TextView passwordView = findViewById(R.id.password);
		
		String email = emailView.getText().toString(),
				password = emailView.getText().toString();
		emailView.setText("");
		passwordView.setText("");

		signIn(email, password);
	}
	
	public void signOut(View view) {
		signOut();
	}
	
	public void submitCreate(View view) {
		TextView emailView = (TextView) findViewById(R.id.email);
		TextView passwordView = (TextView) findViewById(R.id.password);
		
		String email = emailView.getText().toString(),
			password = emailView.getText().toString();
		
		System.out.println(email + " " + password);
		emailView.setText("");
		passwordView.setText("");
		
		createUser(email, password);
	}
}

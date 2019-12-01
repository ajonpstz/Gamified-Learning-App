package com.example.gamified_learning_app;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	
	private FirebaseAuth mAuth = FirebaseAuth.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	// attempts to sign in the user using the given email and pass, then checks if they're email is authorized
	private void signIn(String email, String password) {
		if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
			mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
				MainActivity.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {

							// check if they activated account
							if(mAuth.getCurrentUser().isEmailVerified()) {
								Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();

								Intent intent = new Intent(MainActivity.this, Homepage.class);
								startActivity(intent);
								overridePendingTransition(R.anim.slide_in_south, R.anim.slide_out_south);
							}
							else{
								Toast.makeText(MainActivity.this, "Email has not been verified \nresending activation email", Toast.LENGTH_LONG).show();
								mAuth.getCurrentUser().sendEmailVerification();
							}
						} else {
							// failed to sign in
							Toast.makeText(MainActivity.this, "Incorrect Login", Toast.LENGTH_LONG).show();
						}
					}
				}
			);
		}
	}

	/*
		Actions
	 */
	public void submitLogin(View view) {
		TextView emailView = findViewById(R.id.description);
		TextView passwordView = findViewById(R.id.password);
		
		String email = emailView.getText().toString(),
				password = passwordView.getText().toString();
		emailView.setText("");
		passwordView.setText("");

		signIn(email, password);
	}

	public void gotoCreateAccount(View view){
		Intent intent = new Intent(this, CreateAccount.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}
	

}


/*
TODO: Create Firebase Auth Manager
	- Login             X
	- Logout            X
	- Create Account    X
	- Delte Account     O
	
TODO: Create Firebase Data Manager
	- Connect to Manager
	- Get
		- CardSet                   X
		- User
			- Create                X
			- Get                   X
			- Update                X
	- Write all of the above
	
TODO: Create a server that does the scheduling (or we can schedule locally)
TODO: Create an economy
 */
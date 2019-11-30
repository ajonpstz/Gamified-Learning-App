// this activity is the page for creating a new account

package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity
{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    // creates new users
    private void createUser(String email, String username, String password, String password2)
    {
        // attempt to create user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->{
            Log.e("DEBUG", "COMPLETED");
           if(task.isSuccessful()){
               // give username
               UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
               mAuth.getCurrentUser().updateProfile(profileUpdates);

               // initialize user Data
               FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
               DocumentReference userDetails = mDatabase.collection("userData").document(mAuth.getCurrentUser().getUid());
               Map<String, Object> userData = new HashMap<>();
               userData.put("attemptedTasks", 0);
               userData.put("correctTasks", 0);
               userDetails.set(userData);
               
               // send email verification
               mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(sentEmail ->{
                  if(sentEmail.isSuccessful()) Toast.makeText(CreateAccount.this, "Verification Email Sent", Toast.LENGTH_LONG).show();
                  else Toast.makeText(CreateAccount.this, "Failed to send Email", Toast.LENGTH_LONG).show();
               });
           }
        });
    }

    // checks all input strings before sending them to createUser
    public void submitCreate(View view) {
        TextView emailView = findViewById(R.id.email),
                usernameView = findViewById(R.id.username),
                passwordView = findViewById(R.id.newPass1),
                password2View = findViewById(R.id.newPass2);
        String email = emailView.getText().toString(),
                username = usernameView.getText().toString(),
                password = passwordView.getText().toString(),
                password2 = password2View.getText().toString();
    
    
        Log.e("DEBUG", "email = " + email);
        Log.e("DEBUG", "username = " + username);
        Log.e("DEBUG", "password = " + password);

        // check if any strings are empty
        if(username.isEmpty()) Toast.makeText(CreateAccount.this, "Create a Username", Toast.LENGTH_LONG).show();
        else if(email.isEmpty()) Toast.makeText(CreateAccount.this, "Fill out an Email", Toast.LENGTH_LONG).show();
        else if(password.isEmpty()) Toast.makeText(CreateAccount.this, "Create a Password", Toast.LENGTH_LONG).show();
        else if(password2.isEmpty()) Toast.makeText(CreateAccount.this, "Retype your Password", Toast.LENGTH_LONG).show();
        // check if passwords match
        else if(!password.equals(password2)) Toast.makeText(CreateAccount.this, "Passwords Don't Match", Toast.LENGTH_LONG).show();
        else{
            usernameView.setText("");
            emailView.setText("");
            passwordView.setText("");
            password2View.setText("");
    
            Toast.makeText(CreateAccount.this, "Email is being sent", Toast.LENGTH_LONG).show();
            createUser(email, username, password, password2);
        }
    }

    // go back to login page
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

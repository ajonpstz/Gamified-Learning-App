// this activity is the page for creating a new account

package com.example.gamified_learning_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
           if(task.isSuccessful()){
               Toast.makeText(CreateAccount.this, "Login Successful!", Toast.LENGTH_LONG).show();

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

               // go to homepage
               Intent intent = new Intent(CreateAccount.this, Homepage.class);
               startActivity(intent);
               overridePendingTransition(R.anim.slide_in_south, R.anim.slide_out_south);
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

        // check if any strings are empty
        if(email.isEmpty()) Toast.makeText(CreateAccount.this, "Fill out an Email", Toast.LENGTH_LONG).show();
        else if(username.isEmpty()) Toast.makeText(CreateAccount.this, "Create a Username", Toast.LENGTH_LONG).show();
        else if(password.isEmpty()) Toast.makeText(CreateAccount.this, "Create a Password", Toast.LENGTH_LONG).show();
        else if(password2.isEmpty()) Toast.makeText(CreateAccount.this, "Retype your Password", Toast.LENGTH_LONG).show();
        // check if passwords match
        else if(!password.equals(password2)) Toast.makeText(CreateAccount.this, "Passwords Don't Match", Toast.LENGTH_LONG).show();
        else createUser(email, username, password, password2);
    }

    // go back to login page
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

// this is the homepage you are sent to after you log in. It displays your account details/stats

package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gamified_learning_app.tool.FirebaseDBManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;


public class Homepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        user = mAuth.getCurrentUser();
        setContentView(R.layout.activity_homepage);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        if (user == null) {
        } else {
            // values that dont use firestore
            TextView userName = findViewById(R.id.userName);
            TextView email = findViewById(R.id.email);
    
            FirebaseDBManager.getUser(user.getDisplayName(), user->{
                userName.setText(user.username);
                email.setText(user.email);
                return null;
            }, nothing->{
                goToLogin(null);
                return null;
            });

            TextView successfulTasks = findViewById(R.id.successfulTasks);
            TextView attempted = findViewById(R.id.attempted);
            TextView rate = findViewById(R.id.rate);
            
            successfulTasks.setText(Long.toString(0));
            attempted.setText(Long.toString(0));
            rate.setText(Double.toString(0));
        }
    }

    public void goToLogin(View view) {
        mAuth.signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToCourses(View view){
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToChangePass(View view){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToChangeEmail(View view){
        Intent intent = new Intent(this, ChangeEmail.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
package com.example.gamified_learning_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Homepage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        FirebaseUser user = mAuth.getCurrentUser();
        //userName.setText(user.getDisplayName());
        setContentView(R.layout.activity_homepage);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                } else {
                    // values that dont use firestore
                    TextView userName = findViewById(R.id.userName);
                    TextView email = findViewById(R.id.email);
                    userName.setText(user.getDisplayName());
                    email.setText(user.getEmail());

                    // values that do need firestore
                    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

                    TextView successfulTasks = findViewById(R.id.successfulTasks);
                    TextView attempted = findViewById(R.id.attempted);
                    TextView rate = findViewById(R.id.rate);

                    DocumentReference userDetails = mDatabase.collection("userData").document(user.getUid());
                    userDetails.get().addOnSuccessListener(snapshot ->
                    {
                        successfulTasks.setText(Long.toString(snapshot.getLong("correctTasks")));
                        attempted.setText(Long.toString(snapshot.getLong("attemptedTasks")));

                        // calculate success rate
                        double percentage;
                        if(!attempted.getText().toString().equals("0"))
                        {
                            percentage = Double.parseDouble(successfulTasks.getText().toString()) / Integer.parseInt(attempted.getText().toString()) * 100.00;
                        }
                        else
                        {
                            percentage = 0;
                        }
                        rate.setText(String.format("%.2f", percentage) + "%");
                    });
                }
            }
        });
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
}
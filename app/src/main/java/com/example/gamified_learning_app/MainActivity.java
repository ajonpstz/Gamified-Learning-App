package com.example.gamified_learning_app;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.gamified_learning_app.tool.DBManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    public FirebaseAuth mAuth;
    public DBManager manager;
    
    private static final String TAG = "loginLogout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        manager = new DBManager(this);
        setContentView(R.layout.activity_main);
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null)
                    System.out.println("onAuthStateChanged:signed_out:");
                else {
                    System.out.println("onAuthStateChanged:signed_in: " + user.getEmail());
                }
            }
        });
    }

    public void goToHomepage(View view)
    {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_south, R.anim.slide_out_south);
    }
    
    public String createAccount(String email, String username, String password) {
        if (valid(email) && valid(username) && valid(password)) {
            Log.d(TAG, "create account:" + email);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                MainActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // This is where you would put code for what happens after login
                    }
                });
            // NULL is success
            return null;
        } else
            return "failed";
    }
    public void signIn(String email, String password) {
        if (valid(email) && valid(password)) {
            mAuth.signInWithEmailAndPassword(email, password);
        }
    }
    public void signOut() { mAuth.signOut(); }
    public void deleteAccount() {
        ContentValues values = new ContentValues();
        values.put("email", mAuth.getCurrentUser().getEmail());
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().delete().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    System.out.println("deleted: " + values.getAsString("email"));
                }
            });
        }
    }
    boolean valid(String str) {  return !TextUtils.isEmpty(str); }
}

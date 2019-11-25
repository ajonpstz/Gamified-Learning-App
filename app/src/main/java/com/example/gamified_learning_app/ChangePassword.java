// this activity is the page where you can change your password for your account

package com.example.gamified_learning_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void submitChangePass(View view) {
        ProgressBar loading = findViewById(R.id.loading);
        loading.setProgress(1);

        TextView newPass1View = findViewById(R.id.newPass1);
        TextView newPass2View = findViewById(R.id.newPass2);

        String newPass1 = newPass1View.getText().toString(),
                newPass2 = newPass2View.getText().toString();

        newPass1View.setText("");
        newPass2View.setText("");

        // check password
        if(!newPass1.isEmpty() || !newPass2.isEmpty())
        {
            // check if passwords match
            if (newPass1.equals(newPass2)) {
                // check if new password meets the minimum password requirements
                if(newPass1.length() >= 6)
                {
                    user.updatePassword(newPass1).addOnCompleteListener( task2 -> {
                        if (task2.isSuccessful()) {
                            Toast.makeText(ChangePassword.this, "Password Changed " + newPass1, Toast.LENGTH_LONG).show();

                            FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
                            DocumentReference userDetails = mDatabase.collection("userData").document(user.getUid());

                            Map<String, Object> doc = new HashMap<>();
                            doc.put("password", newPass1);

                            userDetails.set(doc, SetOptions.merge());

                        } else {
                            Toast.makeText(ChangePassword.this, "Error, unable to change password.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Toast.makeText(ChangePassword.this, "Password must contain at least 6 characters", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ChangePassword.this, "New Passwords don't match!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void goBack(View view){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


}

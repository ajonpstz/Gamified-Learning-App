// this activity is the page where you can change your password for your account

package com.example.gamified_learning_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private AuthCredential mCred;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void submitChangePass(View view) {
        TextView oldPassView = findViewById(R.id.oldPass);
        TextView newPass1View = findViewById(R.id.newPass1);
        TextView newPass2View = findViewById(R.id.newPass2);

        String oldPass = oldPassView.getText().toString(),
                newPass1 = newPass1View.getText().toString(),
                newPass2 = newPass2View.getText().toString();

        mCred = EmailAuthProvider.getCredential(user.getEmail(), oldPass);

        // check password
        user.reauthenticate(mCred).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // check if new passwords match
                    if (newPass1.equals(newPass2)) {
                        user.updatePassword(newPass1).addOnCompleteListener( task2 -> {
                            if (task2.isSuccessful()) {
                                Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ChangePassword.this, "Error, unable to change password.", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(ChangePassword.this, "New Passwords don't match!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangePassword.this, "Incorrect Password!", Toast.LENGTH_LONG).show();
                }
            });


    }

    public void goBack(View view){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


}

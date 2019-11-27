package com.example.gamified_learning_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmail extends AppCompatActivity
{
    FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    // updates email and sends verification email
    private void changeEmail(String email)
    {
        user.updateEmail(email).addOnCompleteListener(validEmail ->{
           if(validEmail.isSuccessful())
           {
               user.sendEmailVerification().addOnCompleteListener(emailSent ->{
                  if(emailSent.isSuccessful())
                  {
                      Toast.makeText(ChangeEmail.this, "A Confirmation Email has been Sent", Toast.LENGTH_LONG).show();
                  }
                  else{
                      Toast.makeText(ChangeEmail.this, "Error, Email could not be Sent", Toast.LENGTH_LONG).show();
                  }
               });
           }
           else{
               Toast.makeText(ChangeEmail.this, "Invalid Email Address", Toast.LENGTH_LONG).show();
           }
        });
    }

    public void submitEmail(View view)
    {
        TextView emailView = findViewById(R.id.newEmail);

        String email = emailView.getText().toString();
        emailView.setText("");

        // check if email is empty
        if(email.isEmpty()) {
            Toast.makeText(ChangeEmail.this, "Insert an Email Address", Toast.LENGTH_LONG).show();
        }
        else{
            changeEmail(email);
        }
    }
}

package com.example.gamified_learning_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void goToHomepage(View view)
    {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_south, R.anim.slide_out_south);
    }
}

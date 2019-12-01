package com.example.gamified_learning_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gamified_learning_app.data.CardSet;
import com.example.gamified_learning_app.tool.FirebaseDBManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class Courses extends AppCompatActivity {

    LinearLayout llayout;
    private FirebaseAuth mAuth;
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<CardSet> cardSetList;
    Button buttonHighlighted = null;

    public static CardSet activeSet = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardSetList = new ArrayList<CardSet>();
        mAuth = FirebaseAuth.getInstance();

        llayout = (LinearLayout) findViewById(R.id.scrollLayout);

        setContentView(R.layout.activity_courses);

        //scrollView = (ScrollView) findViewById(R.id.mainScroll);

        FirebaseDBManager.getCardSet(mAuth.getCurrentUser().getDisplayName(), cardSets -> {
            cardSetList.addAll(cardSets);
            for (int i = 0; i < cardSetList.size(); i++){
                Button b = new Button(getApplicationContext());
                b.setText(cardSetList.get(i).name);
                b.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
                b.setPadding(50,50,50,50);
                b.setTextSize(20);
                b.setBackgroundResource(R.drawable.text_border);

                final String msg = cardSetList.get(i).name;
                final int index = i;

                b.setOnClickListener(v -> {
                    System.out.println(msg);
                    if (buttonHighlighted != null){
                        //un-Highlight
                        buttonHighlighted.setBackgroundColor(Color.TRANSPARENT);
                        buttonHighlighted.setBackgroundResource(R.drawable.text_border);
                    }
                    buttonHighlighted = b;
                    activeSet = cardSetList.get(index);
                    b.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                    startActivity(new Intent(Courses.this, CourseOptionsPopup.class));

                });
                runOnUiThread(()->{
                    LinearLayout ll = (LinearLayout) findViewById(R.id.scrollLayout);
                    ll.addView(b);
                });

            }
            runOnUiThread(()->{
                LinearLayout ll = (LinearLayout) findViewById(R.id.scrollLayout);
                Button b = new Button(getApplicationContext());
                b.setText("ADD SET");
                b.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
                b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent_secondary));
                b.setPadding(50,50,50,50);
                b.setTextSize(20);
                //b.setBackgroundResource(R.drawable.text_border);
                ll.addView(b);
            });
            return null;
        }, e->{
            return null;
        });
    }



    public void goToLogin(View view) {
        mAuth.signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void goToHomepage(View view){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

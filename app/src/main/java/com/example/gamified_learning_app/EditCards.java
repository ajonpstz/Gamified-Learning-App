package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gamified_learning_app.data.CardSet;

public class EditCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cards);

        LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.scrollLayout);
        for (int i = 0; i < Courses.activeSet.cards.size(); i++){
            CardSet.Card card = Courses.activeSet.cards.get(i);
            EditText termText = new EditText(getApplicationContext());
            termText.setText(card.term);
            termText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
            termText.setPadding(50,50,50,50);
            termText.setTextSize(20);
            scrollLayout.addView(termText);

            EditText definitionText = new EditText(getApplicationContext());
            definitionText.setText(card.definition);
            definitionText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
            definitionText.setPadding(50,50,50,50);
            definitionText.setTextSize(20);
            definitionText.setBackgroundResource(R.drawable.text_border);
            scrollLayout.addView(definitionText);
        }

        Button addCard = new Button(getApplicationContext());
        addCard.setText("ADD CARD");
        scrollLayout.addView(addCard);

        Button publish = new Button(getApplicationContext());
        publish.setText("PUBLISH");
        scrollLayout.addView(publish);


    }

    public void goToLogin(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    public void goToHomepage(View view){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    public void goToCourses(View view){
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

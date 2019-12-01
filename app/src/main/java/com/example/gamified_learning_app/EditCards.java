package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gamified_learning_app.data.CardSet;

public class EditCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cards);

        LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.scrollLayout);

        TextView tmp = new TextView(getApplicationContext());
        tmp.setText("Name");
        tmp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        tmp.setPadding(50,50,50,50);
        tmp.setTextSize(20);
        scrollLayout.addView(tmp);
        EditText edittmp = new EditText(getApplicationContext());
        edittmp.setText(Courses.activeSet.name);
        edittmp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        edittmp.setPadding(50,50,50,50);
        edittmp.setTextSize(20);
        edittmp.setBackgroundResource(R.drawable.text_border);
        scrollLayout.addView(edittmp);

        tmp = new TextView(getApplicationContext());
        tmp.setText("Owner");
        tmp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        tmp.setPadding(50,50,50,50);
        tmp.setTextSize(20);
        scrollLayout.addView(tmp);
        tmp = new TextView(getApplicationContext());
        tmp.setText(Courses.activeSet.owner);
        tmp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        tmp.setPadding(50,50,50,50);
        tmp.setTextSize(20);
        tmp.setBackgroundResource(R.drawable.text_border);
        scrollLayout.addView(tmp);

        tmp = new TextView(getApplicationContext());
        tmp.setText("Description");
        tmp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        tmp.setPadding(50,50,50,50);
        tmp.setTextSize(20);
        scrollLayout.addView(tmp);
        edittmp = new EditText(getApplicationContext());
        edittmp.setText(Courses.activeSet.description);
        edittmp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        edittmp.setPadding(50,50,50,50);
        edittmp.setTextSize(20);
        edittmp.setBackgroundResource(R.drawable.text_border);
        scrollLayout.addView(edittmp);



        for (int i = 0; i < Courses.activeSet.cards.size(); i++){
            CardSet.Card card = Courses.activeSet.cards.get(i);
            EditText termText = new EditText(getApplicationContext());
            termText.setText(card.term);
            termText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
            termText.setPadding(50,50,50,50);
            if (i == 0){
                termText.setPadding(50,300,50,50);
            }
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
        addCard.setOnClickListener((View view)->{
            addCard(view);
        });
        scrollLayout.addView(addCard);

        Button publish = new Button(getApplicationContext());
        publish.setText("PUBLISH");
        publish.setOnClickListener((View view)->{
            publish(view);
        });
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

    public void addCard(View view){
        LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.scrollLayout);

        EditText termText = new EditText(getApplicationContext());
        termText.setText("New Term");
        termText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        termText.setPadding(50,50,50,50);
        termText.setTextSize(20);

        EditText definitionText = new EditText(getApplicationContext());
        definitionText.setText("New Definition");
        definitionText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
        definitionText.setPadding(50,50,50,50);
        definitionText.setTextSize(20);
        definitionText.setBackgroundResource(R.drawable.text_border);
        scrollLayout.addView(termText, scrollLayout.getChildCount()-2);
        scrollLayout.addView(definitionText,scrollLayout.getChildCount()-2);
    }

    public void publish (View view){

    }
}

package com.example.gamified_learning_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gamified_learning_app.data.CardSet;
import com.example.gamified_learning_app.tool.FirebaseDBManager;

import java.util.ArrayList;

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
        publish.setText("SAVE");
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
        String previousName = Courses.activeSet.name;
        ArrayList<CardSet.Card> newCards = new ArrayList<>();
        LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.scrollLayout);

        for (int i = 0; i < scrollLayout.getChildCount()-2; i++){
            System.out.println("Scroll Here:" + i);
            if (i == 1){
                TextView tv = (TextView)scrollLayout.getChildAt(i);
                Courses.activeSet.name = tv.getText().toString();
            }
            else if (i == 5){
                TextView tv = (TextView)scrollLayout.getChildAt(i);
                Courses.activeSet.description = tv.getText().toString();
            }
            else if (i >= 6){
                EditText tv = (EditText)  scrollLayout.getChildAt(i);
                i++;
                EditText et = (EditText) scrollLayout.getChildAt(i);

                CardSet.Card c = new CardSet.Card(tv.getText().toString(), et.getText().toString());
                System.out.println("NAMES:" + tv.getText().toString() + "\t" + et.getText().toString());
                newCards.add(c);
            }

        }
        Courses.activeSet.cards = newCards;

        FirebaseDBManager.updateCardSet(Courses.activeSet, (CardSet cs)->{
            Toast.makeText(this, "Publish Successful!", Toast.LENGTH_LONG).show();
            return null;
        }, (String str)->{
            Dialog dialog = new Dialog(getApplicationContext());
            Toast.makeText(this, "Publish Unsuccessful!", Toast.LENGTH_LONG).show();
            return null;
        });
    }
}

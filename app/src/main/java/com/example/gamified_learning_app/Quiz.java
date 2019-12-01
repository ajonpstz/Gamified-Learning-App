package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gamified_learning_app.data.CardSet;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class Quiz extends AppCompatActivity {
    private int cardNumber;
    private CardSet cardDeck;
    private CardSet.Card currentCard;

    private int correct = 0;
    private int incorrect = 0;

    private TextView remainingCount, completedCount, percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        remainingCount = (TextView) findViewById(R.id.remainingCount);
        completedCount = (TextView) findViewById(R.id.completedCount);
        percentage = (TextView) findViewById(R.id.percentageText);

        if (Courses.activeSet == null) {
            return;
        }else{
            cardDeck = Courses.activeSet;
            if (cardDeck == null || cardDeck.cards.size() == 0) return;
            cardNumber = 0;
            currentCard = cardDeck.cards.get(cardNumber);
            setDefinitionText();
        }
    }

    private void setDefinitionText(){
        TextView defText = (TextView) findViewById(R.id.textDefinition);
        TextInputEditText tiet = (TextInputEditText) findViewById(R.id.answer);
        currentCard = cardDeck.cards.get(cardNumber);
        defText.setText(currentCard.definition);
        completedCount.setText(Integer.toString(cardNumber));
        remainingCount.setText(Integer.toString(cardDeck.cards.size() - cardNumber));
        if (correct+incorrect == 0){
            percentage.setText("");
        }
        else {
            percentage.setText(Integer.toString((int)Math.round(100.0*((double)correct/(double)(correct+incorrect)))) + "%");
        }

        tiet.setText("");

    }

    public void onEnter(View view){
        //test if term is correct
        TextInputEditText termText = (TextInputEditText) findViewById(R.id.answer);
        String correctTerm = cardDeck.cards.get(cardNumber).term;
        ImageView enterImage = (ImageView) findViewById(R.id.enter);

        boolean pass = false;
        try{
            String enteredString = termText.getText().toString();
            pass = enteredString.equalsIgnoreCase(correctTerm);
        }catch (NullPointerException e){
            pass = false;
        }

        if (pass){
            correct++;
        }
        else {
            incorrect++;
        }



        if (cardNumber+1 >= cardDeck.cards.size()){
            //end of deck
            enterImage.setVisibility(View.GONE);
            remainingCount.setText("0");
            completedCount.setText(Integer.toString(cardDeck.cards.size()));
            int per = (int)Math.round(100*(double)correct/(double)cardDeck.cards.size());

            percentage.setText(Integer.toString(per) + "%");

            TextView conclusion = new TextView(getApplicationContext());

            String badge = "You have not gained any achievements in "+ cardDeck.name + ".";

            if (per >= 95){//gold
                badge = "You have gained a Gold Badge in " + cardDeck.name + ".";
            }
            else if (per >= 90){//silver
                badge = "You have gained a Silver Badge in " + cardDeck.name + ".";
            }else if (per >= 85){//bronze
                badge = "You have gained a Bronze Badge in " + cardDeck.name + ".";
            }

            conclusion.setText(badge);
            conclusion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
            conclusion.setPadding(50,50,50,50);
            conclusion.setTextSize(20);

            LinearLayout ll = (LinearLayout) findViewById(R.id.scrollLayout);
            ll.addView(conclusion);

            Calendar calendar = Calendar.getInstance();
            Courses.activeSet.add(new CardSet.Event(calendar.getTime(), (double)correct/(double)Courses.activeSet.cards.size(), 0.0));
            //UPDATE SET to server
        }
        else {

            cardNumber++;
            setDefinitionText();

        }




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

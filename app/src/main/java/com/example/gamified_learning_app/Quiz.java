package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamified_learning_app.data.CardSet;
import com.google.android.material.textfield.TextInputEditText;

public class Quiz extends AppCompatActivity {
    private int cardNumber;
    private CardSet cardDeck;
    private CardSet.Card currentCard;

    private int correct = 0;
    private int incorrect = 0;

    private TextView correctCount, incorrectCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        correctCount = (TextView) findViewById(R.id.correctCount);
        incorrectCount = (TextView) findViewById(R.id.incorrectCount);

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

        correctCount.setText(Integer.toString(correct));
        incorrectCount.setText(Integer.toString(incorrect));

        if (cardNumber+1 >= cardDeck.cards.size()){
            //end of deck
            enterImage.setVisibility(View.GONE);

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

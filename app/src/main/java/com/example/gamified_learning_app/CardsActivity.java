package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamified_learning_app.data.CardSet;

public class CardsActivity extends AppCompatActivity {

    private boolean term;
    private int cardNumber;
    private CardSet cardDeck;
    private CardSet.Card currentCard;

    private Button cardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        cardButton = (Button) findViewById(R.id.card);

        if (Courses.activeSet == null) {
            return;
        }else{
            cardDeck = Courses.activeSet;
            if (cardDeck == null || cardDeck.cards.size() == 0) return;
            term = true;
            cardNumber = 0;
            currentCard = cardDeck.cards.get(cardNumber);
            setCardText();
        }
    }

    private void setCardText(){
        currentCard = cardDeck.cards.get(cardNumber);

        if (term){
            cardButton.setText(currentCard.term);
        }
        else {
            cardButton.setText(currentCard.definition);
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

    public void doFlip(View view){
        term = !term;
        setCardText();
    }

    public void nextCard(View view){
        if (cardNumber+1 < cardDeck.cards.size()){
            cardNumber++;
            term = true;
            setCardText();
        }
    }
    public void previousCard(View view){
        if (cardNumber-1 >= 0){
            cardNumber--;
            term = true;
            setCardText();
        }
    }


}

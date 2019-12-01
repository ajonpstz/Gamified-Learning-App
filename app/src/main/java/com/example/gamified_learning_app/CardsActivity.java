package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamified_learning_app.course.Card;
import com.example.gamified_learning_app.course.Course;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {

    private boolean term;
    private int cardNumber;
    private ArrayList<Card> cardDeck;
    private Card currentCard;

    private Button cardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        cardButton = (Button) findViewById(R.id.card);

        if (Course.ActiveCourse == null) {
            return;
        }else{
            cardDeck = Course.ActiveCourse.getCardDeck();
            if (cardDeck == null || cardDeck.size() == 0) return;
            term = true;
            cardNumber = 0;
            currentCard = cardDeck.get(cardNumber);
            setCardText();
        }
    }

    private void setCardText(){
        if (term){
            cardButton.setText(currentCard.getTerm());
        }
        else {
            cardButton.setText(currentCard.getDefinition());
        }
    }

    public void goToLogin(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

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

    }

    public void nextCard(View view){
        if (cardNumber+1 < cardDeck.size()){
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

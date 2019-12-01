package com.example.gamified_learning_app.course;

import java.util.ArrayList;

public class Course {
    private ArrayList<Card> cardDeck;
    private String title;

    public Course(String title, ArrayList<Card> cardDeck){
        this.cardDeck = cardDeck;
        this.title = title;
    }

    public String getTitle() {return this.title;}
    public ArrayList<Card> getCardDeck(){return this.cardDeck;}


}

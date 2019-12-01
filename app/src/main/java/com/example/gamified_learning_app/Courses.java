package com.example.gamified_learning_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gamified_learning_app.data.CardSet;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;


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

        //temporary local data for experimentation
        CardSet.Card numberCards[] = new CardSet.Card[] {
                new CardSet.Card("one","1"),
                new CardSet.Card("two", "2"),
                new CardSet.Card("three", "3"),
                new CardSet.Card("four", "4")
        };
        CardSet.Card osCards[] = new CardSet.Card[] {
                new CardSet.Card("access method","The method that is used to find a file, a record, or a set of records."),
                new CardSet.Card("application programming interface (API)", "A standardized library of programming tools used by software developers to write applications that are compatible with a specific operating system or graphic user interface."),
                new CardSet.Card("asynchronous operation", "An operation that occurs without a regular or predictable time relationship to a specified event, for example, the calling of an error diagnostic routine that may receive control at any time during the execution of a computer program."),
                new CardSet.Card("Beowulf", "Defines a class of clustered computing that focuses on minimizing the price-to-performance ratio of the overall system without compromising its ability to perform the computation work for which it is being built. Most Beowulf systems are implemented on Linux computers.")
        };

        //(String owner, String name, String description, List<Event> history, List<Card> cards)
        cardSetList.add(new CardSet("owner", "Numbers", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(numberCards))));
        cardSetList.add(new CardSet("owner", "Operating System", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System2", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System3", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System4", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System5", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System6", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System7", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System8", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System9", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));
        cardSetList.add(new CardSet("owner", "Operating System10", "description", null,
                new ArrayList<CardSet.Card>(Arrays.asList(osCards))));

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_courses);

        //scrollView = (ScrollView) findViewById(R.id.mainScroll);


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

            llayout.addView(b);
        }
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

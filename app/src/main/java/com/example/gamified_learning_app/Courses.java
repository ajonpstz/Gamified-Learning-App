package com.example.gamified_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gamified_learning_app.course.Card;
import com.example.gamified_learning_app.course.Course;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;


public class Courses extends AppCompatActivity {

    LinearLayout llayout;
    //ScrollView scrollView;

    private FirebaseAuth mAuth;

    ArrayList<TextView> textViews = new ArrayList<>();

    ArrayList<Course> courseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseList = new ArrayList<Course>();
        Card numberCards[] = new Card[] {
                new Card("one","1"),
                new Card("two", "2"),
                new Card("three", "3"),
                new Card("four", "4")
        };
        Card osCards[] = new Card[] {
                new Card("access method","The method that is used to find a file, a record, or a set of records."),
                new Card("application programming interface (API)", "A standardized library of programming tools used by software developers to write applications that are compatible with a specific operating system or graphic user interface."),
                new Card("asynchronous operation", "An operation that occurs without a regular or predictable time relationship to a specified event, for example, the calling of an error diagnostic routine that may receive control at any time during the execution of a computer program."),
                new Card("Beowulf", "Defines a class of clustered computing that focuses on minimizing the price-to-performance ratio of the overall system without compromising its ability to perform the computation work for which it is being built. Most Beowulf systems are implemented on Linux computers.")
        };

        courseList.add(new Course("Numbers", new ArrayList<Card>(Arrays.asList(numberCards))));
        courseList.add(new Course("Operating System1", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System2", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System3", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System4", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System5", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System6", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System7", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System8", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System9", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System10", new ArrayList<Card>(Arrays.asList(osCards))));
        courseList.add(new Course("Operating System11", new ArrayList<Card>(Arrays.asList(osCards))));







        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_courses);
        llayout = (LinearLayout) findViewById(R.id.scrollLayout);
        //scrollView = (ScrollView) findViewById(R.id.mainScroll);


        for (int i = 0 ; i < courseList.size(); i++){
            Button b = new Button(getApplicationContext());
            b.setText(courseList.get(i).getTitle());
            b.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
            b.setPadding(50,50,50,50);
            b.setTextSize(20);
            b.setBackgroundResource(R.drawable.text_border);

            final String msg = courseList.get(i).getTitle();
            b.setOnClickListener(v -> {
                System.out.println(msg);
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

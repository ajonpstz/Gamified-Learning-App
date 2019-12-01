package com.example.gamified_learning_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Date;

public class CourseOptionsPopup extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.course_options_popup);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Button b = (Button) findViewById(R.id.view);
        b.measure(0,0);
        int bh = b.getMeasuredHeight()+5;


        //getWindow().setLayout(width, b.getMeasuredHeight()+5);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = width;
        lp.height = bh;
        lp.gravity = Gravity.TOP;
        lp.y = height - bh;
        System.out.println("BH:" + (height - bh));
        System.out.println("lp:");
        //d.show();
        getWindow().setAttributes(lp);

        TextView nextReview = (TextView) findViewById(R.id.nextReview);
        Date nextDate = Courses.activeSet.nextScheduled();
        int hour = nextDate.getHours() +1;
        String m = "am";
        if (hour > 12) {
            hour-=12;
            m = "pm";
        }
        nextReview.setText("Next Review\n" +
                (nextDate.getMonth()+1) + "/" + (nextDate.getDay()+1) + "/" + (nextDate.getYear()+1900) + "\n" +
                hour + ":" +(nextDate.getMinutes()) + " " + m);

    }

    public void goToCards(View view) {
        Intent intent = new Intent(this, CardsActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToViewCard(View view){
        Intent intent = new Intent(this, ViewCard.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToQuiz(View view){
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToEdit(View view){
        Intent intent = new Intent(this, EditCards.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }




}
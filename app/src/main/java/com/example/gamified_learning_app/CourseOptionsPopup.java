package com.example.gamified_learning_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

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
    }

    public void goToCards(View view) {

        Intent intent = new Intent(this, CardsActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }




}
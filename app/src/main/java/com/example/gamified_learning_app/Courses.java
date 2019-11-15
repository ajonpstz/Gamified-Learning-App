package com.example.gamified_learning_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gamified_learning_app.networkio.NetworkConstants;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class Courses extends AppCompatActivity {

    ImageView load_icon;
    Animation rotate_anim;
    LinearLayout llayout;

    private FirebaseAuth mAuth;

    ArrayList<TextView> textViews = new ArrayList<>();


    private Socket socket;
    {
        try{
            socket = IO.socket("http://" + NetworkConstants.SERVER_IP + ":" + NetworkConstants.SERVER_PORT + "/");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                }
            }).on("Courses", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args != null && args.length >= 1){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                load_icon.clearAnimation();
                                llayout.removeView(load_icon);
                                String[] courses = ((String)args[0]).split("\n");
                                for (int i = 0 ; i < courses.length; i ++){
                                    System.out.println(courses[i]);
                                    TextView t = new TextView(getApplicationContext());
                                    t.setText(courses[i]);
                                    t.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
                                    t.setPadding(50,50,50,50);
                                    t.setTextSize(20);
                                    t.setBackgroundResource(R.drawable.text_border);
                                    //t.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

                                    llayout.addView(t);
                                    textViews.add(t);
                                }
                            }
                        });
                    }
                }
            });
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_courses);

        load_icon = new ImageView(getApplicationContext());
        load_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        load_icon.setImageDrawable(getDrawable(R.drawable.icons8_loading));
        load_icon.setPadding(0,200,0,200);


        llayout = (LinearLayout) findViewById(R.id.linearLayout);
        llayout.addView(load_icon);
        rotate_anim = AnimationUtils.loadAnimation(this,R.anim.rotate);
        load_icon.startAnimation(rotate_anim);

        socket.connect();
    }

    public void goToLogin(View view) {
        mAuth.signOut();

        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);

        socket.disconnect();
    }

    public void goToHomepage(View view){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        overridePendingTransition(0, 0);

        socket.disconnect();
    }
}

package com.example.gamified_learning_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class Courses extends AppCompatActivity {

    private static final int SERVER_PORT = 3000;
    private static final String SERVER_IP = "10.0.2.2";

    ImageView load_icon;
    Animation rotate_anim;
    LinearLayout llayout;


    private Socket socket;
    {
        try{
            socket = IO.socket("http://" + SERVER_IP + ":" + SERVER_PORT + "/");
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
        Intent intent = new Intent(this, MainActivity.class);
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

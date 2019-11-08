package com.example.gamified_learning_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class Courses extends AppCompatActivity {

    private static final int SERVER_PORT = 3000;
    private static final String SERVER_IP = "10.0.2.2";

    ImageView load_icon;
    Animation rotate_anim;


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
                        String[] courses = ((String)args[0]).split("\n");
                        for (int i = 0 ; i < courses.length; i ++){

                        }


                        load_icon.clearAnimation();
                        load_icon.setVisibility(View.INVISIBLE);
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

        load_icon=(ImageView)findViewById(R.id.load_icon);
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

package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;


//import static com.example.krisperezcyrus.lostfound.R.id.startButton;

public class MainActivity extends AppCompatActivity /*implements View.OnClickListener*/

{


    Button startbutton;
    private static int SPLASH_TIME_OUT =2000;
    private android.util.Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentsignin = new Intent(MainActivity.this,SliderIntroActivity.class);
                startActivity(intentsignin);
                finish();
            }
        },SPLASH_TIME_OUT);



        //startbutton = findViewById(startButton);

        //startbutton.setOnClickListener(this);


        Log.d("action","In onCreate");

        FirebaseMessaging.getInstance().subscribeToTopic("your_topic");


    }


    @Override
    protected void onStart() {
        super.onStart();
        log.d("action","In onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log.d("action","In onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        log.d("action","In onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log.d("action","In onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log.d("action","In onDestroy");
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case startButton:
//                log.d("action","Get Started button pushed");
//                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
//                Toast.makeText(this,"Internet Connection Required",Toast.LENGTH_LONG).show();
//                startActivity(intent);
//                break;
//
//        }
//    }
}


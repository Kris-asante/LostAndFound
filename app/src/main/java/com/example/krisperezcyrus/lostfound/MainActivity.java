package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.krisperezcyrus.lostfound.R.id.startButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startbutton;

    private android.util.Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbutton = findViewById(startButton);

        startbutton.setOnClickListener(this);


        Log.d("action","In onCreate");

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case startButton:
                log.d("action","Get Started button pushed");
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                Toast.makeText(this,"Internet Connection Required",Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;

        }
    }
}


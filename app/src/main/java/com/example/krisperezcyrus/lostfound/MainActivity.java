package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startbutton;

    private android.util.Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbutton = findViewById(R.id.startButton);

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
            case R.id.startButton:
                log.d("action","Get Started button pushed");
                Intent intent = new Intent(MainActivity.this,ToastActivity.class);
                Toast.makeText(this,"Please Sign in With Google",Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;

        }
    }
}


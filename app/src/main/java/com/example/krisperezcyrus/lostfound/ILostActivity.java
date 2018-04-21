package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ILostActivity extends AppCompatActivity  {


    FirebaseDatabase database ;
    DatabaseReference myRef ;



    //Button Reportbtn;
    //Button Camerabtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilost);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Trial");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             String value = dataSnapshot.getValue(String.class);
             TextView textView = (TextView) findViewById(R.id.textView);
             textView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void buttonClicked(View view){



        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        myRef.setValue(name);

    }
}

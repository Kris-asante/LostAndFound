package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class IFoundActivity extends AppCompatActivity {

    private FirebaseDatabase database ;
    private DatabaseReference myRef ;
    private EditText editTextname,editTextemail,editTextphone,editTextdescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifound);

        editTextname = findViewById(R.id.name);
        editTextemail =  findViewById(R.id.email);
        editTextphone =  findViewById(R.id.phone);
        editTextdescription =  findViewById(R.id.descriptionTextView);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Found");

    }

    public void buttonClicked(View view) {


        String child = editTextname.getText().toString().trim();
        String child1 = editTextemail.getText().toString().trim();
        String child2 = editTextphone.getText().toString().trim();
        String child3 = editTextdescription.getText().toString().trim();

        if (!TextUtils.isEmpty(child) && !TextUtils.isEmpty(child1) && !TextUtils.isEmpty(child2) && !TextUtils.isEmpty(child3)){

        }


        myRef=database.getReference("Found").child(child);


        myRef.child("Name").push().setValue(editTextname.getText().toString());
        myRef.child("Email").push().setValue(editTextemail.getText().toString());
        myRef.child("Phone").push().setValue(editTextphone.getText().toString());
        myRef.child("Description").push().setValue(editTextdescription.getText().toString());


        Intent intent = new Intent(IFoundActivity.this,HomeFoundActivity.class);
        Toast.makeText(this,"Item Reported",Toast.LENGTH_LONG).show();
        startActivity(intent);



    }
}

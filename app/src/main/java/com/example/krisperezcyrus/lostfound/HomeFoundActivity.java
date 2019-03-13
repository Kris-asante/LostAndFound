package com.example.krisperezcyrus.lostfound;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class HomeFoundActivity extends AppCompatActivity {

    private RecyclerView FoundItemsList;
    private DatabaseReference mDatabase;

    public static final String TAG = HomeFoundActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_found);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FoundItemsList = findViewById(R.id.foundItemsList);
        FoundItemsList.setHasFixedSize(true);
        FoundItemsList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Found");



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder> fbra = new FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder>(

                FoundPost.class,
                R.layout.foundlayout,
                HomeFoundActivity.FounditemViewHolder.class,
                mDatabase
        ) {

            // @Override
            protected void populateViewHolder(HomeFoundActivity.FounditemViewHolder viewHolder, FoundPost model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDescription(model.getDescription());
                //viewHolder.setImage(getApplication(),model.getImage());

            }
        };


        FoundItemsList.setAdapter(fbra);

    }

    public static class FounditemViewHolder extends RecyclerView.ViewHolder {

        public FounditemViewHolder(View itemView) {
            super(itemView);
            View mView = itemView;

        }

        public void setName(String name) {

            TextView postName =  itemView.findViewById(R.id.namepost);
            postName.setText(name);
        }


        public void setEmail(String email) {

            TextView postemail =  itemView.findViewById(R.id.emailpost);
            postemail.setText(email);
        }

        public void setPhone(String phone) {

            TextView postphone = itemView.findViewById(R.id.phonepost);
            postphone.setText(phone);
        }

        public void setDescription(String description) {

            TextView postdescription = itemView.findViewById(R.id.descriptionpost);
            postdescription.setText(description);
        }


    }

    public void dialNumber(View view) {
        TextView textView = findViewById(R.id.phonepost);
        // Use format with "tel:" and phone number to create mPhoneNum.

        String phone = String.format("tel: %s",textView.getText().toString());

        // Create the intent.
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the data for the intent as the phone number.
        dialIntent.setData(Uri.parse(phone));
        // If package resolves to an app, send intent.
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
            Toast.makeText(this,"Opening Dialer",Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
            Toast.makeText(this,"Can't Make Call",Toast.LENGTH_LONG).show();
        }


    }


    public void smsSendMessage(View view) {
        // Find the TextView number_to_call and assign it to textView.
        TextView textView = (TextView) findViewById(R.id.phonepost);
        // Concatenate "smsto:" with phone number to create smsNumber.
        String smsNumber = "smsto:" + textView.getText().toString();
       // Create the intent.
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        // Add the message (sms) with the key ("sms_body").
        smsIntent.putExtra("sms_body", "Hello There, you reported a found item on LOST&FOUND App which I did misplace.\n" +"Let's kindly meet to prove my ownership. " );
        // Set the data for the intent as the phone number.
        smsIntent.setData(Uri.parse(smsNumber));
        // If package resolves (target app installed), send intent.
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
            Toast.makeText(this,"Opening Messaging App",Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_SENDTO Intent.");
            Toast.makeText(this,"Can't SMS message number",Toast.LENGTH_LONG).show();
        }


}

    public void send_Email (View view){
        TextView emailToEmail = findViewById(R.id.emailpost);
        String recipientList = emailToEmail.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] recipients = recipientList.split(",");
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_TEXT, "Hello There, you reported a found item on LOST&FOUND App which I did misplace.\n" +"Let's kindly meet to prove my ownership.\n" +"My Contact :");
        intent.putExtra(Intent.EXTRA_SUBJECT, "You Have Found My Lost Item on LOST&FOUND App");
        intent.setType("message/rfc822");
        Intent mailer = Intent.createChooser(intent, "Send Email");
        startActivity(mailer);
        Toast.makeText(this," Select Email Client",Toast.LENGTH_SHORT).show();
    }



}


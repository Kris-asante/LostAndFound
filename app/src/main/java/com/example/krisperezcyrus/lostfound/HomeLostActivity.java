package com.example.krisperezcyrus.lostfound;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeLostActivity extends AppCompatActivity {

    private RecyclerView LostItemsList ;
    private DatabaseReference mDatabase;

    public static final String TAG = HomeLostActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_lost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        LostItemsList = findViewById(R.id.lostItemsList);
        LostItemsList.setHasFixedSize(true);
        LostItemsList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Lost");


    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <LostPost,HomeLostActivity.LostitemViewHolder> fbra = new FirebaseRecyclerAdapter<LostPost, HomeLostActivity.LostitemViewHolder>(

                LostPost.class,
                R.layout.lostlayout,
                HomeLostActivity.LostitemViewHolder.class,
                mDatabase
        )
        {

           // @Override
            protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(getApplication(),model.getImage());

            }
        };


        LostItemsList.setAdapter(fbra);

    }

    public static class LostitemViewHolder extends RecyclerView.ViewHolder{

        public LostitemViewHolder (View itemView){
            super(itemView);
            View mView = itemView;

        }

        public void setName(String name){

            TextView postName = (TextView)itemView.findViewById(R.id.namepost);
            postName.setText(name);
        }


        public void setEmail(String email) {

            TextView postemail = (TextView) itemView.findViewById(R.id.emailpost);
            postemail.setText(email);
        }

        public void setPhone(String phone) {

            TextView postphone = (TextView) itemView.findViewById(R.id.phonepost);
            postphone.setText(phone);
        }

        public void setDescription(String description) {

            TextView postdescription = (TextView) itemView.findViewById(R.id.descriptionpost);
            postdescription.setText(description);
        }

        public void setImage (Context ctx,String image){

            ImageView postimage = itemView.findViewById(R.id.lostimagespreview);
            Picasso.with(ctx).load(image).into(postimage);
        }

    }

    public void dial_Number(View view) {
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
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
            Toast.makeText(this,"Can't call number",Toast.LENGTH_LONG).show();
        }


    }

    public void sms_SendMessage(View view) {
        // Find the TextView number_to_call and assign it to textView.
        TextView textView = (TextView) findViewById(R.id.phonepost);
        // Concatenate "smsto:" with phone number to create smsNumber.
        String smsNumber = "smsto:" + textView.getText().toString();
        // Create the intent.
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        // Set the data for the intent as the phone number.
        smsIntent.setData(Uri.parse(smsNumber));
        // If package resolves (target app installed), send intent.
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_SENDTO Intent.");
            Toast.makeText(this,"Can't SMS message number",Toast.LENGTH_LONG).show();
        }
    }

    }



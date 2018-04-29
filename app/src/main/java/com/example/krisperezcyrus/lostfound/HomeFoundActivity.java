package com.example.krisperezcyrus.lostfound;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFoundActivity extends AppCompatActivity {

    private RecyclerView FoundItemsList ;
    private DatabaseReference mDatabase;

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
        FirebaseRecyclerAdapter<FoundPost,HomeFoundActivity.FounditemViewHolder> fbra = new FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder>(

                FoundPost.class,
                R.layout.foundlayout,
                HomeFoundActivity.FounditemViewHolder.class,
                mDatabase
        )
        {

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

    public static class FounditemViewHolder extends RecyclerView.ViewHolder{

        public FounditemViewHolder (View itemView){
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

            TextView postphone =  itemView.findViewById(R.id.phonepost);
            postphone.setText(phone);
        }

        public void setDescription(String description) {

            TextView postdescription =  itemView.findViewById(R.id.descriptionpost);
            postdescription.setText(description);
        }

       /* public void setImage (Context ctx, String image){

            ImageView postimage = itemView.findViewById(R.id.foundimagespreview);
            Picasso.with(ctx).load(image).into(postimage);
        }*/

    }

}

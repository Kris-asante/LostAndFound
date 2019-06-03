package com.example.krisperezcyrus.lostfound;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class HomeLostActivity extends AppCompatActivity {


    //for deletion


    private RecyclerView LostItemsList ;
    private DatabaseReference mDatabase;

    LinearLayoutManager mLayoutManager;

    public static final String TAG = HomeLostActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_lost);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        LostItemsList = findViewById(R.id.lostItemsList);
        LostItemsList.setHasFixedSize(true);
        LostItemsList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Lost");



        //how the display of the reported items are arranged in the newest above/top
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);



        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        //RecyclerView

        LostItemsList.setHasFixedSize(true);

        //set layout as LinearLayout
        LostItemsList.setLayoutManager(mLayoutManager);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


//

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //THIS SEARCH IS WORKING
                //HAVE TO ADD CATEGORY TO BROADEN IT
                String query = newText.toLowerCase();
                Query firebaseSearchQuery = mDatabase.orderByChild("search").startAt(query).endAt(query + "\uf8ff");
                FirebaseRecyclerAdapter <LostPost,HomeLostActivity.LostitemViewHolder>
                        fbras = new FirebaseRecyclerAdapter<LostPost, HomeLostActivity.LostitemViewHolder>(

                        LostPost.class,
                        R.layout.lostlayout,
                        HomeLostActivity.LostitemViewHolder.class,
                        firebaseSearchQuery
                )

                {
                    @Override
                    protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {

                        viewHolder.setName(model.getName());
                        viewHolder.setEmail(model.getEmail());
                        viewHolder.setPhone(model.getPhone());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setImage(getApplication(),model.getImage());
                        viewHolder.setTime(model.getGps());




//

                    }
                };

                LostItemsList.setAdapter(fbras);







                return false;
            }


        });


            return true;
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

           @Override
            protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTime(model.getTime());
                viewHolder.setImage(getApplication(),model.getImage());
                viewHolder.setGps(model.getGps());

            }









//
//            @Override
//            public  LostitemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//                LostitemViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
//
//                viewHolder.setOnClickListener(new  LostitemViewHolder.ClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        //Views
//                        TextView postName = view.findViewById(R.id.namepost);
//                        ImageView postimage = view.findViewById(R.id.lostimagespreview);
//
//                        //get data from views
//                        String name = postName.getText().toString();
//
//                        Drawable mDrawable = postimage.getDrawable();
//                        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
//
//                        //pass this data to new activity
//                        Intent intent = new Intent(view.getContext(), HomeLostActivity.class);
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byte[] bytes = stream.toByteArray();
//                        intent.putExtra("image", bytes); //put bitmap image as array of bytes
//                        intent.putExtra("name", name); // put title
//
//                        startActivity(intent); //start activity
//
//
//                    }
//
////                    @Override
////                    public void onItemLongClick(View view, int position) {
////                        //TODO do your own implementaion on long item click
////                    }
//                  });
//
//                return viewHolder;
//            }
//
















        };


        LostItemsList.setAdapter(fbra);


            }

    public void gpslocation(View view) {

        String uri = String.format(Locale.ENGLISH,  "http://maps.google.com/maps?q= 6.673327,-1.567072");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }

    }


    public static class LostitemViewHolder extends RecyclerView.ViewHolder{

        public LostitemViewHolder (View itemView){
            super(itemView);
            View mView = itemView;

        }

        public void setName(String name){

            TextView postName = itemView.findViewById(R.id.namepost);
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

            TextView postdescription =  itemView.findViewById(R.id.descriptionpost);
            postdescription.setText(description);
        }


        public void setTime(String time){

            TextView posttime = itemView.findViewById(R.id.time);
            posttime.setText(time);
        }


        public void setImage (Context ctx,String Image){

            ImageView postimage = itemView.findViewById(R.id.lostimagespreview);
            Picasso.with(ctx).load(Image).into(postimage);
        }

        public void setGps(String gps){

            TextView postgps = itemView.findViewById(R.id.user_location_post);
            postgps.setText(gps);
        }


            }

    public void dial_Number(View view) {
        ImageButton dialbutton = findViewById(R.id.phoneicon);
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
            Toast.makeText(this,"Opening Dialer",Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
            Toast.makeText(this,"Can't call number",Toast.LENGTH_LONG).show();
        }


    }

    public void sms_SendMessage(View view) {
        ImageButton smsmessage = findViewById(R.id.phonemessage);
        // Find the TextView number_to_call and assign it to textView.
        TextView postsms = (TextView) findViewById(R.id.phonepost);
        // Concatenate "smsto:" with phone number to create smsNumber.
        String smsNumber = "smsto:" + postsms.getText().toString();
        // Create the intent.
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        // Add the message (sms) with the key ("sms_body").
        smsIntent.putExtra("sms_body", "Hello There, your reported lost item on LOST&FOUND has been found." +
                " Kindly contact me for identification and collection. " );
        // Set the data for the intent as the phone number.
        smsIntent.setData(Uri.parse(smsNumber));
        // If package resolves (target app installed), send intent.
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(this,"Opening SMS App",Toast.LENGTH_LONG).show();
            startActivity(smsIntent);
            Toast.makeText(this,"Opening Messaging App",Toast.LENGTH_LONG).show();
            }
            else {
            Log.e(TAG, "Can't resolve app for ACTION_SENDTO Intent.");
            Toast.makeText(this,"Can't SMS message number",Toast.LENGTH_LONG).show();
        }
    }
    public void sendEmail (View view){
        ImageButton emailbutton = findViewById(R.id.phoneemail);
        TextView postemail = findViewById(R.id.emailpost);
        String recipientList = postemail.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] recipients = recipientList.split(",");
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "I Have Found Your Lost Item on LOST&FOUND App");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello There, your reported lost item on LOST&FOUND has been found.\n" +"Kindly contact me for identification and collection.\n" +"My Contact :");
        intent.setType("message/rfc822");
        Intent mailer = Intent.createChooser(intent, "Send Email");
            startActivity(mailer);
            Toast.makeText(this," Select Email Client",Toast.LENGTH_LONG).show();
    }

    //public void delete_icon (){

//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot objSnapshot: snapshot.getChildren()) {
//                    Object obj = objSnapshot.getKey();
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError firebaseError) {
//                Log.e("Read failed", firebaseError.getMessage());
//            }
//        });
//
//
//
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        Query applesQuery = mDatabase.child("firebase-test").orderByChild("title").equalTo("Apple");
//
//        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                    appleSnapshot.getRef().removeValue();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAG, "onCancelled", databaseError.toException());
//            }
//        });
//
//
//    }

}



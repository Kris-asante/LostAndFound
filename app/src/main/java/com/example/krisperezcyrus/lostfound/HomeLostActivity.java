package com.example.krisperezcyrus.lostfound;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HomeLostActivity extends AppCompatActivity {

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


    // for the search icon on the app bar. A method was used here: firebaseSearch().Which is way below
		







//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //inflate the menu; this adds items to the action bar if it present
//        getMenuInflater().inflate(R.menu.toolbar_menu_search, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//
//
//
//
//////
////        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
////        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
////        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                firebaseSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Filter as you type
//                firebaseSearch(newText);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        // the previous one there getMenuInflater().inflate(R.menu.toolbar_menu_search,menu);
        // searchView.setQueryHint("Search Item");
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
       // final SearchView searchView =(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.toolbar));
        //searchView.setSubmitButtonEnabled(true);
        //searchView.setSubmitButtonEnabled(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


//                String text = query.toLowerCase();
//
//                Query firebaseSearchQuery = mDatabase.orderByChild("description").startAt(text).endAt(text + "\uf8ff");
//                FirebaseRecyclerAdapter <LostPost,HomeLostActivity.LostitemViewHolder>
//                        fbra = new FirebaseRecyclerAdapter<LostPost, HomeLostActivity.LostitemViewHolder>(
//
//                        LostPost.class,
//                        R.layout.lostlayout,
//                        HomeLostActivity.LostitemViewHolder.class,
//                        firebaseSearchQuery
//                )
//
//                {
//                    //@Override
//                    protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {
//
//                        viewHolder.setName(model.getName());
//                        viewHolder.setEmail(model.getEmail());
//                        viewHolder.setPhone(model.getPhone());
//                        viewHolder.setDescription(model.getDescription());
//                        viewHolder.setTime(model.getTime());
//                        viewHolder.setImage(getApplication(),model.getImage());
//
//
//
//
////                Query Q = mDatabase.child("Lost").orderByChild("description").startAt(newText).endAt("~");
////                // endAt(newText+"\uf8ff");
////                FirebaseRecyclerAdapter <LostPost,HomeLostActivity.LostitemViewHolder> fbra = new FirebaseRecyclerAdapter
////                        <LostPost, HomeLostActivity.LostitemViewHolder>
////                        (
////
////                        LostPost.class,
////                        R.layout.lostlayout,
////                        HomeLostActivity.LostitemViewHolder.class,
////                        mDatabase
////                ) {
////                    //@Override
////                    protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {
////
////                        viewHolder.setName(model.getName());
////                        viewHolder.setEmail(model.getEmail());
////                        viewHolder.setPhone(model.getPhone());
////                        viewHolder.setDescription(model.getDescription());
////                        viewHolder.setTime(model.getTime());
////                        viewHolder.setImage(getApplication(),model.getImage());
//
//                    }
//                };
//
//                LostItemsList.setAdapter(fbra);





                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //SearchText.setText(newText);



               //String text = newText.toLowerCase();

                //THIS SEARCH IS WORKING
                //HAVE TO ADD CATEGORY TO BROADEN IT

                Query firebaseSearchQuery = mDatabase.orderByChild("description").startAt(newText).endAt(newText + "\uf8ff");
                FirebaseRecyclerAdapter <LostPost,HomeLostActivity.LostitemViewHolder>
                        fbras = new FirebaseRecyclerAdapter<LostPost, HomeLostActivity.LostitemViewHolder>(

                        LostPost.class,
                        R.layout.lostlayout,
                        HomeLostActivity.LostitemViewHolder.class,
                        firebaseSearchQuery
                )

                {
                    //@Override
                    protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {

                        viewHolder.setName(model.getName());
                        viewHolder.setEmail(model.getEmail());
                        viewHolder.setPhone(model.getPhone());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setImage(getApplication(),model.getImage());




//                Query Q = mDatabase.child("Lost").orderByChild("description").startAt(newText).endAt("~");
//                // endAt(newText+"\uf8ff");
//                FirebaseRecyclerAdapter <LostPost,HomeLostActivity.LostitemViewHolder> fbra = new FirebaseRecyclerAdapter
//                        <LostPost, HomeLostActivity.LostitemViewHolder>
//                        (
//
//                        LostPost.class,
//                        R.layout.lostlayout,
//                        HomeLostActivity.LostitemViewHolder.class,
//                        mDatabase
//                ) {
//                    //@Override
//                    protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {
//
//                        viewHolder.setName(model.getName());
//                        viewHolder.setEmail(model.getEmail());
//                        viewHolder.setPhone(model.getPhone());
//                        viewHolder.setDescription(model.getDescription());
//                        viewHolder.setTime(model.getTime());
//                        viewHolder.setImage(getApplication(),model.getImage());

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

           // @Override
            protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTime(model.getTime());
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
            Toast.makeText(this,"Opening Dialer",Toast.LENGTH_LONG).show();
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
        TextView emailToEmail = findViewById(R.id.emailpost);
        String recipientList = emailToEmail.getText().toString();
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





    //search data
//    private void firebaseSearch(String searchText) {
//
//        //convert string entered in SearchView to lowercase
//        String query = searchText.toLowerCase();
//
//        Query firebaseSearchQuery = mDatabase.orderByChild("description").startAt(query).endAt(query + "\uf8ff");
//        FirebaseRecyclerAdapter <LostPost,HomeLostActivity.LostitemViewHolder>
//                fbra = new FirebaseRecyclerAdapter<LostPost, HomeLostActivity.LostitemViewHolder>(
//
//                LostPost.class,
//                R.layout.lostlayout,
//                HomeLostActivity.LostitemViewHolder.class,
//                firebaseSearchQuery
//        )
//
//        {
//            @Override
//            protected void populateViewHolder(HomeLostActivity.LostitemViewHolder viewHolder, LostPost model, int position) {
//                viewHolder.setName(model.getName());
//                viewHolder.setEmail(model.getEmail());
//                viewHolder.setPhone(model.getPhone());
//                viewHolder.setDescription(model.getDescription());
//                viewHolder.setTime(model.getTime());
//                viewHolder.setImage(getApplicationContext(), model.getImage());
//                    }
//
//
//            @Override
//            public LostitemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//                LostitemViewHolder lostitemViewHolder = super.onCreateViewHolder(parent, viewType);
//
//                    TextView postName = findViewById(R.id.namepost);
//
//                TextView postemail = findViewById(R.id.emailpost);
//
//                TextView postphone = findViewById(R.id.phonepost);
//
//                TextView postdescription = findViewById(R.id.descriptionpost);
//
//                ImageView postimage = findViewById(R.id.lostimagespreview);
//
//                TextView posttime = findViewById(R.id.time);
//
//
//                 String name = postName.getText().toString().trim();
//                 String email = postemail.getText().toString().trim();
//                 String phone = postphone.getText().toString().trim();
//                 String description = postdescription.getText().toString();
//                 String time = posttime.getText().toString().trim();
//                 String image = postimage.toString().trim();
//
//
//
//                return lostitemViewHolder;
//            }
//
//
//
//                };
//
//        //set adapter to recyclerview
//        LostItemsList.setAdapter(fbra);
//    }
//


}



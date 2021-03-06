package com.example.krisperezcyrus.lostfound;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class HomeFoundActivity extends AppCompatActivity {

    //AsyncTask myAsyncTask;

    LinearLayoutManager mLayoutManager;


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




        //how the display of the reported items are arranged in the newest above/top
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);



        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        //RecyclerView

        FoundItemsList.setHasFixedSize(true);

        //set layout as LinearLayout
        FoundItemsList.setLayoutManager(mLayoutManager);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
////
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.toolbar_menu_search, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//
//        // the previous one there getMenuInflater().inflate(R.menu.toolbar_menu_search,menu);
//        // searchView.setQueryHint("Search Item");
//        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        // final SearchView searchView =(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.toolbar));
//        //searchView.setSubmitButtonEnabled(true);
//        //searchView.setSubmitButtonEnabled(true);
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//
//                String text = query.toLowerCase();
//
//                Query firebaseSearchQuery = mDatabase.orderByChild("description").startAt(text).endAt(text + "\uf8ff");
//                FirebaseRecyclerAdapter <FoundPost,HomeFoundActivity.FounditemViewHolder>
//                        fbra = new FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder>(
//
//                        FoundPost.class,
//                        R.layout.foundlayout,
//                        HomeFoundActivity.FounditemViewHolder.class,
//                        firebaseSearchQuery
//                )
//
//                {
//                    //@Override
//                    protected void populateViewHolder(HomeFoundActivity.FounditemViewHolder viewHolder, FoundPost model, int position) {
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
//                    }
//                };
//
//                FoundItemsList.setAdapter(fbra);
//
//
//
//
//
//                return false;
//            }

           // @Override
//            public boolean onQueryTextChange(String newText) {
//                //SearchText.setText(newText);
//
//
//
//                String text = newText.toLowerCase();
//
//                Query firebaseSearchQuery = mDatabase.orderByChild("description").startAt(text).endAt(text + "\uf8ff");
//                FirebaseRecyclerAdapter <FoundPost,HomeFoundActivity.FounditemViewHolder>
//                        fbra = new FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder>(
//
//                        FoundPost.class,
//                        R.layout.foundlayout,
//                        HomeFoundActivity.FounditemViewHolder.class,
//                        firebaseSearchQuery
//                )
//
//                {
//                    //@Override
//                    protected void populateViewHolder(HomeFoundActivity.FounditemViewHolder viewHolder, FoundPost model, int position) {
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
////
//                    }
//                };
//
//                FoundItemsList.setAdapter(fbra);
//
//
//
//
//
//
//
//                return false;
//            }
//
//
//        });
//
//
//        return true;
//    }



    @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                //inflate the menu; this adds items to the action bar if it present
                getMenuInflater().inflate(R.menu.toolbar_menu_search, menu);
                MenuItem item = menu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        //firebaseSearchs(query);
                        firebaseSearchs(query);

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //Filter as you type
                        //firebaseSearch(newText);
                        firebaseSearch(newText);
                        return false;
                    }
                });
                return super.onCreateOptionsMenu(menu);
            }



    private void firebaseSearch(String searchText) {

        String query = searchText.toLowerCase();

        //convert string entered in SearchView to lowercase
        //String text = searchText.toLowerCase();

        Query firebaseSearchQuery = mDatabase.orderByChild("search").startAt(query).endAt(query + "\uf8ff");
                FirebaseRecyclerAdapter <FoundPost,HomeFoundActivity.FounditemViewHolder>
                       fbra = new FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder>(

                        FoundPost.class,
                        R.layout.foundlayout,
                        HomeFoundActivity.FounditemViewHolder.class,
                        firebaseSearchQuery
        )

        {
            @Override
            protected void populateViewHolder(HomeFoundActivity.FounditemViewHolder viewHolder, FoundPost model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTime(model.getTime());
                viewHolder.setImage(getApplication(),model.getImage());
                viewHolder.setTime(model.getGps());
            }


            @Override
            public HomeFoundActivity.FounditemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                FounditemViewHolder founditemViewHolder = super.onCreateViewHolder(parent, viewType);

//
//                TextView postName = findViewById(R.id.namepost);
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
//                String name = postName.getText().toString().trim();
//                String email = postemail.getText().toString().trim();
//                String phone = postphone.getText().toString().trim();
//                String description = postdescription.getText().toString();
//                String time = posttime.getText().toString().trim();
//                String yourimage = postimage.toString().trim();



                return founditemViewHolder;
            }



        };

        //set adapter to recyclerview
        FoundItemsList.setAdapter(fbra);
    }








    private void firebaseSearchs(String searchText) {

        String query = searchText.toLowerCase();

        //convert string entered in SearchView to lowercase
        //String text = searchText.toLowerCase();

        Query firebaseSearchQuerys = mDatabase.orderByChild("searchs").startAt(query).endAt(query + "\uf8ff");
        FirebaseRecyclerAdapter <FoundPost,HomeFoundActivity.FounditemViewHolder>
                fbra = new FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder>(

                FoundPost.class,
                R.layout.foundlayout,
                HomeFoundActivity.FounditemViewHolder.class,
                firebaseSearchQuerys
        )

        {
            @Override
            protected void populateViewHolder(HomeFoundActivity.FounditemViewHolder viewHolder, FoundPost model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTime(model.getTime());
                viewHolder.setImage(getApplication(),model.getImage());
                viewHolder.setTime(model.getGps());
            }


            @Override
            public HomeFoundActivity.FounditemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                FounditemViewHolder founditemViewHolder = super.onCreateViewHolder(parent, viewType);


                return founditemViewHolder;
            }



        };

        //set adapter to recyclerview
        FoundItemsList.setAdapter(fbra);
    }








    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <FoundPost,HomeFoundActivity.FounditemViewHolder> fbra = new FirebaseRecyclerAdapter<FoundPost, HomeFoundActivity.FounditemViewHolder>(

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
                viewHolder.setTime(model.getTime());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(getApplication(),model.getImage());

            }
        };


        FoundItemsList.setAdapter(fbra);

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


        public void setTime(String time){

            TextView postName = itemView.findViewById(R.id.time);
            postName.setText(time);
        }

        public void setImage (Context ctx,String image){

            ImageView postimage = itemView.findViewById(R.id.foundimagespreview);
           Picasso.with(ctx).load(image).into(postimage);
           //Picasso.get().load(image).into(postimage);
        }

        public void setDescription(String description) {

            TextView postdescription = itemView.findViewById(R.id.descriptionpost);
            postdescription.setText(description);
        }


        public void setGps(String gps){

            TextView postgps = itemView.findViewById(R.id.user_location_post);
            postgps.setText(gps);
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
            Toast.makeText(this,"Opening Dialer",Toast.LENGTH_LONG).show();
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
            Toast.makeText(this,"Opening Messaging App",Toast.LENGTH_LONG).show();
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
        Toast.makeText(this," Select Email Client",Toast.LENGTH_LONG).show();
    }


//    public  class YourAsyncTask extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog dialog;
//
//        public YourAsyncTask(HomeFoundActivity activity) {
//            dialog = new ProgressDialog(activity);
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            dialog.setMessage("Checking Permission.");
//            dialog.show();
//        }
//        @Override
//        protected Void doInBackground(Void... args) {
//            Snackbar.make(findViewById(R.id.deleteicon), "Check Internet Connectivity", Snackbar.LENGTH_LONG).show();
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//            // do UI work here
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//        }
//    }


//    public void delete_icon (){
//
//
//   }


}


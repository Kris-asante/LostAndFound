package com.example.krisperezcyrus.lostfound;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krisperezcyrus.lostfound.Model.MyResponse;
import com.example.krisperezcyrus.lostfound.Model.Notification;
import com.example.krisperezcyrus.lostfound.Model.Sender;
import com.example.krisperezcyrus.lostfound.Remote.APIService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;


public class ILostActivity extends AppCompatActivity  {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 3;
    //for GPS
    Button fetch;
    TextView user_location;
    private FusedLocationProviderClient fusedLocationClient;
    Double latitude;
    Double longitude;




    //to display the notification
    TextView noteTitle,noteBody;

    APIService mService;


    private EditText editTextname,editTextemail,editTextphone,editTextdescription;

    private FirebaseDatabase database ;
    private DatabaseReference databaseReference;

    private StorageReference storageReference;
    private static final int GALLARY_INTENT = 5;
    private ImageView imageView;
    Uri uri;

    private TextView mCount;
    //private ArrayList<String> mDetails = new ArrayList<>();

    //ListView listView;



    //Button Reportbtn;
    //Button Camerabtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilost);

        //for gps
        user_location =  findViewById(R.id.user_location);
        fetch =  findViewById(R.id.fetch_location);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        fetch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                
                fetchLocation();



            }
        });


       editTextname =  findViewById(R.id.name);
       editTextemail =  findViewById(R.id.email);
       editTextphone =  findViewById(R.id.phone);
       editTextdescription = findViewById(R.id.descriptionTextView);

        imageView = findViewById(R.id.Cameraimageview);


        databaseReference = database.getInstance().getReference().child("Lost");


        storageReference = FirebaseStorage.getInstance().getReference();

        mCount = findViewById(R.id.title_counter);


        //for counting derscription characters
        editTextdescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editTextdescription.getText().toString();
                mCount.setText(text.length()+"/100");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        /*listView =(ListView)findViewById(R.id.lostListView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mDetails);
       listView.setAdapter(arrayAdapter);
        databaseReference
       .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class);
                mDetails.add(value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/




        //databaseReference
        //.addValueEventListener(new ValueEventListener() {
            //@Override
           // public void onDataChange(DataSnapshot dataSnapshot) {
             //String value = dataSnapshot.getValue(String.class);
             //Intent intent = new Intent(ILostActivity.this,HomeLostActivity.class);
             //TextView textView = (TextView) findViewById(R.id.text1);
                //TextView textView = (TextView)findViewById(R.id.textView);
             //textView.setText(value);
            //}

           // @Override
           // public void onCancelled(DatabaseError databaseError) {

           // }
      //  });


      //  Common.currentToken = FirebaseInstanceId.getInstance().getToken();

        //for multi users - notification
      //  FirebaseMessaging.getInstance().subscribeToTopic("AAAAxxVisgo:APA91bFT5PZDucRSNtJoHwHTR9pwkvakCKJnB0Wp6kwFuBxwYBbVC_pjzrXzsqJ2mKqVdr0ZSrUAAysOln43MCSOeqf06oD172BsVoqQ6oMKHwycgeeJH-YjIoMYMKTk9hZekgXwxskf");



        // this is for the notification under lost items for single user

             //  mService = Common.getFCMClient();

      /* noteTitle = findViewById(R.id.noteTitle);
       noteTitle.setText("(LOST&amp;FOUND)Lost Item Reported");
       noteBody = findViewById(R.id.noteBody);
       noteBody.setText("Open App to view reported item");*/

    }

    private void fetchLocation() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(ILostActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ILostActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to access the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions(ILostActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .create()
                        .show();

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(ILostActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object

                                Double latitude = location.getLatitude();
                                Double longitude = location.getLongitude();

                                user_location.setText("Latitude = "+latitude + "\nLongitude = " +longitude);
                            }
                        }
                    });


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==  MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION){

          if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {

          }else {

          }
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    public void ilostbuttonClicked(View view){

        boolean cancel = false;
        View focusView = null;

        final String yourname = editTextname.getText().toString().trim();
        final String youremail = editTextemail.getText().toString().trim();
        final String yourphone = editTextphone.getText().toString().trim();
        final String yourdescription = editTextdescription.getText().toString().trim();
        final String yourgps = user_location.getText().toString().trim();


       // databaseReference =database.getReference("Lost").child(child);


        //databaseReference.child("Name").push().setValue(editTextname.getText().toString());
       // databaseReference.child("Email").push().setValue(editTextemail.getText().toString());
        //databaseReference.child("Phone").push().setValue(editTextphone.getText().toString());
       // databaseReference.child("Description").push().setValue(editTextdescription.getText().toString());



//        if (isValidPhoneNumber(editTextphone.getText().toString())
//                && isValidEmailAddress(editTextemail.getText().toString())
//                && !TextUtils.isEmpty(yourname) && !TextUtils.isEmpty(youremail)
//                && !TextUtils.isEmpty(yourphone) && !TextUtils.isEmpty(yourdescription )){


        if (TextUtils.isEmpty(yourname)) {
            editTextname.setError("This field is required");
            focusView = editTextname;
            cancel = true;
        }

        if (!isValidName(editTextname.getText().toString())) {
            editTextname.setError("Enter Valid Name");
            focusView = editTextname;
            cancel = true;
        }

        if (!isValidPhone(editTextphone.getText().toString())) {
            editTextphone.setError("Enter Valid Phone Number");
            focusView = editTextphone;
            cancel = true;
        }

        if (editTextphone.getText().length() <10 ){
            editTextphone.setError("Digits of number not up to 10");
            focusView = editTextphone;
            cancel = true;

        }


        if (TextUtils.isEmpty(yourphone)) {
            editTextphone.setError("This field is required");
            focusView = editTextphone;
            cancel = true;
        }
        if (TextUtils.isEmpty(youremail)) {
            editTextemail.setError("This field is required");
            focusView = editTextemail;
            cancel = true;
        }

        if (TextUtils.isEmpty(yourdescription)) {
            editTextdescription.setError("This field is required");
            focusView = editTextdescription;
            cancel = true;
        }

        if (!isValidEmailAddress(editTextemail.getText().toString())) {
            editTextemail.setError("Enter Valid email address");
            focusView = editTextemail;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        } else {




            if(uri != null) {
                //Uri uri = Uri.parse("android.resource://com.example.krisperezcyrus.lostfound/drawable/noimage");
                // this is for upload of image to the cloud
                final StorageReference filepath = storageReference.child("ImageUpload").child(uri.getLastPathSegment());
                filepath.putFile(uri)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        final Uri downloadurl = taskSnapshot.getDownloadUrl();

                        .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                // Forward any exceptions
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }



                                // Request the public download URL
                                return filepath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String downloadurl = task.getResult().toString();
                            Log.d("VIDEO PREVIEW", "onComplete: Url: " + downloadurl);

                            Toast.makeText(ILostActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                            DatabaseReference newPost = databaseReference.push();
                            newPost.child("name").setValue(yourname);
                            newPost.child("email").setValue(youremail);
                            newPost.child("phone").setValue(yourphone);
                            newPost.child("description").setValue(yourdescription);
                            newPost.child("image").setValue(downloadurl);
                            newPost.child("gps").setValue(yourgps);

                            //NOT QUIET SURE
                            newPost.child("time").setValue(Util_time.getTimestamp());


                            new PushNotification().execute();

                            finish();


                        }

                    }
                });
//                }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(@NonNull Uri downloadUri) {
//                        // Upload succeeded
//                        //Log.d(TAG, "uploadFromUri: getDownloadUri success");
//
//                        // [START_EXCLUDE]
//                        //broadcastUploadFinished(downloadUri, filepath);
//                        //showUploadFinishedNotification(downloadUri, filepath);
//                       // taskCompleted();
//                        // [END_EXCLUDE]
//                    }
//                })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Upload failed
//                               // Log.w(TAG, "uploadFromUri:onFailure", exception);
//
//                                // [START_EXCLUDE]
//                               // broadcastUploadFinished(null, fileUri);
//                                //showUploadFinishedNotification(null, fileUri);
//                                //taskCompleted();
//                                //// [END_EXCLUDE]
//                            }
//                        });




//                Intent intent = new Intent(ILostActivity.this, HomeLostActivity.class);
//                Toast.makeText(this, "Item Reported", Toast.LENGTH_LONG).show();
//                startActivity(intent);
            }else{

                /*send notification to topic
                Notification notification = new Notification(noteTitle.getText().toString(),noteBody.getText().toString());
              Sender sender = new Sender("/topics/MyTopic",notification); //send to itself

                //for notification //send request witj token
//                Notification notification = new Notification(noteTitle.getText().toString(),noteBody.getText().toString());
//                Sender sender = new Sender(Common.currentToken,notification); //send to itself
                mService.sendNotification(sender)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                if (response.body().success==1)
                                    Toast.makeText(ILostActivity.this, "Users Notified", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(ILostActivity.this,"Users Not Notified",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                //Log e ("ERROR",t.getMessage());

                            }
                        });*/



                Toast.makeText(ILostActivity.this, "Checking Internet Connection To Post", Toast.LENGTH_LONG).show();
                DatabaseReference newPost = databaseReference.push();
                newPost.child("name").setValue(yourname);
                newPost.child("email").setValue(youremail);
                newPost.child("phone").setValue(yourphone);
                newPost.child("description").setValue(yourdescription);
                newPost.child("time").setValue(Util_time.getTimestamp());
                newPost.child("gps").setValue(yourgps);


                new PushNotification().execute();


                Intent intent = new Intent(ILostActivity.this, HomeLostActivity.class);
                Toast.makeText(this, "Item Reported", Toast.LENGTH_LONG).show();
                startActivity(intent);


                editTextname.setText("");
                editTextemail.setText("");
                editTextphone.setText("");
                editTextdescription.setText("");





                finish();


            }
//
//        } else {
//           if  ( !isValidEmail(editTextemail.getText().toString())){
//        Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
//            }
//
//            if (isValidPhoneNumber(editTextphone.getText().toString())){
//          Toast.makeText(this,"Enter valid Phone Number",Toast.LENGTH_LONG).show();
//            }
//            Toast.makeText(this,"Field cannot be left empty",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ILostActivity.this,HomeLostActivity.class);
            startActivity(intent);
        }







    }



    public void cameraUpload(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
       startActivityForResult(intent,GALLARY_INTENT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == GALLARY_INTENT && resultCode == RESULT_OK){

            uri = data.getData();
            imageView.setImageURI(uri);



        }

    }
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isValidName(String name) {
        String ePattern ="^[A-Za-z - A-Za-z]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean isValidPhone(String phone) {
        String ePattern ="^[0-9]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }


    public void gps_location(View view) {

        //String uri = "http://maps.google.com/maps?q=loc:%f,%f", latitude,longitude);
       // String uri = String.format(Locale.ENGLISH,  "http://maps.google.com/maps?q=loc:%f,%f", 28.43242324,77.8977673);
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



    public class PushNotification extends AsyncTask<Void,Void,Void > {
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");

                HttpURLConnection conn =  (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=AIzaSyDgdzjmis2Sv3YGfOPpWuQ_CsUKyrEidMU");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();


                json.put("to", "/topics/" + "your_topic");






                JSONObject info = new JSONObject();
                info.put("title", "Item Reported");
                info.put("body", "Kindly Check The Item Reported As Lost To Help Someone");
                json.put("notification",info);






                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();
            } catch (Exception e)
            {
                Log.d("Error", "" + e + ":" + e.getMessage());
            }
            return null;
        }
    }







}

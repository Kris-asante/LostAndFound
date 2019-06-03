package com.example.krisperezcyrus.lostfound;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;


import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


public class IFoundActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 3;
    //for GPS
    Button fetch;
    TextView user_location;
    private FusedLocationProviderClient fusedLocationClient;
    Double latitude;
    Double longitude;


    private EditText editTextname,editTextemail,editTextphone,editTextdescription;

    private FirebaseDatabase database ;
    private DatabaseReference databaseReference;




    //camera
    Uri uri, imageUri;
    byte[] image;
    private Button cameraBtn;
    private ImageView imageview;
    private static final int CAMERA_REQUEST_CODE = 4;
    private StorageReference storageReference;
//end


    private TextView mCount;


    /*private StorageReference storageReference;
      private ImageView imageView;
      Uri uri;

    private Button cameracapturebtn;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifound);


        //for gps
        user_location =  findViewById(R.id.user_locations);
        fetch =  findViewById(R.id.fetch_locations);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchLocation();



            }
        });



        editTextname =  findViewById(R.id.name);
        editTextemail =  findViewById(R.id.email);
        editTextphone =  findViewById(R.id.phone);
        editTextdescription = findViewById(R.id.descriptionTextView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Found");
        // database.getInstance() was the initial  thing
//
        //storageReference = FirebaseStorage.getInstance().getReference();
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);


        mCount = findViewById(R.id.title_counter);



        //camera
        storageReference = FirebaseStorage.getInstance().getReference();
        cameraBtn = (Button) findViewById(R.id.camerabutton);
        imageview =  findViewById(R.id.Cameraimageview);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        });
        //end

       /* String image = getIntent().getStringExtra("image_uri");
        imageUri = Uri.parse(image);
        imageview.setImageURI(imageUri);*/




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



    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public void ifoundbuttonClicked(View view) {

        boolean cancel = false;
        View focusView = null;

        final String yourname = editTextname.getText().toString().trim();
        final String youremail = editTextemail.getText().toString().trim();
        final String yourphone = editTextphone.getText().toString().trim();
        final String yourdescription = editTextdescription.getText().toString().trim();
        final String yourgps = user_location.getText().toString().trim();


/*
        if (isValidPhoneNumber(editTextphone.getText().toString()) &&
         isValidEmail(editTextemail.getText().toString()) &&
         !TextUtils.isEmpty(yourname) &&
          !TextUtils.isEmpty(youremail) &&
          !TextUtils.isEmpty(yourphone) &&
          !TextUtils.isEmpty(yourdescription)){

        }*/


        //else {

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


        if (TextUtils.isEmpty(yourphone)) {
            editTextphone.setError("This field is required");
            focusView = editTextphone;
            cancel = true;
        }

        if (!isValidPhone(editTextphone.getText().toString())) {
            editTextphone.setError("Enter Valid Phone Number");
            focusView = editTextphone;
            cancel = true;
        }

        if (editTextphone.getText().length() <10){
            editTextphone.setError("Digits Of Number Not Up To 10");
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
/*
            Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Enter valid Phone Number",Toast.LENGTH_LONG).show();}
            Toast.makeText(this,"Field cannot be left empty",Toast.LENGTH_LONG).show();*/
        // }

        if (cancel){
            focusView.requestFocus();
        } else {


            // for camera

                //Uri uri = Uri.parse("android.resource://com.example.krisperezcyrus.lostfound/drawable/noimage");
                // this is for upload of image to the cloud


//                StorageReference filepath = storageReference.child("FoundImageUpload").child(uri.getLastPathSegment());
//                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                       final Uri downloadurl = taskSnapshot.getDownloadUrl();


            if (image != null) {

                final StorageReference filepath = storageReference.child("FoundUpload").child(image.toString());
                filepath.putBytes(image)
//                                this was changed and converted from the "." downwards
//                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        final Uri downloadurl = uri;
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
                            Log.d("IMAGE PREVIEW", "onComplete: Url: ");



                            Toast.makeText(IFoundActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                            DatabaseReference newPost = databaseReference.push();
                            newPost.child("name").setValue(yourname);
                            newPost.child("email").setValue(youremail);
                            newPost.child("phone").setValue(yourphone);
                            newPost.child("description").setValue(yourdescription);
                            newPost.child("time").setValue(Util_time.getTimestamp());
                            newPost.child("image").setValue(downloadurl);
                            newPost.child("gps").setValue(yourgps);

                            //NOT QUIET SURE
                            newPost.child("time").setValue(Util_time.getTimestamp());
                            new PushNotification().execute();

                            finish();

                        }
                    }
                });







                // }// like the above, removed but
                // }); // like the above, this bracket is supposed to be removed
            }
            else {
                //end camera


                // this is for upload of image to the cloud
                //StorageReference filepath = mStorageReference.child("FoundImageUpload").child(uri.getLastPathSegment());
                // filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                //@Override
                // public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //  final Uri downloadurl = taskSnapshot.getDownloadUrl();
                Toast.makeText(IFoundActivity.this, "Checking Internet Connection To Post", Toast.LENGTH_LONG).show();
                DatabaseReference newPost = databaseReference.push();
                newPost.child("name").setValue(yourname);
                newPost.child("email").setValue(youremail);
                newPost.child("phone").setValue(yourphone);
                newPost.child("description").setValue(yourdescription);
                newPost.child("time").setValue(Util_time.getTimestamp());
                newPost.child("gps").setValue(yourgps);
                // newPost.child("image").setValue(downloadurl.toString());

                new PushNotification().execute();

                // }


                // });

                Intent intent = new Intent(IFoundActivity.this, HomeFoundActivity.class);
                Toast.makeText(this, "Item Reported", Toast.LENGTH_LONG).show();
                startActivity(intent);

                finish();
            }

            Intent intent = new Intent(IFoundActivity.this,HomeFoundActivity.class);
            startActivity(intent);
        }

    }

//    public void camerabtnclicked(View view) {
//
//        Button mUploadBtn;
//         mUploadBtn = (Button) findViewById(R.id.camerabutton);
//         mUploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent camera = new Intent(IFoundActivity.this, CameraActivity.class);
//                startActivity(camera);
//            }
//
//        });
//
//
//    }

    //the method to get the picture and upload
    // that's cameraUpload and onActivityResult on Ilostactivity

   /* public void camerabtnclicked(View view) {


        cameracapturebtn = (Button) findViewById(R.id.camerabutton);
        cameracapturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(IFoundActivity.this, Camera2Activity.class);
                startActivities(camera);
            }
        });

    }

    private void startActivities(Intent camera) {
    }*/

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){

            /*uri = data.getData();
            imageview.setImageURI(uri);*/


            Bitmap photo = (Bitmap)data.getExtras()
                    .get("data");

            // Set the image in imageview for display
            imageview.setImageBitmap(photo);
            //uri = getImageUri(getApplicationContext(), photo);
            image = getImageUri(photo);


//           StorageReference filepath = storageReference.child("FoundImageUpload").child(uri.getLastPathSegment());
//
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    final Uri downloadurl = taskSnapshot.getDownloadUrl();
//                    Picasso.with(IFoundActivity.this).load(downloadurl).fit().centerCrop().into(imageview);
//
//
//                }
//            });
        }
    }


    public byte[] getImageUri( Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        byte[] data = bytes.toByteArray();
        return data;
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



    private void fetchLocation() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(IFoundActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(IFoundActivity.this,
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

                                ActivityCompat.requestPermissions(IFoundActivity.this,
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
                ActivityCompat.requestPermissions(IFoundActivity.this,
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
                info.put("body", "Kindly Check The Item Reported As Found If It Is Yours");
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


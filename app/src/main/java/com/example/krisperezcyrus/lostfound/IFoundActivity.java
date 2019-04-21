package com.example.krisperezcyrus.lostfound;


import android.content.Intent;

import android.net.Uri;


import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class IFoundActivity extends AppCompatActivity {


    private EditText editTextname,editTextemail,editTextphone,editTextdescription;

    private FirebaseDatabase database ;
    private DatabaseReference databaseReference;



//camera
    Uri uri;
    private Button cameraBtn;
    private ImageView imageview;
    private static final int CAMERA_REQUEST_CODE = 3;
    private StorageReference storageReference;
//end


    /*private StorageReference storageReference;
      private ImageView imageView;
      Uri uri;

    private Button cameracapturebtn;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifound);

        editTextname =  findViewById(R.id.name);
        editTextemail =  findViewById(R.id.email);
        editTextphone =  findViewById(R.id.phone);
        editTextdescription = findViewById(R.id.descriptionTextView);

        databaseReference = database.getInstance().getReference().child("Found");
//
        //storageReference = FirebaseStorage.getInstance().getReference();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);



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
        if (TextUtils.isEmpty(youremail)) {
            editTextemail.setError("This field is required");
            focusView = editTextemail;
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
            if (uri != null) {
                //Uri uri = Uri.parse("android.resource://com.example.krisperezcyrus.lostfound/drawable/noimage");
                // this is for upload of image to the cloud
                StorageReference filepath = storageReference.child("FoundImageUpload").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Uri downloadurl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(IFoundActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                        DatabaseReference newPost = databaseReference.push();
                        newPost.child("name").setValue(yourname);
                        newPost.child("email").setValue(youremail);
                        newPost.child("phone").setValue(yourphone);
                        newPost.child("description").setValue(yourdescription);
                        newPost.child("time").setValue(Util_time.getTimestamp());
                        newPost.child("image").setValue(downloadurl.toString());

                        //NOT QUIET SURE
                        newPost.child("time").setValue(Util_time.getTimestamp());
                    }
                });
            } else {
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
                // newPost.child("image").setValue(downloadurl.toString());

                // }


                // });

                Intent intent = new Intent(IFoundActivity.this, HomeFoundActivity.class);
                Toast.makeText(this, "Item Reported", Toast.LENGTH_LONG).show();
                startActivity(intent);


                editTextname.setText("");
                editTextemail.setText("");
                editTextphone.setText("");
                editTextdescription.setText("");
            }
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
        String ePattern ="^[A-Za-z- -A-Za-z]+$";
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

            uri = data.getData();
            imageview.setImageURI(uri);


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



}


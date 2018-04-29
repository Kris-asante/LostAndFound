package com.example.krisperezcyrus.lostfound;


import android.content.Intent;

import android.net.Uri;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




public class IFoundActivity extends AppCompatActivity {


    private EditText editTextname,editTextemail,editTextphone,editTextdescription;

    private FirebaseDatabase database ;
    private DatabaseReference databaseReference;

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

       // imageView = findViewById(R.id.Cameraimageview);


        databaseReference = database.getInstance().getReference().child("Found");


        //storageReference = FirebaseStorage.getInstance().getReference();


    }



    public void ifoundbuttonClicked(View view) {


        final String yourname = editTextname.getText().toString().trim();
        final String youremail = editTextemail.getText().toString().trim();
        final String yourphone = editTextphone.getText().toString().trim();
        final String yourdescription = editTextdescription.getText().toString().trim();



        if (!TextUtils.isEmpty(yourname) && !TextUtils.isEmpty(youremail) && !TextUtils.isEmpty(yourphone) && !TextUtils.isEmpty(yourdescription)){

            // this is for upload of image to the cloud
           // StorageReference filepath = storageReference.child("FoundImageUpload").child(uri.getLastPathSegment());
           // filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                //@Override
               // public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  //  final Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(IFoundActivity.this,"Image Upload Successful",Toast.LENGTH_LONG).show();
                    DatabaseReference newPost = databaseReference.push();
                    newPost.child("name").setValue(yourname);
                    newPost.child("email").setValue(youremail);
                    newPost.child("phone").setValue(yourphone);
                    newPost.child("description").setValue(yourdescription);
                   // newPost.child("image").setValue(downloadurl.toString());

               // }
           // });
        }




        Intent intent = new Intent(IFoundActivity.this,HomeFoundActivity.class);
        Toast.makeText(this,"Item Reported",Toast.LENGTH_LONG).show();
        startActivity(intent);




    }

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

}


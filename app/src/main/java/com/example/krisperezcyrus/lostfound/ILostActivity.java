package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ILostActivity extends AppCompatActivity  {


    private EditText editTextname,editTextemail,editTextphone,editTextdescription;

    private FirebaseDatabase database ;
    private DatabaseReference databaseReference;

    private StorageReference storageReference;
    private static final int GALLARY_INTENT = 5;
    private ImageView imageView;
    Uri uri;
    //private ArrayList<String> mDetails = new ArrayList<>();

    //ListView listView;



    //Button Reportbtn;
    //Button Camerabtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilost);

       editTextname =  findViewById(R.id.name);
       editTextemail =  findViewById(R.id.email);
       editTextphone =  findViewById(R.id.phone);
       editTextdescription = findViewById(R.id.descriptionTextView);

        imageView = findViewById(R.id.Cameraimageview);


        databaseReference = database.getInstance().getReference().child("Lost");


        storageReference = FirebaseStorage.getInstance().getReference();

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
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    public void ilostbuttonClicked(View view){


        final String yourname = editTextname.getText().toString().trim();
        final String youremail = editTextemail.getText().toString().trim();
        final String yourphone = editTextphone.getText().toString().trim();
        final String yourdescription = editTextdescription.getText().toString().trim();


       // databaseReference =database.getReference("Lost").child(child);


        //databaseReference.child("Name").push().setValue(editTextname.getText().toString());
       // databaseReference.child("Email").push().setValue(editTextemail.getText().toString());
        //databaseReference.child("Phone").push().setValue(editTextphone.getText().toString());
       // databaseReference.child("Description").push().setValue(editTextdescription.getText().toString());



        if (isValidPhoneNumber(editTextphone.getText().toString()) && isValidEmail(editTextemail.getText().toString()) && !TextUtils.isEmpty(yourname) && !TextUtils.isEmpty(youremail) && !TextUtils.isEmpty(yourphone) && !TextUtils.isEmpty(yourdescription )){





            if(uri != null) {
                //Uri uri = Uri.parse("android.resource://com.example.krisperezcyrus.lostfound/drawable/noimage");
                // this is for upload of image to the cloud
                StorageReference filepath = storageReference.child("ImageUpload").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Uri downloadurl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(ILostActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                        DatabaseReference newPost = databaseReference.push();
                        newPost.child("name").setValue(yourname);
                        newPost.child("email").setValue(youremail);
                        newPost.child("phone").setValue(yourphone);
                        newPost.child("description").setValue(yourdescription);
                        newPost.child("image").setValue(downloadurl.toString());



                        


                    }
                });

                Intent intent = new Intent(ILostActivity.this, HomeLostActivity.class);
                Toast.makeText(this, "Item Reported", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }else{
                Toast.makeText(ILostActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                DatabaseReference newPost = databaseReference.push();
                newPost.child("name").setValue(yourname);
                newPost.child("email").setValue(youremail);
                newPost.child("phone").setValue(yourphone);
                newPost.child("description").setValue(yourdescription);

                Intent intent = new Intent(ILostActivity.this, HomeLostActivity.class);
                Toast.makeText(this, "Item Reported", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }

        } else {
        Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
          Toast.makeText(this,"Enter valid Phone Number",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Field cannot be left empty",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ILostActivity.this,ILostActivity.class);
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
}

package com.example.krisperezcyrus.lostfound;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class ContactUsActivity extends AppCompatActivity {


    private EditText editTextcontactus;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);



        editTextcontactus =  findViewById(R.id.contact_us);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");
    }




    public void feedbackbuttonClicked(View view) {

        boolean cancel = false;
        View focusView = null;

        final String yourfeedbacktxt = editTextcontactus.getText().toString().trim();


        if (TextUtils.isEmpty(yourfeedbacktxt)) {
            editTextcontactus.setError("This field is required");
            focusView = editTextcontactus;
            cancel = true;
        }


        if (cancel){
            focusView.requestFocus();
        } else {

            Toast.makeText(ContactUsActivity.this, "Thanks For The Feedback", Toast.LENGTH_LONG).show();
            DatabaseReference newPost = databaseReference.push();
            newPost.child("Feedback").setValue(yourfeedbacktxt);

            editTextcontactus.setText("");


            finish();

        }

}
}

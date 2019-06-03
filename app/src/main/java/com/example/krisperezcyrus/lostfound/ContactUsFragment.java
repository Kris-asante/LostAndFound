package com.example.krisperezcyrus.lostfound;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ContactUsFragment  extends Fragment {


    private EditText editTextcontactus;
    private EditText editTextcontactusemail;
    private DatabaseReference databaseReference;


    public ContactUsFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((Ankasa)getActivity()).setActionBarTitle("Contact Us");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editTextcontactus =  view.findViewById(R.id.contact_us);
        editTextcontactus =  view.findViewById(R.id.contact_us_email);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");

        view.findViewById(R.id.contactusbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                boolean cancel = false;
                View focusView = null;

                final String yourfeedbacktxt = editTextcontactus.getText().toString().trim();
                final String yourfeedbacktxtemail = editTextcontactusemail.getText().toString().trim();


                if (TextUtils.isEmpty(yourfeedbacktxt)) {
                    editTextcontactus.setError("This field is required");
                    focusView = editTextcontactus;
                    cancel = true;
                }
                if (TextUtils.isEmpty(yourfeedbacktxtemail)) {
                    editTextcontactusemail.setError("This field is required");
                    focusView = editTextcontactusemail;
                    cancel = true;
                }


                if (cancel){
                    focusView.requestFocus();
                } else {

                    Toast.makeText(getActivity(), "Thanks For The Feedback", Toast.LENGTH_LONG).show();
                    DatabaseReference newPost = databaseReference.push();
                    newPost.child("Feedback").setValue(yourfeedbacktxt);

                    editTextcontactus.setText("");
                    editTextcontactusemail.setText("");


                    getActivity().finish();

                }


            }
        });




    }

}

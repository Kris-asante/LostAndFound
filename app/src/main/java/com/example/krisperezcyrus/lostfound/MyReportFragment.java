package com.example.krisperezcyrus.lostfound;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class MyReportFragment  extends Fragment {

    private static ProgressDialog pd;

    static Thread mThread;

    LinearLayoutManager mLayoutManager;


    private RecyclerView FoundItemsList;
    private DatabaseReference mDatabase;

    public static final String TAG = HomeFoundActivity.class.getSimpleName();


    public MyReportFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((Ankasa)getActivity()).setActionBarTitle(" My Report");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_report, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*view.findViewById(R.id.deleteicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();

            }
        });*/


                FoundItemsList = view.findViewById(R.id.foundItemsList);
        FoundItemsList.setHasFixedSize(true);
        FoundItemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Found");

        pd = new ProgressDialog(getContext());
        pd.setTitle("Please Wait...Loading");
        pd.setCancelable(true);
        //pd.show();


        //how the display of the reported items are arranged in the newest above/top
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);



        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        //RecyclerView

        FoundItemsList.setHasFixedSize(true);

        //set layout as LinearLayout
        FoundItemsList.setLayoutManager(mLayoutManager);


    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<FoundPost, FounditemViewHolder> fbra = new FirebaseRecyclerAdapter<FoundPost, FounditemViewHolder>(

                FoundPost.class,
                R.layout.myreportlayout,
                FounditemViewHolder.class,
                mDatabase

        ) {

            // @Override
            protected void populateViewHolder(FounditemViewHolder viewHolder, FoundPost model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setTime(model.getTime());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(getActivity().getApplication(),model.getImage());

                viewHolder.delete();

            }
        };


        FoundItemsList.setAdapter(fbra);

    }






    public static class FounditemViewHolder extends RecyclerView.ViewHolder {

        public FounditemViewHolder(View itemView) {
            super(itemView);
            View mView = itemView;

            //deleteBtn = mView.findViewById(R.id.deleteicon);

        }

        public void delete(){
            ImageButton delete_btn = itemView.findViewById(R.id.deleteicon);
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    pd.show();
                    mThread = new Thread(){
                        @Override
                        public void run(){
                            //pd.show();
                            try{
                                synchronized (this){
                                    wait(10000);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            pd.dismiss();
                            Snackbar.make(itemView.findViewById(R.id.deleteicon), "Some went wrong. Failed to delete", Snackbar.LENGTH_LONG).show();

                        }
                    };
                    //pd.show();

                }
            });
        }

        public void setName(String name) {

            TextView postName = itemView.findViewById(R.id.namepost);
            postName.setText(name);
        }


        public void setEmail(String email) {

            TextView postemail = itemView.findViewById(R.id.emailpost);
            postemail.setText(email);
        }

        public void setPhone(String phone) {

            TextView postphone = itemView.findViewById(R.id.phonepost);
            postphone.setText(phone);
        }


        public void setTime(String time) {

            TextView postName = itemView.findViewById(R.id.time);
            postName.setText(time);
        }

        public void setImage(Context ctx, String image) {

            ImageView postimage = itemView.findViewById(R.id.foundimagespreview);
            Picasso.with(ctx).load(image).into(postimage);
            //Picasso.get().load(image).into(postimage);
        }

        public void setDescription(String description) {

            TextView postdescription = itemView.findViewById(R.id.descriptionpost);
            postdescription.setText(description);
        }


    }


//    public void dialNumber(View view) {
//        TextView textView = view.findViewById(R.id.phonepost);
//        // Use format with "tel:" and phone number to create mPhoneNum.
//
//        String phone = String.format("tel: %s",textView.getText().toString());
//
//        // Create the intent.
//        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
//        // Set the data for the intent as the phone number.
//        dialIntent.setData(Uri.parse(phone));
//        // If package resolves to an app, send intent.
//        if (dialIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivity(dialIntent);
//            Toast.makeText(getActivity(),"Opening Dialer",Toast.LENGTH_LONG).show();
//        } else {
//            Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
//            Toast.makeText(getActivity(),"Can't Make Call",Toast.LENGTH_LONG).show();
//        }
//
//
//    }
//
//
    public void delete (View view) {


        }
//
//
//    }
//
//    public void send_Email (View view){
//        TextView emailToEmail = view.findViewById(R.id.emailpost);
//        String recipientList = emailToEmail.getText().toString();
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setData(Uri.parse("mailto:"));
//        String[] recipients = recipientList.split(",");
//        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
//        intent.putExtra(Intent.EXTRA_TEXT, "Hello There, you reported a found item on LOST&FOUND App which I did misplace.\n" +"Let's kindly meet to prove my ownership.\n" +"My Contact :");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "You Have Found My Lost Item on LOST&FOUND App");
//        intent.setType("message/rfc822");
//        Intent mailer = Intent.createChooser(intent, "Send Email");
//        startActivity(mailer);
//        Toast.makeText(getActivity()," Select Email Client",Toast.LENGTH_LONG).show();
//    }




}

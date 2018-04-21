package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.makeText;
import static com.example.krisperezcyrus.lostfound.R.id.homelostbtn;
import static com.example.krisperezcyrus.lostfound.R.id.startButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    Button homeLostButton;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((Ankasa) getActivity()).setActionBarTitle("Home");

        //homeLostButton =getActivity().findViewById(homelostbtn);

        //homeLostButton.setOnClickListener(this);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.homelostbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),HomeLostActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Lost Items", Toast.LENGTH_LONG).show();
            }
        });



        view.findViewById(R.id.homefoundbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),HomeFoundActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Found Items", Toast.LENGTH_LONG).show();
            }
        });









        //public void onClick(View view) {
       // switch (view.getId()) {
            //case homelostbtn:

               // Intent intent = new Intent(getActivity(), HomeLostActivity.class);
               // startActivity(intent);
              //  Toast.makeText(getActivity(), "Lost Items", Toast.LENGTH_LONG).show();
               // break;

        //}
    }

    @Override
    public void onClick(View view) {

    }
}

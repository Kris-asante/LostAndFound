package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //implements View.OnClickListener{


    private static int SPLASH_TIME_OUT =1000;

    // this is for slider menu at home
    private ViewPager home_screenPager;
    Home_IntroViewPageAdapter home_introViewPageAdapter;
    TabLayout home_tabIndicator ;
    int position = 0;

//end


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

                Intent i = new Intent(getActivity(), HomeLostActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Loading Lost Items", Toast.LENGTH_LONG).show();
            }
        });


        view.findViewById(R.id.homefoundbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), HomeFoundActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Loading Found Items", Toast.LENGTH_LONG).show();
            }
        });


        //for home slider
        //dotted view
        home_tabIndicator = view.findViewById(R.id.tab_indicators);

//
        //fill list screen
        final List<Home_ScreenItem> mList = new ArrayList<>();

        mList.add(new Home_ScreenItem("Keep the IMEI of your device safe.\nDial *#06# on mobile device to check."));
        mList.add(new Home_ScreenItem("Take pictures of your precious and valuable items and save on your phone"));
        mList.add(new Home_ScreenItem("Before you report an item as lost or found, kindly check if someone has reported it already and contact the person."));
        mList.add(new Home_ScreenItem("Kindly be connected to the internet throughout for ease of usage."));

        //setup viewpager

        home_screenPager = view.findViewById(R.id.screen_viewpagers);
        home_introViewPageAdapter = new Home_IntroViewPageAdapter (getContext(),mList);
        home_screenPager.setAdapter(home_introViewPageAdapter);

        //tablayout with viewpager
        home_tabIndicator.setupWithViewPager(home_screenPager);



       // btnNext.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {

                position = home_screenPager.getCurrentItem();
                if (position< mList.size()){
                    position++;
                    home_screenPager.setCurrentItem(position);
                }
                if (position == mList.size()-1){

                    loadLastScreen();

                }


        //tablayout add change listner
        home_tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition()== mList.size()-1){
                    loadLastScreen();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }




    private void loadLastScreen() {
        home_tabIndicator.setVisibility(View.VISIBLE);

    }

}

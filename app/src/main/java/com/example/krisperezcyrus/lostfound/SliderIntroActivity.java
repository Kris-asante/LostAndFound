package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SliderIntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPageAdapter introViewPageAdapter;
    TabLayout tabIndicator ;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_intro);


//        //making the activity a full screen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//       setContentView(R.layout.activity_slider_intro);
//
//       //hide the action bar
//        getSupportActionBar().hide();

        //before this activity lunches we need to check if it has been opened before
        if (restorePrefData()){

            Intent signinactivity = new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(signinactivity);
            finish();
        }


        //dotted view
        tabIndicator = findViewById(R.id.tab_indicator);

        btnNext = findViewById(R.id.btn_Next);

        btnGetStarted = findViewById(R.id.btn_get_started);

        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        //fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("IMEI","Keep the IMEI of all your devices safe \nDial *#06# on mobile device to check ",R.drawable.imeicircle));
        mList.add(new ScreenItem("OWNERSHIP","Report stolen items with IMEI to help prevent people from buying it on selling platforms and to prove your ownership",R.drawable.ownercircle));
        mList.add(new ScreenItem("INTERNET","Internet connectivity is required in almost all activies \nKindly be connected to the internet throughout for ease of usage ",R.drawable.internetcircle));

        //setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPageAdapter = new IntroViewPageAdapter(this,mList);
        screenPager.setAdapter(introViewPageAdapter);


        //tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               position = screenPager.getCurrentItem();
                if (position< mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size()-1){

                   //TODO: show the GETSTARTED Button and hide indicator and next button
                    loadLastScreen();

                }

            }
        });

        //tablayout add change listner
      tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        //Get Started button click
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open sign in activity
                Intent signinactivity = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(signinactivity);
                //code to save shared preference that already opened the app before
                savePrefsData ();
                finish();
            }
        });

    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;

    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();

    }

    //show the GETSTARTED Button and hide the indicator and next button
    private void loadLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        //TODO : Add an animation to the getstarted button
        // let's create the button animation in resource anim
        //setup animation button
        btnGetStarted.setAnimation(btnAnim);




    }

}

package com.example.krisperezcyrus.lostfound;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;


public class Ankasa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private FirebaseAuth mAuth;

//public FrameLayout frameLayout;

//THE VARIABLES FOR THE BOTTOM NAVIGATION
//public BottomAccountFragment bottomAccountFragment;
//public BottomReportFragment bottomReportFragment;
//public BottomSearchFragment bottomSearchFragment;
//public BottomHomeFragment bottomHomeFragment;


//private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ankasa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//FRAMELAYOUT FOR THE THE FRAME VIEWS
       // frameLayout= (FrameLayout) findViewById(R.id.nav_frame);

//
////BOTTOM NAV SWITCH STATEMETNS

//        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
//        {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        //mTextMessage.setText("Home");
//                        setFragment(bottomHomeFragment);
//                        return true;
//                    case R.id.navigation_myaccount:
//                        //mTextMessage.setText("My Account");
//                        setFragment(bottomAccountFragment);
//                        return true;
//                    case R.id.navigation_ReportItem:
//                        //mTextMessage.setText("Report Item");
//                        setFragment(bottomReportFragment);
//                        return true;
//                    case R.id.navigation_search:
//                        //mTextMessage.setText("Search");
//                        setFragment(bottomSearchFragment);
//                        return true;
//                    case R.id.navigation_more:
//                        //mTextMessage.setText("More");
//                        return true;
//                }
//                return false;
//            }
//        };





//
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//
//        bottomHomeFragment = new BottomHomeFragment();
//        bottomReportFragment = new BottomReportFragment();
//        bottomAccountFragment = new BottomAccountFragment();
//        bottomReportFragment = new BottomReportFragment();
//        bottomSearchFragment = new BottomSearchFragment();
//

      /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //this set the home as default fragment page
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain,new ReportItemFragment());
        ft.commit();

        navigationView.setCheckedItem(R.id.navigation_ReportItem);

        mAuth = FirebaseAuth.getInstance();

    }
// on pc trial for bottom but error of the getSupport
//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//
//
//                    switch (item.getGroupId()){
//
//                        case R.id.navigation_home:
//                            selectedFragment = new BottomHomeFragment();
//                            break;
//
//                        case R.id.navigation_myaccount:
//                            selectedFragment = new BottomAccountFragment();
//                            break;
//
//                        case R.id.navigation_ReportItem:
//                            selectedFragment = new BottomReportFragment();
//                            break;
//
//                        case R.id.navigation_search:
//                            selectedFragment = new BottomSearchFragment();
//                            break;
//                    }
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_frame,selectedFragment).commit();
//                    return false;
//                }
//            };

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_frame,fragment);
        fragmentTransaction.commit();

    }

    public void setActionBarTitle (String title){
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ankasa, menu);




        //searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_us)
        //will change this to about us
            {
                startActivity(new Intent(Ankasa.this,AboutUsActivity.class));

            return false;

       // }else if (id == R.id.action_search){

            //startActivity(new Intent(Ankasa.this,SearchActivity.class));




        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new HomeFragment());
            ft.commit();

        }   else if (id == R.id.navigation_ReportItem) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new ReportItemFragment());
            ft.commit();


        }   else if (id == R.id.navigation_myaccount) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new MyAccountFragment());
            ft.commit();

        }   else if (id == R.id.nav_faq) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new FAQFragment());
            ft.commit();

        }  
        else if (id == R.id.nav_logout) {

            mAuth.signOut();
            Intent intent = new Intent(Ankasa.this,SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }



        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

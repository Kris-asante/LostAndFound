package com.example.krisperezcyrus.lostfound;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Home_IntroViewPageAdapter extends PagerAdapter {

    Context mhomecontext;
    List<Home_ScreenItem> mhomeListScreen;

    public Home_IntroViewPageAdapter(Context mhomecontext, List<Home_ScreenItem> mhomeListScreen) {
        this.mhomecontext = mhomecontext;
        this.mhomeListScreen = mhomeListScreen;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mhomecontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.home_layout_screen,null);



        TextView description = layoutScreen.findViewById(R.id.intro_descriptions);


        description.setText(mhomeListScreen.get(position).getDescription());


        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount()  {
        return mhomeListScreen.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View)object);
    }
}

package com.example.krisperezcyrus.lostfound;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TellFriendFragment extends Fragment {


    public TellFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((Ankasa)getActivity()).setActionBarTitle("Tell A Friend");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tell_friend, container, false);
    }

}

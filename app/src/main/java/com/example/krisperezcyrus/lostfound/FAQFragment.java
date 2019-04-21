package com.example.krisperezcyrus.lostfound;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;


    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((Ankasa)getActivity()).setActionBarTitle("FAQ");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    listView = (ExpandableListView) view.findViewById(R.id.lvExp);
    initData();
    listAdapter = new ExpandableListAdapter(getContext(),listDataHeader,listHash);
    listView.setAdapter(listAdapter);



    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("How Do I Report An Item");
        listDataHeader.add("How Can I Retrieve My Lost Item");
        listDataHeader.add("How Do I Locate My Finder");
        listDataHeader.add("Why Do I Have To Sign In With Google Account");
        listDataHeader.add("Do I Have To Award My Finder");



        List<String> AAA =new ArrayList<>();
        AAA.add("Reports can be done as a lost or found. Report with I FOUND if you have come across" +
                "a missing or found item. Report with I LOST if you have misplaced or lost an item" +
                "of yours");


        List<String> BBB =new ArrayList<>();
        BBB.add("This can be possible if someone has come across your item and reported it as a found item" +
                "Use the contact details provided to get in touch with the reporter" +
                "To increase the chance of finding an item, report your missing item with I LOST report");



        List<String> CCC =new ArrayList<>();
        CCC.add("Kindly use the contact details to get in touch with your finder. This can be done either through" +
                "a phone call, an SMS or an email");




        List<String> DDD =new ArrayList<>();
        DDD.add("Google SignIn was integrated to reduce the number of different acoounts you have and having to set" +
                "password for all accounts. This makes it simplier and easier to remember your account details");

        List<String> EEE =new ArrayList<>();
        EEE.add("I would be just nice to show appreciation to someone who helped you locate your lost item." +
                "To engourage such people to continue with this act of kindness, it is pleasant to do so");


        listHash.put(listDataHeader.get(0),AAA);
        listHash.put(listDataHeader.get(1),BBB);
        listHash.put(listDataHeader.get(2),CCC);
        listHash.put(listDataHeader.get(3),DDD);
        listHash.put(listDataHeader.get(4),EEE);

    }


}

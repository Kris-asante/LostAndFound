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
    private Faq_ExpandableListAdapter listAdapter;
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
    listAdapter = new Faq_ExpandableListAdapter(getContext(),listDataHeader,listHash);
    listView.setAdapter(listAdapter);



    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("What Is Lost&Found? What Do I Do Here"); //F0
        listDataHeader.add("Which Devices Can I Use");//F1
        listDataHeader.add("How Do I Report An Item");//A2
        listDataHeader.add("There's Illegal Content On Lost&Found.How Do I Take It Down"); //H3
        listDataHeader.add("How Can I Retrieve My Lost Item");//B4
        listDataHeader.add("How Do I Locate My Finder");//C5
        listDataHeader.add("Why Do I Have To Sign In With Google Account");//D6
        listDataHeader.add("Do I Have To Award My Finder");//E7
        listDataHeader.add("How Best Can I search For An Item");//I8



        List<String> FFF =new ArrayList<>();
        FFF.add("Lost&Found is a reporting app with a focus on helping people retrieve their lost items quickly and with ease." +
                " You can use Lost&Found on all android devices and reported items are sync seamlessly across the platform. ");

        List<String> GGG =new ArrayList<>();
        GGG.add("You can use Lost&Found on smartphones, tablets and even chromebooks running android operating system." +
                " Android 4.1 and above are able to enjoy the animation features on the application.");

        List<String> HHH =new ArrayList<>();
        HHH.add("All Lost&Found data are hosted online on a database server. We do not process any requests related to them." +
                " However to make the app void of illegal images and comments, kindly ping us on the CONTACT US to report such abuse.");


        List<String> AAA =new ArrayList<>();
        AAA.add("Reports can be done as either a lost or found item. Report with I FOUND if you have come across" +
                " a missing or found item. Report with I LOST if you have misplaced or lost an item" +
                "of yours.");


        List<String> BBB =new ArrayList<>();
        BBB.add("This can be possible if someone has come across your item and reported it as a found item." +
                " Use the contact details provided to get in touch with the reporter." +
                " To increase the chance of finding an item, report your missing item with I LOST report.");



        List<String> CCC =new ArrayList<>();
        CCC.add("Kindly use the contact details to get in touch with your finder. This can be done either through" +
                "a phone call, an SMS or an e-mail.");




        List<String> DDD =new ArrayList<>();
        DDD.add("Google SignIn was integrated to reduce the number of different accounts you have and having to set" +
                " password for all accounts. This makes it simpler and easier to remember your account details.");

        List<String> EEE =new ArrayList<>();
        EEE.add("I would be just nice to show appreciation to someone who helped you locate your lost item." +
                " To encourage such people to continue with this act of kindness, it is cool to do so :).");

        List<String> III =new ArrayList<>();
        III.add("Search is based on keywords. Therefore search with key words in description");

        listHash.put(listDataHeader.get(0),FFF);
        listHash.put(listDataHeader.get(1),GGG);
        listHash.put(listDataHeader.get(2),AAA);
        listHash.put(listDataHeader.get(3),HHH);
        listHash.put(listDataHeader.get(4),BBB);
        listHash.put(listDataHeader.get(5),CCC);
        listHash.put(listDataHeader.get(6),DDD);
        listHash.put(listDataHeader.get(7),EEE);
        listHash.put(listDataHeader.get(8),III);

    }


}

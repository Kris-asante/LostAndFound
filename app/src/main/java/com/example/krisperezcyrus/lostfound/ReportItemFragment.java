package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportItemFragment extends Fragment {


    public ReportItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((Ankasa)getActivity()).setActionBarTitle("Report Item");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.lostItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),ILostActivity.class);
                startActivity(i);
            }
        });

        view.findViewById(R.id.foundItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),IFoundActivity.class);
                startActivity(i);

            }
        });
    }
}

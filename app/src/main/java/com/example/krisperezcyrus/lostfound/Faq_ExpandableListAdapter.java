package com.example.krisperezcyrus.lostfound;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class Faq_ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHolder;
    private HashMap<String,List<String>> listHashMap;


    public Faq_ExpandableListAdapter(Context context, List<String> listDataHolder, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHolder = listDataHolder;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHolder.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHolder.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHolder.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHolder.get(i)).get(i1); // i = group item i1=child item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.faq_list_group,null);
        }
        TextView lb1ListHeader = (TextView) view.findViewById(R.id.lb1ListHeader);
        lb1ListHeader.setTypeface (null, Typeface.BOLD);
        lb1ListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String)getChild(i,i1);
        if (view == null)
        {

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.faq_list_item,null);
        }
        TextView txtListChild = (TextView)view.findViewById(R.id.lb1ListItem);
        txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

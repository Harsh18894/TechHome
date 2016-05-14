package com.techHome.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.techHome.R;
import com.techHome.adapters.ExpandableListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFAQFragment extends android.support.v4.app.Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public NavigationDrawerFAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer_faq, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the listview
        expListView = (ExpandableListView) getActivity().findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("1. How can I assure quality of service?");
        listDataHeader.add("2. What are the charges?");
        listDataHeader.add("3. How to book for a service?");
        listDataHeader.add("4. Do you provide service only on damage?");
        listDataHeader.add("5. Do I need to buy spare parts which will be used?");

        // Adding child data
        List<String> faq1 = new ArrayList<String>();
        faq1.add("We provide best in class service in the market, still our workers are humans only and they are bind to make mistakes. we care about your hard earned money so we provide 7 days repairing Warranty after the date of service. you can claim it through our android app.");

        List<String> faq2 = new ArrayList<String>();
        faq2.add("We have competitive lowest price in the market, so if you are in trouble and low on budget, don't panic, just give us a call and sit back and relax. We will take care of it for you because we exist because you are. Booking a service on our android app is easier.");

        List<String> faq3 = new ArrayList<String>();
        faq3.add("Whenever you face any problem in any of your household equipment that we cover. Go to our website or android app and register your complain. We will provide you a serviceman on your doorsteps at the earliest.");

        List<String> faq4 = new ArrayList<String>();
        faq4.add("We provide repairments on damage as well as complete overhauling and servicing of equipments when they are in working condition so that they are safe from any possible damage and have a longer life. Just go on our website and log a complaint to book a service. Doing it through our android app is easier.");

        List<String> faq5 = new ArrayList<String>();
        faq5.add("No. Our serviceman will check the problem and buy the parts only after consulting you. he will bring with him most of the tools required for servicing. However we expect you to provide him basic things such as cleaning cloth and ladder.");

        listDataChild.put(listDataHeader.get(0), faq1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), faq2);
        listDataChild.put(listDataHeader.get(2), faq3);
        listDataChild.put(listDataHeader.get(3), faq4);
        listDataChild.put(listDataHeader.get(4), faq5);
    }
}

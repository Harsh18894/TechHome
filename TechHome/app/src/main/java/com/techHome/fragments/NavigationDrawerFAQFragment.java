package com.techHome.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techHome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFAQFragment extends android.support.v4.app.Fragment {


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


}

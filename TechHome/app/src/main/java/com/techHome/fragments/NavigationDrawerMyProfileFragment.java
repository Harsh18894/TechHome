package com.techHome.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techHome.R;
import com.techHome.adapters.ProfileRecyclerAdapter;
import com.techHome.constants.ProfileRecyclerInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerMyProfileFragment extends android.support.v4.app.Fragment {


    @Bind(R.id.profileRecyclerView)
    RecyclerView profileRecyclerView;
    private ProfileRecyclerAdapter adapter;
    private View parentView;

    public NavigationDrawerMyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_navigation_drawer_my_profile, container, false);
        ButterKnife.bind(this, parentView);
        populate();
        return parentView;
    }

    private void populate() {
        adapter = new ProfileRecyclerAdapter(getActivity(), getData());
        profileRecyclerView.setAdapter(adapter);
        profileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static List<ProfileRecyclerInformation> getData() {
        List<ProfileRecyclerInformation> data = new ArrayList<>();
        String[] attributes = {"Name", "Phone No.", "Email", "Address", "City", "PIN"};
        String[] values = {"", "", "", "", "", ""};

        for (int i = 0; i < attributes.length && i < values.length; i++) {
            ProfileRecyclerInformation current = new ProfileRecyclerInformation();
            current.attribute = attributes[i];
            current.value = values[i];
            data.add(current);
        }
        return data;
    }

}

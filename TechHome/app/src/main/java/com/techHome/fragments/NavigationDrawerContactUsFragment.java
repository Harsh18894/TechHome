package com.techHome.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techHome.R;
import com.techHome.adapters.ContactRecyclerAdapter;
import com.techHome.constants.ContactRecyclerInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerContactUsFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.contactUsRecyclerView)
    RecyclerView contactRecyclerView;
    private ContactRecyclerAdapter adapter;
    private View parentView;



    public NavigationDrawerContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_navigation_drawer_contact_us, container, false);
        ButterKnife.bind(this, parentView);
        populate();
        return parentView;
    }

    private void populate() {
        adapter = new ContactRecyclerAdapter(getActivity(), getData());
        contactRecyclerView.setAdapter(adapter);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static List<ContactRecyclerInformation> getData() {
        List<ContactRecyclerInformation> data = new ArrayList<>();
        String[] Name = {"Siddhartha Tyagi", "Prateek Kapil", "Shivam Tyagi", "Mohan Bansal"};
        String[] Address = {"Founder", "Co-Founder", "Co-Founder", "Co-Founder"};
        int[] images = {R.mipmap.siddhartha_tyagi, R.mipmap.prateek_kapil, R.mipmap.shivam_tyagi, R.mipmap.mohan_banal};

        for (int i = 0; i < Name.length && i < Address.length; i++) {
            ContactRecyclerInformation current = new ContactRecyclerInformation();
            current.Name = Name[i];
            current.Address = Address[i];
            current.imgPhoto = images[i];
            data.add(current);
        }
        return data;
    }
}

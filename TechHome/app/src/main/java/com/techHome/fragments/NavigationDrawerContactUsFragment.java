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

    private final int[] names = {R.string.siddhartha, R.string.mohan, R.string.prateek, R.string.shivam};
    private final int[] pos = {R.string.sid_pos, R.string.mohan_pos, R.string.prateek_pos, R.string.shivam_pos};
    private final String[] images = {"http://techhome.esy.es/static/siddhartha_tyagi.jpg", "http://techhome.esy.es/static/mohan_bansal.jpg", "http://techhome.esy.es/static/prateek_kapil.jpg", "http://techhome.esy.es/static/shivam_tyagi.jpg"};



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

    public List<ContactRecyclerInformation> getData() {
        List<ContactRecyclerInformation> data = new ArrayList<>();

        for (int i = 0; i < names.length && i < pos.length; i++) {
            ContactRecyclerInformation contactRecyclerInformation = new ContactRecyclerInformation();
            contactRecyclerInformation.setName(names[i]);
            contactRecyclerInformation.setPos(pos[i]);
            contactRecyclerInformation.setImgPhoto(images[i]);
            data.add(contactRecyclerInformation);
        }
        return data;
    }
}

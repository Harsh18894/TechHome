package com.techHome.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techHome.R;
import com.techHome.adapters.AboutUsRecyclerAdapter;
import com.techHome.constants.AboutUsRecyclerInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerAboutUsFragment extends android.support.v4.app.Fragment {


    @Bind(R.id.aboutUsRecyclerView)
    RecyclerView aboutUsRecyclerView;
    private AboutUsRecyclerAdapter adapter;

    private View parentView;

    public NavigationDrawerAboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        parentView = inflater.inflate(R.layout.fragment_navigation_drawer_about_us, container, false);
        ButterKnife.bind(this, parentView);
        populate();
        return parentView;
    }

    private void populate() {
        adapter = new AboutUsRecyclerAdapter(getActivity(), getData());
        aboutUsRecyclerView.setAdapter(adapter);
        aboutUsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public static List<AboutUsRecyclerInformation> getData() {
        List<AboutUsRecyclerInformation> data = new ArrayList<>();
        String[] attributes = {"What are we?", "Why TechHome?", "Which services do we provide?", "Address"};
        String[] values = {"We, at TechHome, provide servicing needs for all the household daily use equipment right at your door step. Currently we provide the repair and install of Electrical appliances and Plumbing services only. Our highly efficient workmanship is dedicated towards providing services in the promised time. We understand the worth of your hard earned money and costly appliances, so we never compromise with quality. TechHome will provide 7 days after service guarantee too.", "Among all the other service providers present in the market, TechHome, with its highly skilled and experienced workforce distinctively stands out. Our dealers are certified and selected workers are tested on various measures before they are finally recruited. We provide services at competitive lower rates than others with best in class quality and high precision.", "Currently we provide the repair for the electrical and plumbing equipment. For now, we are mainly focusing on these categories. As a rapidly growing firm, we are planning to launch our services in other categories very soon."};

        for (int i = 0; i < attributes.length && i < values.length; i++) {
            AboutUsRecyclerInformation current = new AboutUsRecyclerInformation();
            current.ques = attributes[i];
            current.ans = values[i];
            data.add(current);
        }
        return data;
    }


}

package com.techHome.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techHome.R;
import com.techHome.adapters.DashboardRecyclerAdapter;
import com.techHome.constants.DashboardRecyclerInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

//Dashboard Fragment

public class DashboardFragment extends android.support.v4.app.Fragment {


    @Bind(R.id.dashboardRecyclerView)
    RecyclerView dashboardRecyclerView;
    /*@Bind(R.id.gridViewIcons)
    GridView gridView;*/
    private DashboardRecyclerAdapter adapter;
    private View parentView;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, parentView);
        populate();
        return parentView;
    }

    //populating the dashboard fragment with grid icons

    private void populate() {

        adapter = new DashboardRecyclerAdapter(getActivity(), getData());
        dashboardRecyclerView.setAdapter(adapter);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        /*GridAdapter adapter = new GridAdapter(getActivity(), options);
        gridView.setAdapter(adapter);*/

        //setting up on click listener in gridview
        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (options[position]) {
                    case "Appliances":
                        startActivity(new Intent(getActivity(), AppliancesActivity.class));
                        break;
                    *//*case "Electrical":
                        startActivity(new Intent(getActivity(), ElectricalActivity.class));
                        break;*//*
                    case "Wiring":
                        startActivity(new Intent(getActivity(), WiringActivity.class));
                        break;
                    case "Plumbing":
                        startActivity(new Intent(getActivity(), PlumbingActivity.class));
                        break;
                }
            }

        });*/
    }

    public static List<DashboardRecyclerInformation> getData() {
        List<DashboardRecyclerInformation> data = new ArrayList<>();
        int[] icons = {R.mipmap.appliances, R.mipmap.wiring, R.mipmap.plumbing};
        String[] titles = {"Appliances", "Wiring", "Plumbing"};
        String[] descs =  {"Let us take care of your Appliances", "Get your Wiring fixed", "Don't let those pipes leak"};

        for (int i = 0; i < titles.length && i < icons.length; i++) {
            DashboardRecyclerInformation current = new DashboardRecyclerInformation();
            current.iconId = icons[i];
            current.title = titles[i];
            current.desc = descs[i];
            data.add(current);
        }
        return data;
    }
}

package com.techHome.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private final int[] titles = {R.string.appliances_dashboard, R.string.wiring_dashboard ,R.string.plumbing_dashboard};
    private final int[] descs = {R.string.appliances_desc, R.string.wiring_desc, R.string.plumbing_desc};
    private final String[] iconId = {"http://techhome.net16.net/static/appliances.png", "http://techhome.net16.net/static/wiring.png", "http://techhome.net16.net/static/plumbing.png"};

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

    }

    public List<DashboardRecyclerInformation> getData() {
        List<DashboardRecyclerInformation> data = new ArrayList<>();


        for (int i = 0; i < titles.length && i < iconId.length; i++) {
         DashboardRecyclerInformation dashboardRecyclerInformation = new DashboardRecyclerInformation();
            dashboardRecyclerInformation.setTitle(titles[i]);
            dashboardRecyclerInformation.setDesc(descs[i]);
            dashboardRecyclerInformation.setIconId(iconId[i]);
            data.add(dashboardRecyclerInformation);
        }
        return data;
    }
}

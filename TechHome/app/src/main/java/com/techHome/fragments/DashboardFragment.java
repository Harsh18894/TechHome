package com.techHome.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.techHome.R;
import com.techHome.activities.AppliancesActivity;
import com.techHome.activities.ElectricalActivity;
import com.techHome.activities.PlumbingActivity;
import com.techHome.activities.WiringActivity;
import com.techHome.adapters.GridAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

//Dashboard Fragment

public class DashboardFragment extends android.support.v4.app.Fragment {


    @Bind(R.id.gridViewIcons)
    GridView gridView;
    private View parentView;
    String[] options = {"Appliances", "Electrical", "Wiring", "Plumbing"};

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

    //populating the dashboard fraagment with grid icons

    private void populate() {

        GridAdapter adapter = new GridAdapter(getActivity(), options);
        gridView.setAdapter(adapter);

        //setting up on click listener in gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (options[position]) {
                    case "Appliances":
                        startActivity(new Intent(getActivity(), AppliancesActivity.class));
                        break;
                    case "Electrical":
                        startActivity(new Intent(getActivity(), ElectricalActivity.class));
                        break;
                    case "Wiring":
                        startActivity(new Intent(getActivity(), WiringActivity.class));
                        break;
                    case "Plumbing":
                        startActivity(new Intent(getActivity(), PlumbingActivity.class));
                        break;
                }
            }

        });
    }
}

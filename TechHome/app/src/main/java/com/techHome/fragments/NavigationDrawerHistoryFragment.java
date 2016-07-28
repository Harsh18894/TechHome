package com.techHome.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techHome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerHistoryFragment extends android.support.v4.app.Fragment {


    private View parentView;
    private SharedPreferences preferences;
    @Bind(R.id.txtValue)
    TextView txtValue;
    @Bind(R.id.txtValue1)
    TextView txtValue1;
    @Bind(R.id.txtValue2)
    TextView txtValue2;
    @Bind(R.id.txtValue3)
    TextView txtValue3;
    @Bind(R.id.txtValue4)
    TextView txtValue4;
    @Bind(R.id.txtValue5)
    TextView txtValue5;

    public NavigationDrawerHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_navigation_drawer_history, container, false);
        populate();
        return parentView;
    }

    private void populate() {
        ButterKnife.bind(this, parentView);
        preferences = this.getActivity().getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);

        txtValue.setText(preferences.getString("order_type", null));
        txtValue1.setText(preferences.getString("order_description", null));
        txtValue2.setText(preferences.getString("service_city", null));
        txtValue3.setText(preferences.getString("order_date", null));
        txtValue4.setText(preferences.getString("service_slot", null));
        txtValue5.setText(preferences.getString("order_mobile", null));

    }


}

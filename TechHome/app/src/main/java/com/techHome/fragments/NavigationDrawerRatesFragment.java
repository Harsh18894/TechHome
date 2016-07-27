package com.techHome.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techHome.R;
import com.techHome.adapters.RateCardRecyclerAdapter;
import com.techHome.constants.RatesRecyclerInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerRatesFragment extends android.support.v4.app.Fragment {


    private View view;
    @Bind(R.id.rateRecyclerView)
    RecyclerView rateRecyclerView;
    RateCardRecyclerAdapter adapter;

    public NavigationDrawerRatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_navigation_drawer_rates, container, false);
        ButterKnife.bind(this, view);
        populate();
        return view;
    }

    private void populate() {
        adapter = new RateCardRecyclerAdapter(getActivity(), getData());
        rateRecyclerView.setAdapter(adapter);
        rateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public List<RatesRecyclerInformation> getData(){
        List<RatesRecyclerInformation> data = new ArrayList<>();
        String[] cities = {"Meerut", "Modinagar", "Muradnagar" ,"Ghaziabad", "Saharanpur"};
        String[] prices = {"Rs. 149", "Rs. 99", "Rs. 99", "Rs. 149", "Rs. 199"};
        String[] condition = {"Visit charges - 7:30am to 7:00pm", "Visit charges - 7:30am to 7:00pm", "Visit charges - 7:30am to 7:00pm", "Visit charges - 7:30am to 7:00pm", "Visit charges - 7:30am to 7:00pm"};
        String[] info = {"Rest according to the work done", "Rest according to the work done", "Rest according to the work done", "Rest according to the work done", "Rest according to the work done"};

        for (int i = 0; i < cities.length; i++){
            RatesRecyclerInformation ratesRecyclerInformation = new RatesRecyclerInformation();
            ratesRecyclerInformation.city = cities[i];
            ratesRecyclerInformation.charges = prices[i];
            ratesRecyclerInformation.condition = condition[i];
            ratesRecyclerInformation.information = info[i];
            data.add(ratesRecyclerInformation);
        }
        return data;
    }

}

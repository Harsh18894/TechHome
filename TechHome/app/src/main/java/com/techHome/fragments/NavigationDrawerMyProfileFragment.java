package com.techHome.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.techHome.R;
import com.techHome.activities.DashboardActivity;
import com.techHome.adapters.ProfileRecyclerAdapter;
import com.techHome.constants.NetworkConstants;
import com.techHome.constants.ProfileRecyclerInformation;
import com.techHome.global.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerMyProfileFragment extends android.support.v4.app.Fragment {


    @Bind(R.id.profileRecyclerView)
    RecyclerView profileRecyclerView;
    private ProfileRecyclerAdapter adapter;
    private View parentView;
    private SharedPreferences preferences, sharedPreferences;
    private SweetAlertDialog dialog;

    public NavigationDrawerMyProfileFragment() {
        // Required empty public constructor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_navigation_drawer_my_profile, container, false);
        ButterKnife.bind(this, parentView);
        preferences = this.getActivity().getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
        sharedPreferences = this.getActivity().getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        String mobile = preferences.getString("mobile", null);
        getUser(mobile);
        populate();
        return parentView;
    }

    private void populate() {
        adapter = new ProfileRecyclerAdapter(getActivity(), getData());
        profileRecyclerView.setAdapter(adapter);
        profileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void getUser(final String mobile) {

        String tag_string_user = "get_user";

        dialog.setTitleText("Just A Moment..");
        dialog.setTitle("Please Wait...");
        dialog.setCancelable(false);
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkConstants.GET_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(DashboardActivity.class.getSimpleName(), "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("user");
                    sharedPreferences.edit().putString("name", jsonObject1.getString("name")).commit();
                    sharedPreferences.edit().putString("email", jsonObject1.getString("email")).commit();
                    sharedPreferences.edit().putString("mobile", jsonObject1.getString("mobile")).commit();
                    sharedPreferences.edit().putString("address", jsonObject1.getString("address")).commit();
                    sharedPreferences.edit().putString("city", jsonObject1.getString("city")).commit();
                    sharedPreferences.edit().putString("pincode", jsonObject1.getString("pincode")).commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(DashboardActivity.class.getSimpleName(), "Profile Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);
                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_user);
    }

    public List<ProfileRecyclerInformation> getData() {
        List<ProfileRecyclerInformation> data = new ArrayList<>();

        String[] attributes = {"Name", "Phone No.", "Email", "Address", "City", "PIN"};
        String[] values = {sharedPreferences.getString("name", null), sharedPreferences.getString("mobile", null), sharedPreferences.getString("email", null),
                sharedPreferences.getString("address", null), sharedPreferences.getString("city", null), sharedPreferences.getString("pincode", null)};

        for (int i = 0; i < attributes.length; i++) {
            ProfileRecyclerInformation current = new ProfileRecyclerInformation();
            current.attribute = attributes[i];
            current.value = values[i];
            data.add(current);
        }
        return data;
    }

    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}

package com.techHome.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.techHome.R;
import com.techHome.dto.MessageCustomDialogDTO;
import com.techHome.fragments.DashboardFragment;
import com.techHome.fragments.NavigationDrawerAboutUsFragment;
import com.techHome.fragments.NavigationDrawerContactUsFragment;
import com.techHome.fragments.NavigationDrawerFAQFragment;
import com.techHome.fragments.NavigationDrawerHistoryFragment;
import com.techHome.fragments.NavigationDrawerMyProfileFragment;
import com.techHome.fragments.NavigationDrawerRatesFragment;
import com.techHome.ui.SnackBar;
import com.techHome.util.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 4/13/2016.
 */

//Main Activity of the application

public class DashboardActivity extends AppCompatActivity {


    //Declaring and Initialising the variables using Butterknife
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.navigation_view)
    NavigationView navigation_view;
    private MenuItem previousMenuItem;
    private SessionManager sessionManager;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        preferences = getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
        populate();
    }

    //populating the variables
    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        sessionManager = new SessionManager(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            if (bundle.containsKey("done") && bundle.getString("done").equals("done")) {
                MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                messageCustomDialogDTO.setTitle(getResources().getString(R.string.success));
                messageCustomDialogDTO.setMessage(getResources().getString(R.string.order_success));
                messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                messageCustomDialogDTO.setContext(DashboardActivity.this);
                SnackBar.success(DashboardActivity.this, messageCustomDialogDTO);
            }
        }

        Bundle bundle1 = getIntent().getExtras();
        if (null != bundle1) {
            if (bundle1.containsKey("login") && bundle1.get("login").equals("login")) {
                MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                messageCustomDialogDTO.setTitle(getResources().getString(R.string.login));
                messageCustomDialogDTO.setMessage(getResources().getString(R.string.login_success));
                messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                messageCustomDialogDTO.setContext(DashboardActivity.this);
                SnackBar.success(DashboardActivity.this, messageCustomDialogDTO);
            }
        }

        Bundle bundle2 = getIntent().getExtras();
        if (null != bundle1) {
            if (bundle2.containsKey("register") && bundle2.get("register").equals("register")) {
                MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                messageCustomDialogDTO.setTitle(getResources().getString(R.string.registration));
                messageCustomDialogDTO.setMessage(getResources().getString(R.string.registration_success));
                messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                messageCustomDialogDTO.setContext(DashboardActivity.this);
                SnackBar.success(DashboardActivity.this, messageCustomDialogDTO);
            }
        }

        navigation_view.inflateMenu(R.menu.menu_dashboard_drawer);
        //setting up the navigation drawer
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (previousMenuItem != null)
                    previousMenuItem.setChecked(false);

                item.setCheckable(true);
                item.setChecked(true);

                previousMenuItem = item;

                drawer_layout.closeDrawers();

                //opening the fragments upon click of every item in navigtion drawer
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.dashboardHome:
                        DashboardFragment dashboardFragment = new DashboardFragment();
                        fragmentTransaction.replace(R.id.frame, dashboardFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                        return true;
                    case R.id.myProfile:
                        NavigationDrawerMyProfileFragment navigationDrawerMyProfileFragment = new NavigationDrawerMyProfileFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerMyProfileFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.my_profile);
                        return true;
                    case R.id.history:
                        NavigationDrawerHistoryFragment navigationDrawerHistoryFragment = new NavigationDrawerHistoryFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerHistoryFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.history);
                        return true;
                /*    case R.id.offers:
                        NavigationDrawerOffersFragment navigationDrawerOffersFragment = new NavigationDrawerOffersFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerOffersFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.app_offers);
                        return true;*/
                    case R.id.rate:
                        NavigationDrawerRatesFragment navigationDrawerRatesFragment = new NavigationDrawerRatesFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerRatesFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.rate_card);
                        return true;
/*
                    case R.id.settings:
                        NavigationDrawerSettingsFragment navigationDrawerSettingsFragment = new NavigationDrawerSettingsFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerSettingsFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.settings);
                        return true;
*/
                    case R.id.about_us:
                        NavigationDrawerAboutUsFragment navigationDrawerAboutUsFragment = new NavigationDrawerAboutUsFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerAboutUsFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.about_us);
                        return true;
                    case R.id.contact_us:
                        NavigationDrawerContactUsFragment navigationDrawerContactUsFragment = new NavigationDrawerContactUsFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerContactUsFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.contact_us);
                        return true;
                    case R.id.faqs:
                        NavigationDrawerFAQFragment navigationDrawerFAQFragment = new NavigationDrawerFAQFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerFAQFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.faqs);
                        return true;
                    case R.id.logout:
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(DashboardActivity.this);
                        final MaterialDialog dialog = builder.build();
                        builder.title(R.string.logout).content(R.string.logout_message).positiveText(R.string.logout).negativeText(R.string.cancel).typeface("roboto_bold.ttf", "roboto_light.ttf");
                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction which) {
                                dialog.dismiss();
                                try {
                                    logout();
                                } catch (Exception e) {
                                    SnackBar.show(DashboardActivity.this, e.toString());
                                    e.printStackTrace();
                                }
                            }
                        });
                        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        return true;

                    default:
                        return true;
                }

            }
        });

        //inflating the dashboard fragment
        DashboardFragment fragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();


        //syncing the drawer toggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer_layout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = LayoutInflater.from(this).inflate(R.layout.dashboard_drawer_header, null);
        android.widget.TextView txtUser = (android.widget.TextView) convertView.findViewById(R.id.txtPhone);
        android.widget.TextView txtName = (android.widget.TextView) convertView.findViewById(R.id.txtName);
        txtName.setText(preferences.getString("name", null));
        txtUser.setText(preferences.getString("mobile", null));
        navigation_view.addHeaderView(convertView);
    }

    private void logout() {
        sessionManager.setLogin(false);
        preferences.edit().clear().commit();
        Intent intent = new Intent(DashboardActivity.this, EnterModeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("logout", "Logout");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_notifications:
                Intent intent = new Intent(this, NotificationsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //setting up back pressed so that it always navigates to dashboard fragment
    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (f instanceof DashboardFragment)
            super.onBackPressed();
        else {
            Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

    }
}


package com.techHome.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;
import com.techHome.fragments.DashboardFragment;
import com.techHome.fragments.NavigationDrawerFAQFragment;
import com.techHome.fragments.NavigationDrawerHistoryFragment;
import com.techHome.fragments.NavigationDrawerMyProfileFragment;
import com.techHome.fragments.NavigationDrawerOffersFragment;
import com.techHome.fragments.NavigationDrawerRatesFragment;
import com.techHome.fragments.NavigationDrawerSettingsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dell on 4/13/2016.
 */
public class DashboardActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    /*@InjectView(R.id.gridViewIcons)
    GridView gridView;*/
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.navigation_view)
    NavigationView navigation_view;
    private MenuItem previousMenuItem;
    /*@InjectView(R.id.btnAppliance)
    Button btnAppliance;
    @InjectView(R.id.btnElectrical)
    Button btnElectrical;
    @InjectView(R.id.btnWiring)
    Button btnWirirng;
*/
    /*String[] options = {"Appliances", "Electrical", "Wiring", "Plumbing"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

       /* SpannableString s = new SpannableString("TechHome");
        ((TextView) toolbar.findViewById(R.id.toolbarTitle)).setText(s);
        getSupportActionBar().setTitle("");*/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            if (bundle.containsKey("done") && bundle.getString("done").equals("done")) {
                Snackbar snackbar = Snackbar.make(drawer_layout, "Your Order has been Placed.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

        /*GridAdapter adapter = new GridAdapter(DashboardActivity.this, options);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (options[position]) {
                    case "Appliances":
                        startActivity(new Intent(DashboardActivity.this, AppliancesActivity.class));
                        break;
                    case "Electrical":
                        startActivity(new Intent(DashboardActivity.this, ElectricalActivity.class));
                        break;
                    case "Wiring":
                        startActivity(new Intent(DashboardActivity.this, WiringActivity.class));
                        break;
                    case "Plumbing":
                        startActivity(new Intent(DashboardActivity.this, PlumbingActivity.class));
                        break;
                }*/
/*
                Toast.makeText(DashboardActivity.this, "You facing problem with " + options[+position], Toast.LENGTH_SHORT).show();
*/
    /*}*/
        /*});*/

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (previousMenuItem != null)
                    previousMenuItem.setChecked(false);

                item.setCheckable(true);
                item.setChecked(true);

                previousMenuItem = item;

                drawer_layout.closeDrawers();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
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
                    case R.id.offers:
                        NavigationDrawerOffersFragment navigationDrawerOffersFragment = new NavigationDrawerOffersFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerOffersFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.app_offers);
                        return true;
                    case R.id.rate:
                        NavigationDrawerRatesFragment navigationDrawerRatesFragment = new NavigationDrawerRatesFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerRatesFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.rate_card);
                        return true;
                    case R.id.settings:
                        NavigationDrawerSettingsFragment navigationDrawerSettingsFragment = new NavigationDrawerSettingsFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerSettingsFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.settings);
                        return true;
                    case R.id.faqs:
                        NavigationDrawerFAQFragment navigationDrawerFAQFragment = new NavigationDrawerFAQFragment();
                        fragmentTransaction.replace(R.id.frame, navigationDrawerFAQFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.faqs);
                        return true;
                    case R.id.logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                        builder.setTitle("LOG OUT");
                        TextView textView = new TextView(DashboardActivity.this);
                        textView.setText("Are you sure you want to logout?");
                        textView.setGravity(Gravity.CENTER);
                        textView.setPadding(15, 50, 15, 15);
                        textView.setTextSize(16);
                        builder.setView(textView);

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(DashboardActivity.this, EnterModeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("NO", null);
                        builder.show();

                    default:
                        /*Toast.makeText(getApplicationContext(), "Something's Wrong.", Toast.LENGTH_SHORT).show();*/
                        return true;
                }
                /*item.setChecked(false)
                Toast.makeText(DashboardActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();*/
            }
        });

        DashboardFragment fragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();


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
  /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnAppliance.setBackgroundResource(R.drawable.ripple_appliance);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnElectrical.setBackgroundResource(R.drawable.ripple_electrical);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnWirirng.setBackgroundResource(R.drawable.ripple_wiring);
        }*/
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


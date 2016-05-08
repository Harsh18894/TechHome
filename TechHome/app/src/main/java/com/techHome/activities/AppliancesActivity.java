package com.techHome.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Dell on 4/16/2016.
 */

//Appliances grid item setup

public class AppliancesActivity extends AppCompatActivity {

/*
Declaring and intialising the variables..
*/

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spinnerAppliances)
    MaterialSpinner materialSpinner;
    private String[] appliances;
    @Bind(R.id.btnNext)
    Button btnNext;
    private DrawerLayout drawerLayout;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        populate();
    }

    //populating the variables used and defining their values
    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.appliances_activity);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        appliances = getApplication().getResources().getStringArray(R.array.appliance_issue);

        //setting up ripple effect in the buttons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnNext.setBackgroundResource(R.drawable.ripple);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppliancesActivity.this, android.R.layout.simple_spinner_item, appliances);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(adapter);

        //button to navigate to main page confirming the choice
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      final AlertDialog.Builder builder = new AlertDialog.Builder(AppliancesActivity.this);
                builder.setTitle("Confirmation");
                TextView textView = new TextView(AppliancesActivity.this);
                textView.setText("Click OK to confirm your order.");
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(15, 50, 15, 15);
                textView.setTextSize(18);
                builder.setView(textView);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AppliancesActivity.this, DashboardActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("done", "done");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();
*/

                startActivity(new Intent(AppliancesActivity.this, AppliancesActivityFinalSelection.class));
            }
        });
    }
}

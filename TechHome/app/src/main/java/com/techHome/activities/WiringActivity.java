package com.techHome.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.neopixl.pixlui.components.edittext.EditText;
import com.techHome.R;
import com.techHome.ui.SnackBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Harsh on 4/16/2016.
 */

//Wiring setup

public class WiringActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spinnerWiring)
    MaterialSpinner materialSpinner;
    private String[] wiring;
    @Bind(R.id.btnSubmit)
    Button btnNext;
    @Bind(R.id.etIssueDescription)
    EditText etIssueDescription;
    private DrawerLayout drawerLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiring);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.wiring);


        wiring = getApplication().getResources().getStringArray(R.array.wiring_issues);

        //setting up ripple effect in the buttons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnNext.setBackgroundResource(R.drawable.ripple_rounded);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(WiringActivity.this, android.R.layout.simple_spinner_item, wiring);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(adapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (materialSpinner.getSelectedItem().toString().equals("Wiring Options")) {
                    SnackBar.show(WiringActivity.this, "Please Select any option");
                    return;
                }

                if (etIssueDescription.getText().toString().equals("")) {
                    SnackBar.show(WiringActivity.this, "Please Fill Description");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("category", materialSpinner.getSelectedItem().toString());
                bundle.putString("desc", etIssueDescription.getText().toString());
                Intent intent = new Intent(WiringActivity.this, PlaceOrderActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(WiringActivity.this, DashboardActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
        finish();

    }
}

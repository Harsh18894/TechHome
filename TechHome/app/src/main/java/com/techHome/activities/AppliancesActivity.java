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

import com.neopixl.pixlui.components.edittext.EditText;
import com.techHome.R;
import com.techHome.dto.OrderDTO;

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
    @Bind(R.id.btnSubmit)
    Button btnNext;
    @Bind(R.id.etIssueDescription)
    EditText etIssueDescription;
    private String[] appliances;
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
            btnNext.setBackgroundResource(R.drawable.ripple_rounded);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppliancesActivity.this, android.R.layout.simple_spinner_item, appliances);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(adapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setCategory(materialSpinner.getSelectedItem().toString());
                orderDTO.setDescription(etIssueDescription.getText().toString());
                Intent intent = new Intent(AppliancesActivity.this, PlaceOrderActivity.class);
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

        Intent intent = new Intent(AppliancesActivity.this, DashboardActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
        finish();

    }

}

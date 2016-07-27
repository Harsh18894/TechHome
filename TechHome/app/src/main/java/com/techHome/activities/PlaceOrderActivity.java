package com.techHome.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.techHome.R;
import com.techHome.asynctasks.PlaceOrderAsyncTask;
import com.techHome.dto.OrderDTO;
import com.techHome.global.AppController;

import java.util.ArrayList;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Dell on 5/8/2016.
 */
public class PlaceOrderActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    MaterialSpinner spinnerCity, spinnerSlot;
    private String[] city;
    private String[] slot;
    AppCompatButton btnNext;
    Toolbar toolbar;
    Button btnDate;
    String orderDate;
    StringBuilder date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        populate();
    }

    @SuppressWarnings("unchecked")
    public void setDate(View view) {
        showDialog(999);
    }


    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };


    private void showDate(int year, int i, int day) {
        date = new StringBuilder().append("You Selected ").append(day).append("/")
                .append(month).append("/").append(year);

        orderDate = date.toString();

        dateView.setText(orderDate);
    }


    private void populate() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.select_date);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateView = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.txtDateshow);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnNext = (AppCompatButton) findViewById(R.id.btnSubmit);
        spinnerCity = (MaterialSpinner) findViewById(R.id.spinnerCity);
        spinnerSlot = (MaterialSpinner) findViewById(R.id.spinnerslot);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        city = getApplication().getResources().getStringArray(R.array.city_names);
        slot = getApplication().getResources().getStringArray(R.array.time_slots);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnDate.setBackgroundResource(R.drawable.ripple_rounded);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnNext.setBackgroundResource(R.drawable.ripple);
        }


        ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(PlaceOrderActivity.this, android.R.layout.simple_spinner_item, city);
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter_city);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(PlaceOrderActivity.this, android.R.layout.simple_spinner_item, slot);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSlot.setAdapter(adapter_time);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setCity(spinnerCity.getSelectedItem().toString());
                orderDTO.setSlot(spinnerSlot.getSelectedItem().toString());
                orderDTO.setDate(orderDate);

                if (null == ((AppController) getApplicationContext()).getOrderDTOs()) {
                    ((AppController) getApplicationContext()).setOrderDTOs(new ArrayList<OrderDTO>());
                    ((AppController) getApplicationContext()).getOrderDTOs().add(orderDTO);
                } else {
                    ((AppController) getApplicationContext()).getOrderDTOs().add(orderDTO);
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(PlaceOrderActivity.this);
                builder.setTitle("Confirmation");
                TextView textView = new TextView(PlaceOrderActivity.this);
                textView.setText("Click OK to confirm your order.");
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(15, 50, 15, 15);
                textView.setTextSize(18);
                builder.setView(textView);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PlaceOrderAsyncTask placeOrderAsyncTask = new PlaceOrderAsyncTask(PlaceOrderActivity.this, ((AppController)getApplicationContext()).getOrderDTOs());
                        placeOrderAsyncTask.execute();

                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();


            }
        });


    }

}

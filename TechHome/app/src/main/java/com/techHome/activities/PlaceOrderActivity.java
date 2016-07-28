package com.techHome.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.datetimepicker.date.DatePickerDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.techHome.R;
import com.techHome.constants.NetworkConstants;
import com.techHome.global.AppController;
import com.techHome.ui.SnackBar;
import com.techHome.util.NetworkCheck;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Harsh on 5/8/2016.
 */
public class PlaceOrderActivity extends AppCompatActivity implements com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.spinnerCity)
    MaterialSpinner spinnerCity;

    @Bind(R.id.spinnerSlot)
    MaterialSpinner spinnerSlot;

    @Bind(R.id.txtDate)
    TextView txtDate;

    @Bind(R.id.btnSubmit)
    Button btnSubmit;

    private String[] city;
    private String[] slot;
    private SweetAlertDialog dialog;
    public SharedPreferences preferences;


    @OnClick(R.id.txtDate)
    void date() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.btnSubmit)
    void place() {

        if (spinnerCity.getSelectedItem().toString().equals("Select City")) {
            SnackBar.show(this, "Please Select Your City");
            return;
        }

        if (spinnerSlot.getSelectedItem().toString().equals("Select Preferred Slot")) {
            SnackBar.show(this, "Please Select Time Slot");
            return;
        }

        if (!NetworkCheck.isNetworkAvailable(PlaceOrderActivity.this)) {
            SnackBar.show(this, "No Internet Connection");
            return;
        }

        Bundle bundle = getIntent().getExtras();
        final String category = bundle.getString("category");
        final String desc = bundle.getString("desc");

        new MaterialDialog.Builder(PlaceOrderActivity.this)
                .title("Confirm Your Order").inputType(InputType.TYPE_CLASS_PHONE)
                .input("Enter Your Mobile Number", null, new MaterialDialog.InputCallback() {

                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        String data = input.toString();
                        if (data.equals("")) {
                            SnackBar.show(PlaceOrderActivity.this, "Enter Something");
                        } else if (data.length() != 10) {
                            SnackBar.show(PlaceOrderActivity.this, "Enter Valid Mobile Number");
                        } else {
                            placeOrder(category, desc, spinnerSlot.getSelectedItem().toString(), spinnerCity.getSelectedItem().toString(), txtDate.getText().toString(), data);
                        }
                    }
                }).typeface("roboto_bold.ttf", "roboto_light.ttf").show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        preferences = getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.select_date);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnSubmit.setBackgroundResource(R.drawable.ripple);
        }

        city = getApplication().getResources().getStringArray(R.array.city_names);
        slot = getApplication().getResources().getStringArray(R.array.time_slots);

        ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(PlaceOrderActivity.this, android.R.layout.simple_spinner_item, city);
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter_city);

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(PlaceOrderActivity.this, android.R.layout.simple_spinner_item, slot);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSlot.setAdapter(adapter_time);

        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setCancelable(false);

    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        String dateString = dayOfMonth + " - " + (monthOfYear+1) + " - " + year;
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        Date now = new Date();
        now = getDateWithOutTime(now);

        Date date = gregorianCalendar.getTime();

        if (date.compareTo(now) == -1) {
            SnackBar.show(this, getResources().getString(R.string.invalid_date));
            return;
        }
        txtDate.setText(dateString);
    }


    private Date getDateWithOutTime(Date targetDate) {
        Calendar newDate = Calendar.getInstance();
        newDate.setLenient(false);
        newDate.setTime(targetDate);
        newDate.set(Calendar.HOUR_OF_DAY, 0);
        newDate.set(Calendar.MINUTE, 0);
        newDate.set(Calendar.SECOND, 0);
        newDate.set(Calendar.MILLISECOND, 0);

        return newDate.getTime();

    }


    private void placeOrder(final String category, final String description, final String slot, final String city, final String date, final String mobile) {
        String tag_string_req = "place_order";

        dialog.setTitle("Placing Order..");
        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        dialog.setTitleText("Please Wait....");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkConstants.PLACE_ORDER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(PlaceOrderActivity.class.getSimpleName(), "Place Order Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        // Launch main activity
                        JSONObject jsonObject = jObj.getJSONObject("order");
                        preferences.edit().putString("order_type", jsonObject.getString("order_type")).commit();
                        preferences.edit().putString("order_description", jsonObject.getString("description")).commit();
                        preferences.edit().putString("order_date", jsonObject.getString("date")).commit();
                        preferences.edit().putString("service_slot", jsonObject.getString("slot")).commit();
                        preferences.edit().putString("service_city", jsonObject.getString("city")).commit();
                        preferences.edit().putString("order_mobile", jsonObject.getString("mobile")).commit();
                        Intent intent = new Intent(PlaceOrderActivity.this, DashboardActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("done", "done");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        ActivityCompat.finishAffinity(PlaceOrderActivity.this);
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(PlaceOrderActivity.class.getSimpleName(), "Place Order Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_type", category);
                params.put("description", description);
                params.put("slot", slot);
                params.put("city", city);
                params.put("order_date", date);
                params.put("mobile", mobile);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);


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

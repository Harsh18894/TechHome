package com.techHome.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.neopixl.pixlui.components.edittext.EditText;
import com.techHome.R;
import com.techHome.constants.NetworkConstants;
import com.techHome.dto.MessageCustomDialogDTO;
import com.techHome.dto.RegisterDTO;
import com.techHome.global.AppController;
import com.techHome.ui.SnackBar;
import com.techHome.util.NetworkCheck;
import com.techHome.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Dell on 4/14/2016.
 */

//Registration of a user

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @Bind(R.id.etAddress)
    EditText etAddress;
    @Bind(R.id.etPinCode)
    EditText etPinCode;
    @Bind(R.id.etCity)
    EditText etCity;
    @Bind(R.id.etPswrd)
    EditText etPassword;
    @Bind(R.id.etCnfrmPswrd)
    EditText etConfirmPassword;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private SweetAlertDialog dialog;
    private SessionManager sessionManager;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferences = getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

     /*   SpannableString s = new SpannableString("Create New Account");
        ((TextView) toolbar.findViewById(R.id.toolbarTitle)).setText(s);
        getSupportActionBar().setTitle("");*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnRegister.setBackgroundResource(R.drawable.ripple);
        }

        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setCancelable(false);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etPhoneNumber.getText().toString().trim().equals("") || etAddress.getText().toString().trim().equals("") || etPassword.getText().toString().equals("") || etConfirmPassword.getText().toString().equals("")) {
                    if (NetworkCheck.isNetworkAvailable(RegisterActivity.this)) {
                        if (etPhoneNumber.getText().length() == 10) {
                            if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                                RegisterDTO registerDTO = new RegisterDTO();
                                registerDTO.setName(etName.getText().toString());
                                registerDTO.setEmail(etEmail.getText().toString());
                                registerDTO.setMobile(etPhoneNumber.getText().toString());
                                registerDTO.setAddress(etAddress.getText().toString());
                                registerDTO.setCity(etCity.getText().toString());
                                registerDTO.setPincode(etPinCode.getText().toString());
                                registerDTO.setPassword(etPassword.getText().toString());
                                registerUser(etName.getText().toString(), etEmail.getText().toString(), etPhoneNumber.getText().toString(), etAddress.getText().toString(),
                                        etCity.getText().toString(), etPinCode.getText().toString(), etPassword.getText().toString());
                            } else {
                                MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                                messageCustomDialogDTO.setTitle(getResources().getString(R.string.passwords_didnt_match_title));
                                messageCustomDialogDTO.setMessage(getResources().getString(R.string.passwords_didnt_match));
                                messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                                messageCustomDialogDTO.setContext(RegisterActivity.this);
                                SnackBar.show(RegisterActivity.this, messageCustomDialogDTO);
                            }
                        } else {
                            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                            messageCustomDialogDTO.setTitle(getResources().getString(R.string.login_activity_invalid_phone_title));
                            messageCustomDialogDTO.setMessage(getResources().getString(R.string.login_activity_invalid_phone));
                            messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                            messageCustomDialogDTO.setContext(RegisterActivity.this);
                            SnackBar.show(RegisterActivity.this, messageCustomDialogDTO);
                        }
                    } else {
                        MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                        messageCustomDialogDTO.setTitle(getResources().getString(R.string.no_internet_title));
                        messageCustomDialogDTO.setMessage(getResources().getString(R.string.no_internet));
                        messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                        messageCustomDialogDTO.setContext(RegisterActivity.this);
                        SnackBar.show(RegisterActivity.this, messageCustomDialogDTO);
                    }
                } else {
                    MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                    messageCustomDialogDTO.setTitle(getResources().getString(R.string.blank_fields_title));
                    messageCustomDialogDTO.setMessage(getResources().getString(R.string.blank_fields));
                    messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                    messageCustomDialogDTO.setContext(RegisterActivity.this);
                    SnackBar.show(RegisterActivity.this, messageCustomDialogDTO);
                }
            }
        });
    }


    private void registerUser(final String name, final String email,
                              final String mobile, final String address, final String city, final String pincode, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        dialog.setTitle("Registering ...");
        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        dialog.setTitleText("Registering...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, NetworkConstants.REGISTRATION_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch main activity
                        sessionManager.setLogin(true);
                        JSONObject jsonObject = jObj.getJSONObject("user");
                        preferences.edit().putString("name", jsonObject.getString("name")).commit();
                        preferences.edit().putString("mobile", jsonObject.getString("mobile")).commit();
                        Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        ActivityCompat.finishAffinity(RegisterActivity.this);
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("address", address);
                params.put("city", city);
                params.put("pincode", pincode);
                params.put("password", password);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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


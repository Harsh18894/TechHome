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
import com.techHome.dto.LoginDTO;
import com.techHome.dto.MessageCustomDialogDTO;
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

//Login page

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    /*@Bind(R.id.txtForgotPassword)
    TextView txtForgotPassword;*/
    private SessionManager sessionManager;
    private SweetAlertDialog dialog;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.login);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnLogin.setBackgroundResource(R.drawable.ripple);
        }

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setCancelable(false);

        //logging into the application and clearing the flags
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(LoginActivity.this)) {
                    if (etUsername.getText().toString().length() == 10) {
                        if (etPassword.getText().toString().length() != 0) {
                            LoginDTO loginDTO = new LoginDTO();
                            loginDTO.setMobile(etUsername.getText().toString().trim());
                            loginDTO.setPassword(etPassword.getText().toString().trim());
                            checkLogin(loginDTO.getMobile(), loginDTO.getPassword());
                        } else {
                            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                            messageCustomDialogDTO.setTitle(getResources().getString(R.string.login_activity_invalid_password_title));
                            messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                            messageCustomDialogDTO.setMessage(getResources().getString(R.string.login_activity_invalid_password));
                            messageCustomDialogDTO.setContext(LoginActivity.this);
                            SnackBar.show(LoginActivity.this, messageCustomDialogDTO);
                        }
                    } else {
                        MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                        messageCustomDialogDTO.setTitle(getResources().getString(R.string.login_activity_invalid_phone_title));
                        messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                        messageCustomDialogDTO.setMessage(getResources().getString(R.string.login_activity_invalid_phone));
                        messageCustomDialogDTO.setContext(LoginActivity.this);
                        SnackBar.show(LoginActivity.this, messageCustomDialogDTO);
                    }
                } else {
                    MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                    messageCustomDialogDTO.setTitle(getResources().getString(R.string.no_internet_title));
                    messageCustomDialogDTO.setButton(getResources().getString(R.string.ok));
                    messageCustomDialogDTO.setMessage(getResources().getString(R.string.no_internet));
                    messageCustomDialogDTO.setContext(LoginActivity.this);
                    SnackBar.show(LoginActivity.this, messageCustomDialogDTO);
                }
            }
        });


    }


    private void checkLogin(final String mobile, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        dialog.setTitleText("Logging in...");
        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        dialog.setTitle("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, NetworkConstants.LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in


                        // Create login session
                        sessionManager.setLogin(true);

                        // Launch main activity
                        JSONObject jsonObject = jObj.getJSONObject("user");
                        preferences.edit().putString("name", jsonObject.getString("name")).commit();
                        preferences.edit().putString("mobile", jsonObject.getString("mobile")).commit();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("login", "login");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        ActivityCompat.finishAffinity(LoginActivity.this);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);
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


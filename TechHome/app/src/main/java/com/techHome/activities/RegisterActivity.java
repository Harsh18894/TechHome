package com.techHome.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.neopixl.pixlui.components.edittext.EditText;
import com.techHome.R;
import com.techHome.asynctasks.HitJSPService;
import com.techHome.asynctasks.TaskCompleted;
import com.techHome.dto.MessageCustomDialogDTO;
import com.techHome.ui.SnackBar;
import com.techHome.util.NetworkCheck;

import butterknife.Bind;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etPhoneNumber.getText().toString().trim().equals("") || etAddress.getText().toString().trim().equals("") || etPassword.getText().toString().equals("") || etConfirmPassword.getText().toString().equals("")) {
                    if (NetworkCheck.isNetworkAvailable(RegisterActivity.this)) {
                        if (etPhoneNumber.getText().length() == 10) {
                            if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                                register();
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


    private void register() {
        try {
            new HitJSPService(this, null, new TaskCompleted() {

                @Override
                public void onTaskCompleted(String result, int resultType) {
                    try {
                        if (result.trim().equals("successfully registered")) {
                            Intent i = new Intent(RegisterActivity.this, DashboardActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                    }

                }
            }, "http://techhome.net16.net/registration.php?name=" + etName.getText().toString().trim() + "&mobile=" + etPhoneNumber.getText().toString().trim() + "&email=" + etEmail.getText().toString().trim()
                    + "&address=" + etAddress.getText().toString().trim() + "&city=" + etCity.getText().toString().trim()
                    + "&pincode=" + etPinCode.getText().toString().trim() + "&password=" + etPassword.getText().toString().trim(), 1).execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Invalid character found", Toast.LENGTH_SHORT).show();
        }
    }
}


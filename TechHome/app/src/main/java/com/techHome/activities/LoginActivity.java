package com.techHome.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;
import com.techHome.asynctasks.LoginAsyncTask;
import com.techHome.dto.LoginDTO;
import com.techHome.dto.MessageCustomDialogDTO;
import com.techHome.ui.SnackBar;
import com.techHome.util.NetworkCheck;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    /* @InjectView(R.id.txtRegister)
     TextView txtRegister;*/
    @Bind(R.id.txtForgotPassword)
    TextView txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        //logging into the application and clearing the flags
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setMobile(etUsername.getText().toString().trim());
                loginDTO.setPassword(etPassword.getText().toString().trim());
                if (NetworkCheck.isNetworkAvailable(LoginActivity.this)) {
                    if (loginDTO.getMobile().length() == 10) {
                        if (loginDTO.getPassword().length() != 0) {
                            LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginActivity.this, loginDTO);
                            loginAsyncTask.execute();
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

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        /*txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });*/

    }
}

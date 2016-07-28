package com.techHome.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Button;

import com.neopixl.pixlui.components.edittext.EditText;
import com.techHome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 5/14/2016.
 */
public class ForgotPasswordActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
 @Bind(R.id.btnForgotPassword)
    Button btnForgotPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.forgot_password);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            btnForgotPassword.setBackgroundResource(R.drawable.ripple);
        }

    }
}

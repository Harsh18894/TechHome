package com.techHome.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.techHome.R;
import com.techHome.dto.MessageCustomDialogDTO;
import com.techHome.ui.SnackBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dell on 4/16/2016.
 */

//entering preferences setup

public class EnterModeActivity extends AppCompatActivity {

    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.btnGuest)
    Button btnGuest;
    @Bind(R.id.relative_layout)
    RelativeLayout relativeLayout;
    private boolean doubleBackToExitPressedOnce = false;
    /*@Bind(R.id.toolbar)
    Toolbar toolbar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mode);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);


        /*setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.enter_mode);*/


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.containsKey("logout")) {
                if (bundle.getString("logout").equals("Logout")) {
                    MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                    messageCustomDialogDTO.setMessage("Logout Successful.");
                    messageCustomDialogDTO.setButton("OK");
                    SnackBar.show(EnterModeActivity.this, messageCustomDialogDTO);
                }
            }
        }


        //applying ripple effect on the buttons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnLogin.setBackgroundResource(R.drawable.ripple_rounded);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnRegister.setBackgroundResource(R.drawable.ripple_rounded);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnGuest.setBackgroundResource(R.drawable.ripple);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnterModeActivity.this, LoginActivity.class);
                // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnterModeActivity.this, RegisterActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterModeActivity.this, DashboardActivity.class);
 /*               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("type",2);
 */
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Snackbar snackbar = Snackbar.make(relativeLayout, "Press back again to exit.", Snackbar.LENGTH_LONG);
        snackbar.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

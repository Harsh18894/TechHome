package com.techHome.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.techHome.R;
import com.techHome.ui.CustomTitle;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 4/24/2016.
 */

//Notifications setup

public class NotificationsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtNoNotifications)
    TextView txtNoNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.notifications);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

      //  txtNoNotifications.setText(CustomTitle.getTitle(this, getString(R.string.no_notifications)));
    }
}

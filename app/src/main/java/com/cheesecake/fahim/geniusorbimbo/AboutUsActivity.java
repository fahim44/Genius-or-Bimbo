package com.cheesecake.fahim.geniusorbimbo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AboutUsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
        actionBar.setTitle("About Us");

        TextView tv_about_us = (TextView) findViewById(R.id.tv_about_us);

        StringBuffer sb = new StringBuffer();
        sb.append("Genius Or Bimbo\n");
        sb.append("Version: 1.0\n");
        sb.append("Dev: Fahim Salam Chowdhury\n");
        sb.append("Com: CheesecakeTech\n");
        sb.append("2015-");
        tv_about_us.setText(sb);
    }


}

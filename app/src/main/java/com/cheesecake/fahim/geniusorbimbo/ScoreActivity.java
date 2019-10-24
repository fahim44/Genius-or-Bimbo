package com.cheesecake.fahim.geniusorbimbo;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cheesecake.fahim.geniusorbimbo.model.MainModelClass;


public class ScoreActivity extends ActionBarActivity {

    private final int NUM_OF_QUES = MainModelClass.NUM_OF_QUES;
    private int high_score;

    private TextView tv_high_score;
    private TextView tv_total_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.MAGENTA));

        tv_high_score = (TextView) findViewById(R.id.tv_high_scored);
        tv_total_score = (TextView) findViewById(R.id.tv_total_scored);

        tv_total_score.setText(Integer.toString(NUM_OF_QUES));

        SharedPreferences preferences = getSharedPreferences("GoBPref",0);
        tv_high_score.setText(Integer.toString(preferences.getInt("HighScore",0)));


    }


}

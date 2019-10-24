package com.cheesecake.fahim.geniusorbimbo;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    ImageButton bt_start,bt_score;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(1,116,223)));

        ScaleAnimation sc = new ScaleAnimation(0,2,0,2);
        sc.setDuration(500);

        bt_start = (ImageButton) findViewById(R.id.start_button);
        bt_score = (ImageButton) findViewById(R.id.score_button);

        bt_start.startAnimation(sc);
        bt_score.startAnimation(sc);

        bt_start.setOnClickListener(this);
        bt_score.setOnClickListener(this);


        //ads
        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }

    @Override
    public void onBackPressed() {
        //
        super.onBackPressed();
       // onStop();
       // System.exit(0);
    }

    @Override
    public void onClick(View v) {

        RotateAnimation rotate = new RotateAnimation(0,360);
        rotate.setDuration(500);

        AlphaAnimation alpha = new AlphaAnimation(0,100);
        alpha.setDuration(10000);

        if(v.getId()==R.id.start_button){
            bt_start.startAnimation(rotate);
            bt_score.startAnimation(alpha);

            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            intent.putExtra("quesNo",0);
            startActivity(intent);
        }

        else if(v.getId() == R.id.score_button){
            bt_score.startAnimation(rotate);
            bt_start.startAnimation(alpha);

            Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.menu_button_about_us){
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if(adView !=null)
            adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adView !=null)
            adView.resume();
    }

    @Override
    protected void onDestroy() {
        if(adView !=null)
            adView.destroy();
        super.onDestroy();
    }
}

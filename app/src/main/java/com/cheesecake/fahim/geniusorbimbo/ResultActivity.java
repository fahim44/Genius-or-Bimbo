package com.cheesecake.fahim.geniusorbimbo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cheesecake.fahim.geniusorbimbo.model.ExpListDataProvider;
import com.cheesecake.fahim.geniusorbimbo.model.MainModelClass;
import com.cheesecake.fahim.geniusorbimbo.model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


public class ResultActivity extends ActionBarActivity {

    Hashtable<String,List<String>> question_details;
    List<String> question_header_num;
    ExpandableListView exp_list;
    ExpListAdapter adapter;
    int[] submitted_ans_list;

    TextView tv_score,tv_total_score;

    public ArrayList<Question> questions = new ArrayList<Question>();

    private final int NUM_OF_QUES = MainModelClass.NUM_OF_QUES;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(51,102,0)));

        submitted_ans_list = getIntent().getExtras().getIntArray("submittedAns");
        questions = getIntent().getExtras().getParcelableArrayList("questions");

        exp_list = (ExpandableListView) findViewById(R.id.exp_list_results);

        ExpListDataProvider dataProvider = new ExpListDataProvider(this,questions);
        question_details = dataProvider.get_info();
        question_header_num = new ArrayList<String>(question_details.keySet());

        adapter = new ExpListAdapter(this,question_details,question_header_num,submitted_ans_list);
        exp_list.setAdapter(adapter);

        score = 0;

        for (int i = 0; i<NUM_OF_QUES;i++){
            if (submitted_ans_list[i]==1)
                score++;
        }

        SharedPreferences preferences = getSharedPreferences("GoBPref",0);
        int high_score = preferences.getInt("HighScore",0);

        if(score> high_score){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("HighScore",score);
            editor.commit();
        }

        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_score.setText(Integer.toString(score));

        tv_total_score = (TextView)findViewById(R.id.tv_total_score);
        tv_total_score.setText("/ " + Integer.toString(NUM_OF_QUES));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_button_home){
            setResult(44);
            this.finish();
            return true;
        }

        else if(item.getItemId() == R.id.menu_button_share){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String sAux = "\nMy score is " +Integer.toString(score)+ ". Beat me if you can\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id="+getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(44);
        this.finish();
        //super.onBackPressed();
    }
}

package com.cheesecake.fahim.geniusorbimbo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cheesecake.fahim.geniusorbimbo.model.MainModelClass;
import com.cheesecake.fahim.geniusorbimbo.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class QuestionActivity extends ActionBarActivity implements View.OnClickListener {

    ActionBar actionBar;

    TextView tv_timer, tv_question;
    RadioButton rb_opt_1,rb_opt_2,rb_opt_3;
    ImageButton btn_submit;
    RadioGroup rg;

    public ArrayList<Question> questions = new ArrayList<Question>();
    public ArrayList<Question> all_questions = new ArrayList<Question>();

    TimerCounter timer;

    TransitionDrawable transitionDrawable;

    int current_ques_no;
    int[] submitted_ans_list;

    private final int NUM_OF_QUES = MainModelClass.NUM_OF_QUES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ////initializing integers
        current_ques_no = getIntent().getExtras().getInt("quesNo");
        submitted_ans_list = new int[NUM_OF_QUES];
        for(int l=0;l<NUM_OF_QUES;l++){
            submitted_ans_list[l] = 0;
        }


       ini_everything();

    }

    void ini_everything(){
        ////actionbar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));


        ///timer
        tv_timer = (TextView) findViewById(R.id.tv_timer);

        transitionDrawable = (TransitionDrawable) tv_timer.getBackground();

        timer = new TimerCounter(30000,1000);

        //////question & opts
        rg = (RadioGroup) findViewById(R.id.rg);


        tv_question = (TextView) findViewById(R.id.tv_question);
        rb_opt_1 = (RadioButton) findViewById(R.id.rb_option1);
        rb_opt_2 = (RadioButton) findViewById(R.id.rb_option2);
        rb_opt_3 = (RadioButton) findViewById(R.id.rb_option3);



        btn_submit = (ImageButton) findViewById(R.id.button_submit);
        btn_submit.setOnClickListener(this);


        ///xml parse
        QuestionPullParser parser = new QuestionPullParser();
        all_questions = parser.parseXML(this);

        if(questions.size()==0){
            Random random_num = new Random();

            while (questions.size()<NUM_OF_QUES) {
                int random = random_num.nextInt(MainModelClass.NUM_OF_ALL_QUES);
                if (!questions.contains(all_questions.get(random)))
                    questions.add(all_questions.get(random));
            }
        }

        set_up_ques();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_question);
        timer.cancel();
        ini_everything();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.start();
        transitionDrawable.resetTransition();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_button_skip){
            current_ques_no++;
            if(current_ques_no < NUM_OF_QUES){
                set_up_ques();
            }
            else {
                start_result_activity();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_submit){
            if(rb_opt_1.isChecked()==true)
                check_up_ans(1);
            else if(rb_opt_2.isChecked()==true)
                check_up_ans(2);
            else if(rb_opt_3.isChecked()==true)
                check_up_ans(3);
            else {
                MediaPlayer mp = MediaPlayer.create(QuestionActivity.this,R.raw.sound_error);
                mp.start();
            }
        }
    }

    //////////////////////functions///////////////////////////////////////
    ///////////////////setup ques/////////////////////////////////////
    private void set_up_ques() {
        MediaPlayer mp = MediaPlayer.create(QuestionActivity.this,R.raw.sound_submit);
        mp.start();

        actionBar.setTitle("Question #" + Integer.toString(current_ques_no+1));
        timer.cancel();
        transitionDrawable.resetTransition();
        timer.start();

        tv_question.setText(questions.get(current_ques_no).getQuestion_title());
        rb_opt_1.setText(questions.get(current_ques_no).getOption_1());
        rb_opt_2.setText(questions.get(current_ques_no).getOption_2());
        rb_opt_3.setText(questions.get(current_ques_no).getOption_3());

        //rb_opt_1.setChecked(false);
      //  rb_opt_2.setChecked(false);
       // rb_opt_3.setChecked(false);
        rg.clearCheck();
    }

    /////////////check up ans//////////////////////
    private void check_up_ans(int opt){
        if(opt == questions.get(current_ques_no).getAns()){
            submitted_ans_list[current_ques_no] = 1;
        }
        current_ques_no++;
        if(current_ques_no < NUM_OF_QUES){
            set_up_ques();
        }
        else {
            start_result_activity();
        }
    }

    /////////////start result activity///////////////
    private void start_result_activity(){
        Bundle b = new Bundle();
        b.putIntArray("submittedAns", submitted_ans_list);
        b.putParcelableArrayList("questions", questions);
        Intent intent = new Intent(QuestionActivity.this,ResultActivity.class);
        intent.putExtras(b);

        startActivityForResult(intent,12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode == 44)
            this.finish();
    }

    /////////////////////timer class//////////////////////////////////////
    public class TimerCounter extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimerCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            tv_timer.setText("00:" + millisUntilFinished / 1000);

            if(millisUntilFinished < 10000 && millisUntilFinished >= 9000){
                transitionDrawable.startTransition(1000);
            }

        }

        @Override
        public void onFinish() {
            tv_timer.setText("00:0");

            current_ques_no++;
            if(current_ques_no < NUM_OF_QUES){
                set_up_ques();
            }
            else {
                start_result_activity();
            }

        }
    }
}

package com.cheesecake.fahim.geniusorbimbo.model;

import android.content.Context;

import com.cheesecake.fahim.geniusorbimbo.QuestionPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by fahim on 30-Apr-15.
 */
public class ExpListDataProvider {

    private final int NUM_OF_QUES =MainModelClass.NUM_OF_QUES;
    public ArrayList<Question> questions = new ArrayList<Question>();


    public ExpListDataProvider(Context context,ArrayList<Question> qs){

      ///xml parse
        QuestionPullParser parser = new QuestionPullParser();
        //questions = parser.parseXML(context);
        questions = qs;
    }




    public Hashtable<String, List<String>> get_info(){
        Hashtable<String, List<String>> question_details =  new Hashtable<String, List<String>>();

        for(int i =0;i < NUM_OF_QUES ;i++){
            List<String> list = new ArrayList<String>();
            list.add(questions.get(i).getQuestion_title());
            list.add(questions.get(i).getAns_description());

            question_details.put("Question " + Integer.toString(i+1) , list);
        }

        return question_details;
    }
}

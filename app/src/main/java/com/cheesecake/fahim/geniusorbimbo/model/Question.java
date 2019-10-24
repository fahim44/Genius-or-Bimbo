package com.cheesecake.fahim.geniusorbimbo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fahim on 19-Apr-15.
 */
public class Question implements Parcelable{

    private int question_id;
    private String question_title;
    private String option_1;
    private String option_2;
    private String option_3;
    private int ans;
    private String ans_description;


    //set
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public void setAns_description(String ans_description) {
        this.ans_description = ans_description;
    }



    //get
    public int getQuestion_id() {
        return question_id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public String getOption_1() {
        return option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public int getAns() {
        return ans;
    }

    public String getAns_description() {
        return ans_description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getQuestion_id());
        dest.writeString(getQuestion_title());
        dest.writeString(getOption_1());
        dest.writeString(getOption_2());
        dest.writeString(getOption_3());
        dest.writeInt(getAns());
        dest.writeString(getAns_description());
    }

    public Question(){
    }

    public Question(Parcel in){
        setQuestion_id(in.readInt());
        setQuestion_title(in.readString());
        setOption_1(in.readString());
        setOption_2(in.readString());
        setOption_3(in.readString());
        setAns(in.readInt());
        setAns_description(in.readString());
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.ClassLoaderCreator<Question>(){
        @Override
        public Question createFromParcel(Parcel source, ClassLoader loader) {
            return new Question(source);
        }


        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        /* public Question createFromParcel(Parcel in){
                    return new Question(in);
                }
        */
        public Question[] newArray(int size) {
            return new Question[size];
        }

    };
}

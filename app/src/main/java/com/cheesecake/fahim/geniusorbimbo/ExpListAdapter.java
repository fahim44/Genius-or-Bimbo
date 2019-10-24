package com.cheesecake.fahim.geniusorbimbo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by fahim on 30-Apr-15.
 */
public class ExpListAdapter extends BaseExpandableListAdapter {


    Context context;
    Hashtable<String, List<String>> question_details;
    List<String> question_header_num;
    int[] submitted_ans_list;

    public ExpListAdapter(Context cnx, Hashtable<String, List<String>> q_d, List<String> q_head, int[] ans_list ){
        submitted_ans_list = ans_list;
        context = cnx;
        question_details = q_d;
        question_header_num = q_head;
    }


    @Override
    public int getGroupCount() {
        return question_details.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return question_details.get(question_header_num.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return question_details.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return question_details.get(question_header_num.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //String parent_title =  question_header_num.get(groupPosition);
        String parent_title =  "Question " + Integer.toString(groupPosition+1);
        int parent_type = (int) getGroupId(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_list_parent_layout,parent,false);
        }
        TextView tv_parent = (TextView) convertView.findViewById(R.id.tv_exp_list_parent);

        if(submitted_ans_list[parent_type]==0){
            String hex = "77ffff00"; //yellow
            tv_parent.setBackgroundColor(Integer.parseInt(hex,16));
        }
        else if(submitted_ans_list[parent_type]==1){
            String hex = "770000ff"; //blue
            tv_parent.setBackgroundColor(Integer.parseInt(hex,16));
        }
        tv_parent.setTypeface(null, Typeface.BOLD);
        tv_parent.setText(parent_title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //String child_title = (String) getChild(groupPosition,childPosition);
        String child_title = question_details.get("Question " + Integer.toString(groupPosition+1)).get(childPosition);
        int child_type = (int) getChildId(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_list_child_layout,parent,false);
        }

        TextView tv_child_header = (TextView) convertView.findViewById(R.id.tv_exp_list_child_header);
        TextView tv_child_body = (TextView) convertView.findViewById(R.id.tv_exp_list_child_body);
        tv_child_body.setText(child_title);
        if(child_type==0){
            tv_child_header.setTextColor(Color.RED);
            tv_child_header.setText("Q : ");
        }
        else if(child_type==1){
            tv_child_header.setTextColor(Color.BLUE);
            tv_child_header.setText("A : ");
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

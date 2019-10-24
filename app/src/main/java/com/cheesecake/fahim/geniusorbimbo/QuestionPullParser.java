package com.cheesecake.fahim.geniusorbimbo;

import android.content.Context;

import com.cheesecake.fahim.geniusorbimbo.model.Question;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahim on 21-Apr-15.
 */
public class QuestionPullParser {

    private static final String QUESTION_TAG = "question";
    private static final String QUESTION_ID = "questionId";
    private static final String QUESTION_TITLE = "questionTitle";
    private static final String OPTION_1 = "option1";
    private static final String OPTION_2 = "option2";
    private static final String OPTION_3 = "option3";
    private static final String ANS = "ans";
    private static final String ANS_DESCRIPTION = "ansDescription";


    //public List<Question> questions = new ArrayList<Question>();

    public ArrayList<Question> parseXML (Context context){
        InputStream stream = context.getResources().openRawResource(R.raw.questions);
        SAXBuilder builder = new SAXBuilder();

        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            Document document = (Document)builder.build(stream);
            Element root_node =  document.getRootElement();

            List<Element> list = root_node.getChildren(QUESTION_TAG);

            for (Element node : list){
                Question question = new Question();
                question.setQuestion_id(Integer.parseInt(node.getChildText(QUESTION_ID)));
                question.setQuestion_title(node.getChildText(QUESTION_TITLE));
                question.setOption_1(node.getChildText(OPTION_1));
                question.setOption_2(node.getChildText(OPTION_2));
                question.setOption_3(node.getChildText(OPTION_3));
                question.setAns(Integer.parseInt(node.getChildText(ANS)));
                question.setAns_description(node.getChildText(ANS_DESCRIPTION));

                questions.add(question);
            }


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
}

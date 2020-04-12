package com.example.urstudyguide_migration.Common.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestItem extends Object implements Serializable {

    private String question;
    private List<Answer> answers = new ArrayList();

    public TestItem() {
        //NOTE:- Seeting the default two answers
        answers.add(new Answer());
        answers.add(new Answer());
    }

    public TestItem(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    //TODO:- Make the TestItem to evaluate himself!

    public double evaluateQuestion(TestItem testToEvualte) {
        int hits = 0;
        //TODO:- Throw error!
        if (this.answers.size() != testToEvualte.answers.size())
            return -1;
        hits = testToEvualte.answers.size();
        for(int i=0; i<this.answers.size(); i++) {
            if(!testToEvualte.answers.get(i).isCorrect() && this.answers.get(i).isCorrect())
                hits--;
        }
        return hits / testToEvualte.answers.size();
    }
}

package com.example.bbvacontrol.uranitexpert.Common.Models;

import java.io.Serializable;
import java.util.List;

public class TestItem implements Serializable {

    private String question;
    private List<Answer> answers;

    public TestItem() {
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
}

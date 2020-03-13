package com.example.urstudyguide_migration.Common.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestItem implements Serializable {

    private String question;
    private List<Answer> answers = new ArrayList();

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

package com.example.bbvacontrol.uranitexpert.Common.Models;

import java.io.Serializable;

public class Answer implements Serializable {

    private String text;
    private boolean isCorrect = false;

    public Answer() {
    }

    public Answer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}

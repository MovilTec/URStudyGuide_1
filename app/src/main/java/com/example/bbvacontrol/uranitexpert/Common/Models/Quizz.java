package com.example.bbvacontrol.uranitexpert.Common.Models;

import java.io.Serializable;

public class Quizz implements Serializable {


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public float getPrice() {
            return noQuestions;
        }

        public void setPrice(int price) {
            this.noQuestions = price;
        }

        private String name;
        private String description;
    //private String Author;
    //private String Members;
    private int noQuestions;

        public Quizz() {

        }

    public Quizz(String name, String description, int questions) {
            this.name = name;
            this.description = description;
        this.noQuestions = questions;
        }
}

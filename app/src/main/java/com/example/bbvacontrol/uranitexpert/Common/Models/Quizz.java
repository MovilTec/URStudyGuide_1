package com.example.bbvacontrol.uranitexpert.Common.Models;

import java.io.Serializable;
import java.util.List;

public class Quizz implements Serializable {

    private String name;
    private String description;
    private String author;
    private List<String> members;
    private List<TestItem> testItems;

    public Quizz() { }

    public Quizz(String name, String description, String author, List<String> members, List<TestItem> testItems) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.members = members;
        this.testItems = testItems;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<TestItem> getTestItems() {
        return testItems;
    }

    public void setTestItems(List<TestItem> testItems) {
        this.testItems = testItems;
    }
}

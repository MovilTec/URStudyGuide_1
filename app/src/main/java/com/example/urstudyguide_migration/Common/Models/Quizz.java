package com.example.urstudyguide_migration.Common.Models;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseReponseWithString;
import com.example.urstudyguide_migration.Common.Services.FirebaseRequests;
import com.example.urstudyguide_migration.Common.Services.RequestType;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class Quizz implements Serializable {

    private String name;
    private String description;
    private String author;
    private List<String> allowed_users;
    private List<TestItem> testItems;

    static private String authorName;

    public Quizz() { }

    public Quizz(String name, String description, String author, List<String> allowed_users, List<TestItem> testItems) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.allowed_users = allowed_users;
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

    public List<String> getAllowed_users() {
        return allowed_users;
    }

    public void setAllowed_users(List<String> allowed_users) {
        this.allowed_users = allowed_users;
    }

    public List<TestItem> getTestItems() {
        return testItems;
    }

    public void setTestItems(List<TestItem> testItems) {
        this.testItems = testItems;
    }

    public void getAuthorName(TextView textView ) {

         ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals(author)) {
                        for(DataSnapshot authorData : data.getChildren()) {
                            if(authorData.getKey().equals("name")) {
                                textView.setText("by " + authorData.getValue().toString());
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        FirebaseRequests.getInstance().singleRequest(RequestType.USERS, listener);
    }


}


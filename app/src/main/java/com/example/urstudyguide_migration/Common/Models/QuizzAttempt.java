package com.example.urstudyguide_migration.Common.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import androidx.annotation.NonNull;

public class QuizzAttempt implements Serializable, Parcelable {

    private String user;
    private double grade;
    private long timestamp;

    public QuizzAttempt(String user, double grade, long timestamp) {
        super();
        this.user = user;
        this.grade = grade;
        this.timestamp = timestamp;
    }

    public QuizzAttempt() {
    }

    protected QuizzAttempt(Parcel in) {
        user = in.readString();
        grade = in.readDouble();
        timestamp = in.readLong();
    }

    public static final Creator<QuizzAttempt> CREATOR = new Creator<QuizzAttempt>() {
        @Override
        public QuizzAttempt createFromParcel(Parcel in) {
            return new QuizzAttempt(in);
        }

        @Override
        public QuizzAttempt[] newArray(int size) {
            return new QuizzAttempt[size];
        }
    };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStudent() {
        return user;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        Date date = new Date(timestamp);
        DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
        return df.format(date);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setStudentName(TextView textView) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Users").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("name").getValue();
                textView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user);
    }
}

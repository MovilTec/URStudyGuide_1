package com.example.urstudyguide_migration.Quizzes.ui.quizzattempts;

import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;
import com.example.urstudyguide_migration.Common.Services.FirebaseRequests;
import com.example.urstudyguide_migration.Quizzes.navigators.AttemptsNavigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class QuizzAttemptsViewModel extends ViewModel {

    public AttemptsNavigator navigator;
    public void getAttempts(String quizzId) {
        FirebaseDatabase.getInstance().getReference("Quizzes")
                .child(quizzId)
                .child("Attempts")
                .addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<QuizzAttempt> quizzAttempts = new ArrayList();
                for(DataSnapshot postData : dataSnapshot.getChildren()) {
                    QuizzAttempt quizzAttempt = postData.getValue(QuizzAttempt.class);
                    quizzAttempts.add(quizzAttempt);
                }
                navigator.onAttemtpsRetrivalSuccess(quizzAttempts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                navigator.onError(databaseError.toException().getMessage());
            }
        });
    }
}

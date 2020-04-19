package com.example.urstudyguide_migration.Quizzes.ui.quizzattempts;

import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;
import com.example.urstudyguide_migration.Common.Services.FirebaseRequests;
import com.example.urstudyguide_migration.Quizzes.navigators.AttemptsNavigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class QuizzAttemptsViewModel extends ViewModel {

    public AttemptsNavigator navigator;
    public void getAttempts(String quizzId) {
        String path = "Quizzes/" + quizzId + "/Attempts";
        FirebaseDatabase.getInstance().getReference(path).orderByChild("user")
                .addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Map<String, List<QuizzAttempt>> attempts_map = new HashMap();
            List<QuizzAttempt> quizzAttempts = new ArrayList();
            String user = "";
                for(DataSnapshot postData : dataSnapshot.getChildren()) {
                    QuizzAttempt quizzAttempt = postData.getValue(QuizzAttempt.class);
                    quizzAttempts.add(quizzAttempt);

                    String attempt_user = (String) postData.child("user").getValue();
                    if (!user.equals(attempt_user)) {
                        user = attempt_user;
                        attempts_map.put(user, quizzAttempts);

                        //Clear the quizz Attempts array
                        quizzAttempts = new ArrayList();
                    }
                }
                navigator.onAttemtpsRetrivalSuccess(attempts_map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                navigator.onError(databaseError.toException().getMessage());
            }
        });
    }
}

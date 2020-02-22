package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzes;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bbvacontrol.uranitexpert.Common.Models.Quizz;
import com.example.bbvacontrol.uranitexpert.Quizzes.QuizzNavigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class QuizzesViewModel extends ViewModel {

    public QuizzNavigator navigator;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference quizzesRef = database.getReference("Quizzes");

    void getAvilableQuizzes() {
        final List<Quizz> quizz = new ArrayList<>();
        quizzesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postData : dataSnapshot.getChildren()) {
                    Quizz quizzItem = postData.getValue(Quizz.class);
                    quizz.add(quizzItem);
                }
                navigator.onDataRetrieve(quizz);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    void createQuizz(String name) {
//        quizzesRef.setValue("Hello, World!");

    }

}

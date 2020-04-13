package com.example.urstudyguide_migration.Quizzes.ui.quizzes;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Quizzes.navigators.QuizzNavigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;

import static android.content.ContentValues.TAG;

public class QuizzesViewModel extends ViewModel {

    public QuizzNavigator navigator;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //TODO:- Move this to allowed quizzes!
    DatabaseReference quizzesRef = database.getReference("Quizzes");

    void getAvilableQuizzes() {
        final Map<String, Quizz> quizz = new HashMap();
        quizzesRef.addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postData : dataSnapshot.getChildren()) {
                    Quizz quizzItem = postData.getValue(Quizz.class);
                    String key = postData.getKey();
                    quizz.put(key, quizzItem);
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

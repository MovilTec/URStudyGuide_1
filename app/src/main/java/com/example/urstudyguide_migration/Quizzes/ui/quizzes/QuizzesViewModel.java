package com.example.urstudyguide_migration.Quizzes.ui.quizzes;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.Quizzes.navigators.QuizzNavigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;

import static android.content.ContentValues.TAG;

public class QuizzesViewModel extends ViewModel {

    public QuizzNavigator navigator;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    void getAvilableQuizzes() {
        final Map<String, Quizz> quizz = new HashMap();
        final String userId = User.getInstance().getUserId();

        database.getReference("Quizzes")
                .addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> allowed_users = new HashMap();
                for(DataSnapshot postData : dataSnapshot.getChildren()) {
                    allowed_users = (Map<String, Object>) postData.child("allowed_users").getValue();
                    String author = postData.child("author").getValue().toString();
                    if (allowed_users.containsValue(userId) || author.equals(userId) ) {
                        Quizz quizzItem = postData.getValue(Quizz.class);
                        String key = postData.getKey();
                        quizz.put(key, quizzItem);
                    }

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

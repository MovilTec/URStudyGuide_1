package com.example.urstudyguide_migration.Quizzes.ui.simulator;

import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.Quizzes.navigators.SimulatorNavigator;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModel;

public class SimulatorViewModel extends ViewModel {
    SimulatorNavigator navigator;

    void setAttempt(String quizzId, double grade) {

        Map attempt = new HashMap();
        attempt.put("timestamp", ServerValue.TIMESTAMP);
        attempt.put("user", User.getInstance().getUserId());
        attempt.put("grade", grade);

        FirebaseDatabase.getInstance().getReference("Quizzes")
                .child(quizzId).child("Attempts")
                .push()
                .setValue(attempt)
                .addOnSuccessListener(aVoid -> {
                    navigator.onSucces(String.valueOf(grade));
                }
                ).addOnFailureListener(e -> {
                    navigator.onError("No ha sido posible registrar intento. Error: \n" + e.getMessage());
                });
    }
}

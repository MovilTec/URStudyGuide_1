package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.bbvacontrol.uranitexpert.Common.Models.Quizz;
import com.example.bbvacontrol.uranitexpert.Common.Models.TestItem;
import com.example.bbvacontrol.uranitexpert.Common.Services.FirebaseRequests;
import com.example.bbvacontrol.uranitexpert.Common.Services.RequestType;
import com.example.bbvacontrol.uranitexpert.Quizzes.QuizzCreatorNavigator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorViewModel extends ViewModel {

    private FirebaseRequests firebaseRequests = FirebaseRequests.getInstance();
    QuizzCreatorNavigator navigator;

    void createQuizz(String quizzName, List<TestItem> testItems) {
        String author = FirebaseAuth.getInstance().getUid();
        String quizzDescription = "";
        List<String> members = new ArrayList();
        Quizz quizz = new Quizz(quizzName, "", author, members, testItems);
        firebaseRequests.write(RequestType.QUIZZ, quizz, onSuccess, onFailure);
    }

    private OnSuccessListener onSuccess = new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            navigator.onCreatedQuizz();
        }
    };

    private OnFailureListener onFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            navigator.onError(e.getLocalizedMessage());
        }
    };
}

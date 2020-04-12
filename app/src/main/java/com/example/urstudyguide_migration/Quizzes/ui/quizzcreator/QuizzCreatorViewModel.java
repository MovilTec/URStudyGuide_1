package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Common.Services.FirebaseRequests;
import com.example.urstudyguide_migration.Common.Services.RequestType;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.Quizzes.navigators.QuizzCreatorNavigator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;

public class QuizzCreatorViewModel extends ViewModel {

    private FirebaseRequests firebaseRequests = FirebaseRequests.getInstance();
    QuizzCreatorNavigator navigator;
    private List<TestItem> testItems = new ArrayList();
    private int tposition;
    private String mQuizzId;

    void createQuizz(String quizzName, String quizzDescription) {
        String author = FirebaseAuth.getInstance().getUid();
        List<String> members = new ArrayList();
        Quizz quizz = new Quizz(quizzName, quizzDescription, author, members, testItems);
        firebaseRequests.write(RequestType.QUIZZ, quizz, onSuccess, onFailure);
    }

    void saveQuizz(String quizzName, String quizzDescription) {
        //TODO:- Recieve the route to save the edited Test
        //TODO:- Do someting on quizz name change or description!

        Map<String, Object> editedTest = new HashMap();
        editedTest.put("testItems", testItems);
        FirebaseDatabase.getInstance().getReference().child("Quizzes").child(mQuizzId).updateChildren(editedTest, (databaseError, databaseReference) -> {
            //TODO:- Something on success
            if (databaseError != null) {
                navigator.onError(databaseError.getMessage());
                return;
            }
            navigator.onSavedQuizz();
        });
    }

    private OnSuccessListener onSuccess = new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            navigator.onCreatedQuizz();
        }
    };

    private OnFailureListener onFailure = new OnFailureListener() {
        @Override
        public
        void onFailure(@NonNull Exception e) {
            navigator.onError(e.getLocalizedMessage());
        }
    };

    public void addQuestions() {
        TestItem testItem = new TestItem();
        testItems.add(testItem);
    }

    public void removeQuestions() {
        testItems.remove(testItems.size() - 1);
    }

    public void setPosition(int position) {
        this.tposition = position;
    }

    public void setTestItem(TestItem testItem) {
        testItems.set(tposition, testItem);
        navigator.updateRecyclerView(tposition, testItem);
    }

    public void setQuizzId(String quizzId) {
        if(quizzId != null) {
            if (!quizzId.isEmpty()){
                this.mQuizzId = quizzId;
            }
        }
    }

    public TestItem getTestItem() {
        return testItems.get(tposition);
    }

    public List<TestItem> getTestItems() {
        return this.testItems;
    }

    public void setQuizz(Quizz quizz) {
        this.testItems = quizz.getTestItems();
    }
}

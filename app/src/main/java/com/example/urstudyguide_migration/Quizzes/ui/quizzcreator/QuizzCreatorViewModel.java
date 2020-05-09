package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import androidx.lifecycle.ViewModel;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Common.Services.FirebaseRequests;
import com.example.urstudyguide_migration.Quizzes.navigators.QuizzCreatorNavigator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class QuizzCreatorViewModel extends ViewModel {

    QuizzCreatorNavigator navigator;
    private List<TestItem> testItems = new ArrayList();
    private int tposition;
    private String mQuizzId;

    public Quizz createQuizzFrom(String quizzName, String quizzDescription) {
        String author = FirebaseAuth.getInstance().getUid();
        HashMap<String, Object> members = new HashMap();
        return new Quizz(quizzName, quizzDescription, author, members, testItems);
    }

    void saveQuizz(String quizzName, String quizzDescription) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Map<String, Object> editedTest = new HashMap();
        editedTest.put("name", quizzName);
        editedTest.put("description", quizzDescription);
        editedTest.put("testItems", testItems);

        final String path = "Quizzes/" + mQuizzId;
        database.getReference(path)
                .updateChildren(editedTest, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                navigator.onError(databaseError.getMessage());
                return;
            }
            FirebaseManager firebaseManager = new FirebaseManager(database);
            CompletableFuture<DataSnapshot> quizzPromise = firebaseManager.read(path);
            quizzPromise.thenAccept(dataSnapshot -> {
                Quizz quizz = dataSnapshot.getValue(Quizz.class);
                navigator.onSavedQuizz(quizz);
            });
        });
    }

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

    public void setTestItems(List<TestItem> testItems) {
        this.testItems = testItems;
    }

    public void setQuizz(Quizz quizz) {
        this.testItems = quizz.getTestItems();
    }
}

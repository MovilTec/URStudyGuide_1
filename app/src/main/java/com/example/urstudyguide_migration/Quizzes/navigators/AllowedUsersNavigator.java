package com.example.urstudyguide_migration.Quizzes.navigators;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;

import java.util.ArrayList;
import java.util.HashMap;

public interface AllowedUsersNavigator {
    void onRetrieved(ArrayList<String> users);
    void changeSaveQuizzButton();
    void appendUserToRecyclerView(UsersModelingClass user);
    void onCreatedQuizz(Quizz quizz);
    void onSavedQuizz(HashMap<String, Object> savedAllowedUsers);
    void onError(String errorMessage);
}

package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.allowedUsers;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;

import java.util.ArrayList;

public interface AllowedUsersNavigator {
    void onRetrieved(ArrayList<String> users);
    void changeSaveQuizzButton();
    void appendUserToRecyclerView(UsersModelingClass user);
    void onCreatedQuizz(Quizz quizz);
    void onError(String errorMessage);
}

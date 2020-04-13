package com.example.urstudyguide_migration.Quizzes.navigators;

import com.example.urstudyguide_migration.Common.Helpers.Navigator;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.TestItem;

public interface QuizzCreatorNavigator extends Navigator {
    void onCreatedQuizz(Quizz quizz);
    void onError(String errorMessage);
    void updateRecyclerView(int position, TestItem testItem);
    void onSavedQuizz();
}

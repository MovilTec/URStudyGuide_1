package com.example.urstudyguide_migration.Quizzes;

import com.example.urstudyguide_migration.Common.Helpers.Navigator;
import com.example.urstudyguide_migration.Common.Models.Quizz;

public interface QuizzCreatorNavigator extends Navigator {
    void onCreatedQuizz();
    void onError(String errorMessage);
}

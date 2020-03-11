package com.example.urstudyguide_migration.Quizzes;

import com.example.urstudyguide_migration.Common.Models.Quizz;

import java.util.List;

public interface QuizzNavigator {
    void onDataRetrieve(List<Quizz> items);
    void onQuizzCreated();
}

package com.example.urstudyguide_migration.Quizzes;

import com.example.urstudyguide_migration.Common.Models.Quizz;

import java.util.List;
import java.util.Map;

public interface QuizzNavigator {
    void onDataRetrieve(Map<String, Quizz> items);
    void onQuizzCreated();
}

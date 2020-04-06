package com.example.urstudyguide_migration.Quizzes.navigators;

import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;

import java.util.List;

public interface AttemptsNavigator {
    void onAttemtpsRetrivalSuccess(List<QuizzAttempt> items);
    void onError(String errorMessage);
}

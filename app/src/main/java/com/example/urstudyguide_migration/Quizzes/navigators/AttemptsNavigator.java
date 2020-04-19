package com.example.urstudyguide_migration.Quizzes.navigators;

import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;

import java.util.List;
import java.util.Map;

public interface AttemptsNavigator {
    void onAttemtpsRetrivalSuccess(Map<String, List<QuizzAttempt>> items);
    void onError(String errorMessage);
}

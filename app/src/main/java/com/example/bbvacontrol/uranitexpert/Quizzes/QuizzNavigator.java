package com.example.bbvacontrol.uranitexpert.Quizzes;

import com.example.bbvacontrol.uranitexpert.Common.Models.Quizz;
import java.util.List;

public interface QuizzNavigator {
    void onDataRetrieve(List<Quizz> items);
    void onQuizzCreated();
}

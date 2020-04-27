package com.example.urstudyguide_migration.Quizzes.navigators;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import java.util.HashMap;

public interface QuizzDetailNavigator {
    void updateRecyclerViewWith(HashMap<String, Object> allowedUsers);
    void updatedEditedQuizz(Quizz quizz);
}

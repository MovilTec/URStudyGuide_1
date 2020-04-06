package com.example.urstudyguide_migration.Quizzes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.urstudyguide_migration.Quizzes.ui.quizzattempts.QuizzAttemptsFragment;
import com.example.urstudyguide_migration.R;

public class QuizzAttempts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_attempts_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuizzAttemptsFragment.newInstance())
                    .commitNow();
        }
    }
}

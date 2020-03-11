package com.example.urstudyguide_migration.Quizzes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urstudyguide_migration.Quizzes.ui.quizzes.QuizzesFragment;
import com.example.urstudyguide_migration.R;

public class Quizzes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizzes_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuizzesFragment.newInstance())
                    .commitNow();
        }
    }
}

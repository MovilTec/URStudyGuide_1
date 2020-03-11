package com.example.urstudyguide_migration.Quizzes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorFragment;
import com.example.urstudyguide_migration.R;

public class QuizzCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_creator_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuizzCreatorFragment.newInstance())
                    .commitNow();
        }
    }
}

package com.example.bbvacontrol.uranitexpert.Quizzes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bbvacontrol.uranitexpert.R;

import com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator.QuizzCreatorFragment;

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

package com.example.bbvacontrol.uranitexpert.Quizzes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzes.QuizzesFragment;
import com.example.bbvacontrol.uranitexpert.R;

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

package com.example.bbvacontrol.uranitexpert.Quizzes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzdetail.QuizzDetailFragment;
import com.example.bbvacontrol.uranitexpert.R;

public class QuizzDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_detail_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuizzDetailFragment.newInstance())
                    .commitNow();
        }
    }
}

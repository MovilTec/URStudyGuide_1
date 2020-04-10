package com.example.urstudyguide_migration.Quizzes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.urstudyguide_migration.Quizzes.ui.quizzcreatorquestion.QuizzCreatorQuestionFragment;
import com.example.urstudyguide_migration.R;

import java.util.List;

public class QuizzCreatorQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_creator_question_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuizzCreatorQuestionFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof QuizzCreatorQuestionFragment)
                ((QuizzCreatorQuestionFragment)f).onBackPressed();
        }
        super.onBackPressed();
    }
}

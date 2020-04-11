package com.example.urstudyguide_migration.Quizzes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorFragment;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreatorquestion.QuizzCreatorQuestionFragment;
import com.example.urstudyguide_migration.R;

import java.util.List;

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

    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof QuizzCreatorQuestionFragment)
                ((QuizzCreatorQuestionFragment)f).onBackPressed();
        }
//        super.onBackPressed();
    }

    public void callBack() {
        super.onBackPressed();

    }
}

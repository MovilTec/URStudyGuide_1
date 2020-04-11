package com.example.urstudyguide_migration.Quizzes;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urstudyguide_migration.Quizzes.ui.quizzdetail.QuizzDetailFragment;
import com.example.urstudyguide_migration.R;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

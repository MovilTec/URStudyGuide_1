package com.example.urstudyguide_migration.Quizzes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.urstudyguide_migration.Quizzes.navigators.QuizzDetailNavigator;
import com.example.urstudyguide_migration.Quizzes.ui.quizzdetail.QuizzDetailFragment;
import com.example.urstudyguide_migration.Quizzes.ui.quizzdetail.QuizzDetailViewModel;
import com.example.urstudyguide_migration.R;

import java.util.HashMap;

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
            case R.id.advance_setting:
                Intent intent = new Intent(this, QuizzAdvanceSettings.class);
                startActivityForResult(intent, 111);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if(resultCode == RESULT_OK) {
                HashMap<String, Object> allowed_users = (HashMap<String, Object>) data.getSerializableExtra("allowed_users");
                for(Fragment fragment: getSupportFragmentManager().getFragments()) {
                    if (fragment instanceof QuizzDetailNavigator){
                        QuizzDetailNavigator navigator = (QuizzDetailNavigator) fragment;
                        navigator.updateRecyclerViewWith(allowed_users);
                    }
                }
            }
        }
    }
}

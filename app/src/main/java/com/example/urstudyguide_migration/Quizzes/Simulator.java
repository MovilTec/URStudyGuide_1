package com.example.urstudyguide_migration.Quizzes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.urstudyguide_migration.Quizzes.ui.simulator.SimulatorFragment;
import com.example.urstudyguide_migration.R;

public class Simulator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulator_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SimulatorFragment.newInstance())
                    .commitNow();
        }
    }
}

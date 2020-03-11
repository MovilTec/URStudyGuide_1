package com.example.urstudyguide_migration;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class MCSA_70_740_Activity extends AppCompatActivity {

    private Toolbar mToolBar;

    MCSA_70_740_Questions MCSAquestions = new MCSA_70_740_Questions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcsa_70_740_);

         mToolBar = findViewById(R.id.MCSA_70_740_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("MCSA 70-740");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void openUnit1(View view) {
        String title = "Unit 1";
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    public void openUnit2(View view) {
        String title = "Unit 2";
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    public void openUnit3(View view) {
        String title = "Unit 3";
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    public void openUnit4(View view) {
        String title = "Unit 4";
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}

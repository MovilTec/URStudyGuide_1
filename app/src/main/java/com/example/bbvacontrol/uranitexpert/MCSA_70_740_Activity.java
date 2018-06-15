package com.example.bbvacontrol.uranitexpert;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        startActivity(intent);
    }

    public void openUnit2(View view) {
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        startActivity(intent);
    }

    public void openUnit3(View view) {
//        MCSAquestions.updateTextView("MCSA 70-740 Unit 3 Quiz");
//        MCSAquestions.setTitle("MCSA 70-740 Unit3");
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        startActivity(intent);
    }

    public void openUnit4(View view) {
        Intent intent = new Intent(this, MCSA_70_740_Questions.class);
        startActivity(intent);
    }
}

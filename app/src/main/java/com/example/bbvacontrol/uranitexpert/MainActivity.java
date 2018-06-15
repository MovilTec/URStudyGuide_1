package com.example.bbvacontrol.uranitexpert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.uranitexpert.MESSAGE";

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth =  FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("URStudyGuide");

    }

        public void sendMessage(View view) {
        Intent intent = new Intent(this, NetworkActivity.class);
        startActivity(intent);
    }
        public void openProgrammingActivity(View view){
        Intent intent = new Intent(this, Programming.class);
        startActivity(intent);
    }
        public void openDataBaseActivity(View view){
        Intent intent = new Intent(this, DataBase.class);
        startActivity(intent);
    }
        public void openClientSupportActivity(View view){
        Intent intent = new Intent(this, ClientSupport.class);
        startActivity(intent);
    }

        public void openMCSA_70_740_Activity(View view){
        Intent intent = new Intent(this, MCSA_70_740_Activity.class);
        startActivity(intent);
        }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if(currentUser == null){
            sendToStart();
        }
    }

    private void sendToStart(){
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    //Accioens al elegir cualquier botón del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.main_LogOut_button){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        if(item.getItemId() == R.id.main_AccountSettings_button){
            Intent settingsIntent = new Intent(this, AccountSettings.class);
            startActivity(settingsIntent);
        }

        return true;
    }
}



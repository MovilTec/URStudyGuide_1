package com.example.bbvacontrol.uranitexpert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.uranitexpert.MESSAGE";

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private SectionsPagerAdapter mSections;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DatabaseReference deviceToken_Reference;
    private DatabaseReference mUserDatabase;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(users.getUserID());

        mAuth =  FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("URStudyGuide");

        mViewPager = findViewById(R.id.main_tabPager);
        mSections = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSections);

        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
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
        }else{
            mUserDatabase.child("online").setValue(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUserDatabase.child("online").setValue(false);
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
        deviceToken_Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(users.getUserID()).child("device_token");
        if(item.getItemId() == R.id.main_LogOut_button){
//            FirebaseAuth.getInstance().signOut();
//            sendToStart();
            deviceToken_Reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseAuth.getInstance().signOut();
                        sendToStart();
                    }else{
                        Exception logoutError = task.getException();
                        Toast.makeText(MainActivity.this, "Han error has ocurred during logout. ERROR CODE: " + logoutError, Toast.LENGTH_LONG).show();
                        System.out.println(logoutError);
                    }
                }
            });
        }else if(item.getItemId() == R.id.main_AccountSettings_button){
            Intent settingsIntent = new Intent(this, AccountSettings.class);
            startActivity(settingsIntent);
        }else if(item.getItemId() == R.id.main_AllUsers_button){
            Intent usersIntent = new Intent(this, UsersActivity.class);
            startActivity(usersIntent);
        }

        return true;
    }
}



package com.example.urstudyguide_migration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.Main.AccountSettings;
import com.example.urstudyguide_migration.Social.Users.UsersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import io.reactivex.annotations.NonNull;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.uranitexpert.MESSAGE";

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private SectionsPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DatabaseReference deviceToken_Reference;
    private DatabaseReference mUserDatabase;

    private Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseApp.initializeApp(this);

        mAuth =  FirebaseAuth.getInstance();
        FirebaseAnalytics f = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        f.logEvent("Prueba", bundle);
        if(mAuth.getCurrentUser() != null) {
            User user = User.getInstance();
            user.setContext(this);
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUserId());
        }
        setupToolBar();
        setupPager();
    }

    private void setupToolBar() {
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("URStudyGuide");
    }

    private void setupPager() {
        mViewPager = findViewById(R.id.main_tabPager);
        mAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getBaseContext());

        // It can only handle three pages!!!
        mViewPager.setAdapter(mAdapter);

        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
//         Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
        if(currentUser == null){
            sendToStart();
        }else{
            mUserDatabase.child("online").setValue(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            mUserDatabase.child("online").setValue(ServerValue.TIMESTAMP);
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
        deviceToken_Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(users.getUserID()).child("device_token");
        if(item.getItemId() == R.id.main_LogOut_button){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
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

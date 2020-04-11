package com.example.urstudyguide_migration.Social.Users;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.R;
import com.example.urstudyguide_migration.Social.ProfileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolBar = findViewById(R.id.users_app_tool_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mUsersList = findViewById(R.id.users_list);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        
        return true;
    }

    public void startListening(){
    Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(50);

    FirebaseRecyclerOptions<UsersModelingClass> options = new FirebaseRecyclerOptions.Builder<UsersModelingClass>()
                .setQuery(query, UsersModelingClass.class)
                .build();

    FirebaseRecyclerAdapter firebaseRecyclerAdapter = new UsersFirebaseAdapter(options, userID -> {
            Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
            profileIntent.putExtra("user_id", userID);
            startActivity(profileIntent);
    });

    mUsersList.setAdapter(firebaseRecyclerAdapter);
    firebaseRecyclerAdapter.startListening();

    }

}

package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.allowedUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.Quizzes.QuizzDetail;
import com.example.urstudyguide_migration.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class AllowedUserSelection extends AppCompatActivity implements AllowedUsersNavigator {

    private SpinnerDialog spinnerDialog;
    private AllowedUsersViewModel mViewModel;
    private Quizz mQuizz;
    private RecyclerView mRecyclerView;
    private AllowedUsersAdapter mAdapter;
    private Button createButton;

    private View.OnClickListener onClickListener = view -> {

        HashMap<String, Object> allowedUsers =  mAdapter.getAllowedUsers();
        mViewModel.createQuizzWith(allowedUsers);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowed_user_selection);
        mViewModel =  ViewModelProviders.of(this).get(AllowedUsersViewModel.class);
        mViewModel.navigator = this;

        setupToolbar();

        Intent intent = getIntent();
        mViewModel.setQuizz((Quizz) intent.getSerializableExtra("quizz"));

        mViewModel.getUsers();

        setupView();
        setupRecyclerView();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupView() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> spinnerDialog.showSpinerDialog());

        createButton = findViewById(R.id.allowed_user_create_button);
        createButton.setOnClickListener(onClickListener);
    }

    private void setupRecyclerView() {
        mAdapter = new AllowedUsersAdapter();

        mRecyclerView = findViewById(R.id.allowed_user_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }

    // ----- Navigator implementation

    @Override
    public void onRetrieved(ArrayList<String> users) {
        spinnerDialog = new SpinnerDialog(this, users, "Selecciona los usuarios con los que que deseas compartir tu cuestionario ");
        spinnerDialog.bindOnSpinerListener((item, position) -> mViewModel.selectedUser(item));
    }

    @Override
    public void changeSaveQuizzButton() {
        createButton.setText("SAVE QUIZZ");
    }

    @Override
    public void appendUserToRecyclerView(UsersModelingClass user) {
        mAdapter.appendUser(user);
    }

    @Override
    public void onCreatedQuizz(Quizz quizz) {
        Intent intent = new Intent(this, QuizzDetail.class);
        intent.putExtra("Quizz", quizz);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String errorMessage) {

    }
}

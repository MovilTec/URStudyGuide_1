package com.example.urstudyguide_migration.Quizzes.ui.quizzattempts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;
import com.example.urstudyguide_migration.Quizzes.navigators.AttemptsNavigator;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizzAttemptsFragment extends Fragment implements AttemptsNavigator {

    private QuizzAttemptsViewModel mViewModel;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private String quizzId;

    public static QuizzAttemptsFragment newInstance() {
        return new QuizzAttemptsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_attempts_fragment, container, false);
        Intent intent = getActivity().getIntent();
        String quizzName = intent.getStringExtra("quizzName");
        quizzId = intent.getStringExtra("quizzId");
        setupToolbar(view, quizzName);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzAttemptsViewModel.class);
        mViewModel.navigator = this;
        mViewModel.getAttempts(quizzId);
    }

    private void setupToolbar(View view, String name) {
        mToolbar = view.findViewById(R.id.quizzattempts_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(name);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.quizzattempts_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onAttemtpsRetrivalSuccess(Map<String, List<QuizzAttempt>> items) {
        List<Student> students = new ArrayList();
        items.forEach( (student_code, attemps) -> {
            Student student = new Student(student_code, attemps);
            students.add(student);
        });
//        mRecyclerView.setAdapter(new QuizzAttemptsAdapter(items));
        mRecyclerView.setAdapter(new QuizzAttemptsExpandableAdapter(students));
    }

    @Override
    public void onError(String errorMessage) {

    }

}

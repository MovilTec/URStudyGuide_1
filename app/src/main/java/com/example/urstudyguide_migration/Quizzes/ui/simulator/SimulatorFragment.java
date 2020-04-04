package com.example.urstudyguide_migration.Quizzes.ui.simulator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.R;


public class SimulatorFragment extends Fragment {

    private SimulatorViewModel mViewModel;
    private RecyclerView mRecyclerview;
    private SimulatorAdapter mAdapter;
    private Toolbar mToolBar;
    private Quizz mQuizz;
    private Button mSubmitButton;

    public static SimulatorFragment newInstance() {
        return new SimulatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simulator_fragment, container, false);
        mRecyclerview = view.findViewById(R.id.quizzsimulator_recyclerview);
        mToolBar = view.findViewById(R.id.quizzsimulator_toolbar);
        Intent intent = getActivity().getIntent();
        mQuizz = (Quizz) intent.getSerializableExtra("Quizz");
        mSubmitButton = view.findViewById(R.id.quizzsimulator_submit_button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SimulatorViewModel.class);
        setupToolBar();
        setupRecyclerView();
        setupView();
    }

    private void setupToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mQuizz.getName());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerview.setLayoutManager(layoutManager);
        mAdapter = new SimulatorAdapter(mQuizz.getTestItems());
        mRecyclerview.setAdapter(mAdapter);
    }

    private void setupView() {
        mSubmitButton.setOnClickListener(v -> {
            //TODO:- Make a quizz validation
            double grade = mAdapter.getGrade();
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Calificación!")
                    .setMessage(String.valueOf(grade));
            alert.create();
            alert.show();
        });
    }

}

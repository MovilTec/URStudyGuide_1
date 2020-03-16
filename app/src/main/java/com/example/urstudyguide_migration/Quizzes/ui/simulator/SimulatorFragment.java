package com.example.urstudyguide_migration.Quizzes.ui.simulator;

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

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.R;


public class SimulatorFragment extends Fragment {

    private SimulatorViewModel mViewModel;
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapater;
    private Toolbar mToolBar;
    private Quizz mQuizz;

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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SimulatorViewModel.class);
        // TODO: Use the ViewModel
        setupToolBar();
        setupRecyclerView();
    }

    private void setupToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mQuizz.getName());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerview.setLayoutManager(layoutManager);
//        mRecyclerview.setHasFixedSize(true);

        mAdapater = new SimulatorAdapter(mQuizz.getTestItems());
        mRecyclerview.setAdapter(mAdapater);
    }

}

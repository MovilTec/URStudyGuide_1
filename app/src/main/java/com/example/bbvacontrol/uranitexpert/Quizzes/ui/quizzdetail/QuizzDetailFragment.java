package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bbvacontrol.uranitexpert.Common.Models.Quizz;
import com.example.bbvacontrol.uranitexpert.R;

public class QuizzDetailFragment extends Fragment {

    private QuizzDetailViewModel mViewModel;
    private Toolbar mToolbar;
    private Quizz mQuizz;
    private TextView mTextView;

    public static QuizzDetailFragment newInstance() {
        return new QuizzDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_detail_fragment, container, false);
        Intent intent = getActivity().getIntent();
        mQuizz = (Quizz) intent.getSerializableExtra("Quizz");
        setupNavBar(view, mQuizz.getName());
        setupView(view, mQuizz.getDescription());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setupNavBar(View view, String quizzName) {
        mToolbar = view.findViewById(R.id.quizzdetail_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(quizzName);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupView(View view, String description) {
        mTextView = view.findViewById(R.id.quizzdetail_textView);
        mTextView.setText(description);
        // TODO:- Set the start quizz button and the editor button and validation
    }

}

package com.example.urstudyguide_migration.Quizzes.ui.quizzdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.Quizzes.QuizzCreator;
import com.example.urstudyguide_migration.Quizzes.Simulator;
import com.example.urstudyguide_migration.R;

public class QuizzDetailFragment extends Fragment {

    private QuizzDetailViewModel mViewModel;
    private Toolbar mToolbar;
    private Quizz mQuizz;
    private TextView mTextView, mAuthor;
    private Button mStartButton, mEditButton;

    private View.OnClickListener startButtonAction = (v -> {
        Intent intent = new Intent(getContext(), Simulator.class);
        intent.putExtra("Quizz", mQuizz);
        startActivity(intent);
    });

    private View.OnClickListener editButtonAction = ( v -> {
        Intent intent = new Intent(getContext(), QuizzCreator.class);
        intent.putExtra("Quizz", mQuizz);
        startActivity(intent);
    });

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
        setupView(view);
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

    private void setupView(View view) {
        mTextView = view.findViewById(R.id.quizzdetail_textView);
        mAuthor = view.findViewById(R.id.quizzdetail_author_textView);
        mEditButton = view.findViewById(R.id.quizzdetail_edit_button);
        mEditButton.setOnClickListener(editButtonAction);

        mStartButton = view.findViewById(R.id.quizzdetail_start_button);
        mStartButton.setOnClickListener(startButtonAction);

        mTextView.setText(mQuizz.getDescription());
        validateEdit();
    }

    private void validateEdit() {
        String userID = User.getInstance().getUserID(getContext());
        if(userID == null) {
            userID = new Users().getUserID();
            if (!mQuizz.getAuthor().equals(userID)) {
                mEditButton.setAlpha(0);
                mEditButton.setEnabled(false);
            }
        } else {
            if (!mQuizz.getAuthor().equals(userID)) {
                mEditButton.setAlpha(0);
                mEditButton.setEnabled(false);
            }
        }
    }
}

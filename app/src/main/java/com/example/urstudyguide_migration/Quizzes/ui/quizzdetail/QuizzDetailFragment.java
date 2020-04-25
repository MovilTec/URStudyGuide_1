package com.example.urstudyguide_migration.Quizzes.ui.quizzdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.Quizzes.QuizzAttempts;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreator;
import com.example.urstudyguide_migration.Quizzes.Simulator;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.allowedUsers.AllowedUserSelection;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuizzDetailFragment extends Fragment implements QuizzDetailUsersRecyclerAdapter.UserResponder {

    private QuizzDetailViewModel mViewModel;
    private Toolbar mToolbar;
    private Quizz mQuizz;
    private TextView mTextView;
    private Button mStartButton, mEditButton, mAttemptsButton;
    private String quizzId;
    private RecyclerView mRecyclerView;

    private View.OnClickListener startButtonAction = (v -> {
        Intent intent = new Intent(getContext(), Simulator.class);
        intent.putExtra("Quizz", mQuizz);
        intent.putExtra("quizzId", quizzId);
        startActivity(intent);
    });

    private View.OnClickListener editButtonAction = ( v -> {
        Intent intent = new Intent(getContext(), QuizzCreator.class);
        intent.putExtra("Quizz", mQuizz);
        intent.putExtra("quizzId", quizzId);
        startActivity(intent);
    });

    private View.OnClickListener attemptsButtonAction = ( v -> {
        Intent intent = new Intent(getContext(), QuizzAttempts.class);
        intent.putExtra("quizzName", mQuizz.getName());
        intent.putExtra("quizzId", quizzId);
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
        quizzId = (String) intent.getSerializableExtra("quizzId");
        setupNavBar(view, mQuizz.getName());
        setupView(view);
        setupRecyclerView(view);

        validateOptions();
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

//       Setting the menu icon
        Drawable settings_icon = ContextCompat.getDrawable(getContext(),R.drawable.ic_settings);
        mToolbar.setOverflowIcon(settings_icon);
    }

    private void setupView(View view) {
        mTextView = view.findViewById(R.id.quizzdetail_textView);
        mEditButton = view.findViewById(R.id.quizzdetail_edit_button);
        mEditButton.setOnClickListener(editButtonAction);

        mAttemptsButton = view.findViewById(R.id.quizzdetail_attempts_button);
        mAttemptsButton.setOnClickListener(attemptsButtonAction);

        mStartButton = view.findViewById(R.id.quizzdetail_start_button);
        mStartButton.setOnClickListener(startButtonAction);

        mTextView.setText(mQuizz.getDescription());
    }

    private void setupRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.quizzdetail_users_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        List<String> allowedUsers = new ArrayList();
        for (Object object : mQuizz.getAllowed_users().values()) {
            String allowedUser = (String) object;
            allowedUsers.add(allowedUser);
        }
        Drawable drawablr = getResources().getDrawable(R.drawable.ic_add_user_vector);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new QuizzDetailUsersDecorator());
        mRecyclerView.setAdapter(new QuizzDetailUsersRecyclerAdapter(allowedUsers, this, drawablr));
    }

    private void validateOptions() {
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

                mAttemptsButton.setAlpha(0);
                mAttemptsButton.setEnabled(false);

                mRecyclerView.setAlpha(0);
                mRecyclerView.setEnabled(false);
            }
        }
    }

    @Override
    public void onUserAddAction() {
        Intent intent = new Intent(getContext(), AllowedUserSelection.class);
        intent.putExtra("quizz", mQuizz);
        getActivity().startActivityForResult(intent, 111);


    }
}

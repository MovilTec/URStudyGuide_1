package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Helpers.AlertableFragment;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.QuizzCreatorNavigator;
import com.example.urstudyguide_migration.Quizzes.QuizzDetail;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

public class QuizzCreatorFragment extends AlertableFragment implements QuizzCreatorNavigator {

    private QuizzCreatorViewModel mViewModel;
    private NumberPicker mNumberPicker;
    private RecyclerView mRecyclerView;
    private QuizzCreatorAdapter mAdapter;
    private Button mCreateQuizzButton;
    private TextView mQuizzName;

    private Button.OnClickListener createQuizzAction = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<TestItem> mTestItems = mAdapter.getQuizz();
            String quizzName = mQuizzName.getText().toString();
            mViewModel.createQuizz(quizzName, mTestItems);
        }
    };

    public static QuizzCreatorFragment newInstance() {
        return new QuizzCreatorFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_creator_fragment, parent, false);
        mNumberPicker = view.findViewById(R.id.number_picker);
        mRecyclerView = view.findViewById(R.id.quizzcreator_recyclerView);
        mCreateQuizzButton = view.findViewById(R.id.quizzcreator_button);
        mCreateQuizzButton.setOnClickListener(createQuizzAction);
        mQuizzName = view.findViewById(R.id.quizzcreator_quizzName);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzCreatorViewModel.class);
        mViewModel.navigator = this;
        setupNumberPicker();
        setupRecyclerView();
    }

    private void setupNumberPicker() {

        mNumberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                switch (action) {
                    case INCREMENT:
                        mAdapter.notifyItemInserted(mAdapter.addQuestions());
                        break;
                    case DECREMENT:
                        mAdapter.notifyItemRemoved(mAdapter.removeQuestions());
                        break;
                }
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new QuizzCreatorAdapter(new QuizzCreatorAdapter.QuizzCreatorHandler() {
            @Override
            public void onErrorMessage(String errorMessage) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreatedQuizz() {
        Intent intent = new Intent(getContext(), QuizzDetail.class);
//        intent.putExtra("quizz", quizz);
        startActivity(intent);
    }


    public void onError(String error) {
        displayErrorMessage(error);
    }
}

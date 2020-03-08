package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bbvacontrol.uranitexpert.Common.Helpers.AlertableFragment;
import com.example.bbvacontrol.uranitexpert.Common.Models.TestItem;
import com.example.bbvacontrol.uranitexpert.Quizzes.QuizzCreatorNavigator;
import com.example.bbvacontrol.uranitexpert.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

public class QuizzCreatorFragment extends AlertableFragment implements QuizzCreatorNavigator {

    private QuizzCreatorViewModel mViewModel;
    private NumberPicker mNumberPicker;
    private RecyclerView mRecyclerView;
    private QuizzCreatorAdapter mAdapter;
    private Button mQuizzCreatorButton;
    private EditText mQuizzName;

    public static QuizzCreatorFragment newInstance() {
        return new QuizzCreatorFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_creator_fragment, parent, false);
        mNumberPicker = view.findViewById(R.id.number_picker);
        mRecyclerView = view.findViewById(R.id.quizzcreator_recyclerView);
        mQuizzCreatorButton = view.findViewById(R.id.quizzcreator_button);
        mQuizzName = view.findViewById(R.id.quizzcreator_quizzName);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzCreatorViewModel.class);
        setupView();
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
                onError(errorMessage);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupView() {
        mQuizzCreatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String quizzName = validateQuizzName(mQuizzName);
                    List<TestItem> testItems = mAdapter.getTestItems();
                    mViewModel.createQuizz(quizzName, testItems);
                } catch (IncorrectQuizzName e) {
                    onError(e.getLocalizedMessage());
                }
            }
        });
    }

    private String validateQuizzName(EditText quizzName) throws IncorrectQuizzName {
        if (!quizzName.getText().toString().isEmpty()) {
            return quizzName.getText().toString();
        }
        throw new IncorrectQuizzName("");
    }

    @Override
    public void onCreatedQuizz() {
        //TODO:- Something when quizz created
    }

    @Override
    public void onError(String error) {

    }
}

class IncorrectQuizzName extends Exception {
    public IncorrectQuizzName(String errorMessage) {
        super(errorMessage);
    }
}

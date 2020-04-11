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
import com.example.urstudyguide_migration.Quizzes.QuizzCreatorQuestion;
import com.example.urstudyguide_migration.Quizzes.QuizzDetail;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreatorquestion.QuizzCreatorQuestionFragment;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class QuizzCreatorFragment extends AlertableFragment implements QuizzCreatorNavigator, QuizzCreatorPrubeAdatper.QuestionCreatable {

    private QuizzCreatorViewModel mViewModel;
    private NumberPicker mNumberPicker;
    private RecyclerView mRecyclerView;
    private QuizzCreatorPrubeAdatper mAdapter;
    private Button mCreateQuizzButton;
    private TextView mQuizzName;

    private Button.OnClickListener createQuizzAction = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<String> students;
            String quizzName = mQuizzName.getText().toString();
            mViewModel.createQuizz(quizzName);
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

        setupNumberPicker();
        setupRecyclerView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(QuizzCreatorViewModel.class);
        mViewModel.navigator = this;
        mViewModel.addQuestions();
    }

    private void setupNumberPicker() {
        mNumberPicker.setValueChangedListener((value, action) -> {
            switch (action) {
                case INCREMENT:
                    mAdapter.notifyItemInserted(mAdapter.addQuestions());
                    mViewModel.addQuestions();
                    break;
                case DECREMENT:
                    mAdapter.notifyItemRemoved(mAdapter.removeQuestions());
                    mViewModel.removeQuestions();
                    break;
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new QuizzCreatorPrubeAdatper(this::onQuestionSelected);
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

    @Override
    public void onQuestionSelected(int position) {

        mViewModel.setPosition(position);
        getActivity().
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.quizzcreator, QuizzCreatorQuestionFragment.newInstance())
                .addToBackStack(this.toString())
                .commit();
    }

    @Override
    public void updateRecyclerView(int position, TestItem testItem) {
        mAdapter.updateTestItem(position, testItem);
        mAdapter.notifyItemChanged(position);
    }
}

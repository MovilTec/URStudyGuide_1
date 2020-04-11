package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private TextView mQuizzName, mQuizzDescription;
    private Toolbar mToolbar;

    private Button.OnClickListener createQuizzAction = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO:- Set the allowed audience?
            List<String> students;

            try {
                String quizzName = validateQuizzName(mQuizzName.getText().toString());
                String quizzDescription = mQuizzDescription.getText().toString();

                mViewModel.createQuizz(quizzName, quizzDescription);
            } catch (InvalidQuizzName ex) {
                displayErrorMessage(ex.getLocalizedMessage());
            }
        }
    };

    private Button.OnClickListener editQuizzAction = (v -> {
        //TODO:- Save the edited test
        try {
            String quizzName = validateQuizzName(mQuizzName.getText().toString());
            String quizzDescription = mQuizzDescription.getText().toString();

            mViewModel.saveQuizz(quizzName, quizzDescription);
        } catch (InvalidQuizzName ex) {
            displayErrorMessage(ex.getLocalizedMessage());
        }
    });

    public static QuizzCreatorFragment newInstance() {
        return new QuizzCreatorFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_creator_fragment, parent, false);
        setupView(view);

        Intent intent = getActivity().getIntent();
        Quizz quizz = (Quizz) intent.getSerializableExtra("Quizz");
        setupQuizzFromEdit(quizz);

        return view;
    }

    private void setupView(View view ) {
        mNumberPicker = view.findViewById(R.id.number_picker);
        mRecyclerView = view.findViewById(R.id.quizzcreator_recyclerView);
        mCreateQuizzButton = view.findViewById(R.id.quizzcreator_button);
        mCreateQuizzButton.setOnClickListener(createQuizzAction);
        mQuizzName = view.findViewById(R.id.quizzcreator_quizzName);
        mQuizzDescription = view.findViewById(R.id.quizzcreator_quizzDescription);
        mToolbar = view.findViewById(R.id.quizzcreator_toolbar);
    }

    private void setupQuizzFromEdit(Quizz quizz) {
        if(quizz != null) {
            mViewModel.setQuizz(quizz);
            mQuizzName.setText(quizz.getName());
            mQuizzDescription.setText(quizz.getDescription());

            //
            mCreateQuizzButton.setText("SAVE CHANGES");
            mCreateQuizzButton.setOnClickListener(editQuizzAction);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupNumberPicker();
        setupRecyclerView();
        setupToolBar();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(QuizzCreatorViewModel.class);
        mViewModel.navigator = this;
        mViewModel.addQuestions();
    }

    @Override
    public void callTowayOut() {

    }

    private void setupToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Quizz Creator");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupNumberPicker() {
        mNumberPicker.setValueChangedListener((value, action) -> {
            switch (action) {
                case INCREMENT:
                    mAdapter.notifyItemInserted(mAdapter.addQuestions());
//                    mViewModel.addQuestions();
                    break;
                case DECREMENT:
                    mAdapter.notifyItemRemoved(mAdapter.removeQuestions());
//                    mViewModel.removeQuestions();
                    break;
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        // Passing the data from the viewModel
        mAdapter = new QuizzCreatorPrubeAdatper(this::onQuestionSelected, mViewModel.getTestItems());
        mRecyclerView.setAdapter(mAdapter);
    }

    private String validateQuizzName(String quizzName) throws InvalidQuizzName {
        if(quizzName.isEmpty()) {
            throw new InvalidQuizzName();
        }
        return quizzName;
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
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void updateRecyclerView(int position, TestItem testItem) {
        mAdapter.updateTestItem(position, testItem);
        mAdapter.notifyItemChanged(position);
    }
}

class InvalidQuizzName extends Exception {
    public InvalidQuizzName() {
        super("No se ha ingresado el nombre del cuestionario!");
    }
}

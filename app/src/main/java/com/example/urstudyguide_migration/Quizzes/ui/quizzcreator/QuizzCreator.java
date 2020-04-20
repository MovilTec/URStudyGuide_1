package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Exceptions.EmptyQuizzException;
import com.example.urstudyguide_migration.Common.Exceptions.InvalidQuizzName;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.QuizzDetail;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.allowedUsers.AllowedUserSelection;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreatorquestion.QuizzCreatorQuestion;
import com.example.urstudyguide_migration.Quizzes.navigators.QuizzCreatorNavigator;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

public class QuizzCreator extends AppCompatActivity implements QuizzCreatorNavigator, QuizzCreatorPrubeAdatper.QuestionCreatable {

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

            try {
                String quizzName = validateQuizzName(mQuizzName.getText().toString());
                String quizzDescription = mQuizzDescription.getText().toString();

                Quizz quizz = mViewModel.createQuizzFrom(quizzName, quizzDescription);
                validateQuizz(quizz);

                Intent intent = new Intent(getApplicationContext(), AllowedUserSelection.class);
                intent.putExtra("quizz", quizz);
                startActivity(intent);
            } catch (InvalidQuizzName | EmptyQuizzException ex ) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.quizz_creator_activity);
        setContentView(R.layout.quizz_creator_fragment);
        mViewModel = ViewModelProviders.of(this).get(QuizzCreatorViewModel.class);
        mViewModel.navigator = this;
        mViewModel.addQuestions();

        setupView();

        setupNumberPicker();
        setupRecyclerView();
        setupToolBar();

        Intent intent = getIntent();
        Quizz quizz = (Quizz) intent.getSerializableExtra("Quizz");
        mViewModel.setQuizzId(intent.getStringExtra("quizzId"));
        setupQuizzFromEdit(quizz);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                TestItem testItem = (TestItem) data.getSerializableExtra("testItem");
                mViewModel.setTestItem(testItem);
            }
        }
    }

    private void setupView() {
        mNumberPicker = findViewById(R.id.number_picker);
        mRecyclerView = findViewById(R.id.quizzcreator_recyclerView);

        mCreateQuizzButton = findViewById(R.id.quizzcreator_button);
        mCreateQuizzButton.setOnClickListener(createQuizzAction);

        mQuizzName = findViewById(R.id.quizzcreator_quizzName);
        mQuizzDescription = findViewById(R.id.quizzcreator_quizzDescription);
        mToolbar = findViewById(R.id.quizzcreator_toolbar);
    }

    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Quizz Creator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupNumberPicker() {
        mNumberPicker.setValueChangedListener((value, action) -> {
            switch (action) {
                case INCREMENT:
                    mAdapter.notifyItemInserted(mAdapter.addQuestions());
                    break;
                case DECREMENT:
                    mAdapter.notifyItemRemoved(mAdapter.removeQuestions());
                    break;
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // Passing the data from the viewModel
        mAdapter = new QuizzCreatorPrubeAdatper(this::onQuestionSelected, mViewModel.getTestItems());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupQuizzFromEdit(Quizz quizz) {
        if(quizz != null) {
            mViewModel.setQuizz(quizz);
            mQuizzName.setText(quizz.getName());
            mQuizzDescription.setText(quizz.getDescription());

            //
            mCreateQuizzButton.setText("SAVE CHANGES");
            mCreateQuizzButton.setOnClickListener(editQuizzAction);

            //Setting the number picker base on the quizz questions
            mNumberPicker.setValue(quizz.getTestItems().size());
        }
    }

    private String validateQuizzName(String quizzName) throws InvalidQuizzName {
        if(quizzName.isEmpty()) {
            throw new InvalidQuizzName();
        }
        return quizzName;
    }

    private void validateQuizz(Quizz quizz) throws EmptyQuizzException {
        List<TestItem> testItems = quizz.getTestItems();
        if (testItems.size() == 1 && testItems.get(0).getQuestion() == null) {
            throw new EmptyQuizzException();
        }
    }

    // ----- QuestionCreatable Implementation
    @Override
    public void onQuestionSelected(int position) {

        mViewModel.setPosition(position);
        Intent intent = new Intent(this, QuizzCreatorQuestion.class);
        intent.putExtra("testItem", mViewModel.getTestItem());
        startActivityForResult(intent, 1);
    }

    // --------- Navigator Implementation!
    @Override
    public void onError(String errorMessage) {
        displayErrorMessage(errorMessage);
    }

    @Override
    public void updateRecyclerView(int position, TestItem testItem) {
        mAdapter.updateTestItem(position, testItem);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onSavedQuizz() {

    }

    private void displayErrorMessage(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error)
                .setTitle("Error")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    //NOTE:- Do nothing!
                })
                .create()
                .show();
    }
}

package com.example.urstudyguide_migration.Quizzes.ui.quizzcreatorquestion;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.urstudyguide_migration.Common.Helpers.AlertableFragment;
import com.example.urstudyguide_migration.Common.Helpers.BackPressable;
import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.QuizzCreator;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.CustomNumberPicker;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorViewModel;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

public class QuizzCreatorQuestionFragment extends AlertableFragment implements BackPressable {

    private QuizzCreatorViewModel mViewModel;
    private ListView listView;
    private EditText mQuestionText;
    private NumberPicker numberPicker;
    private TestItem testItem = new TestItem();
    private QuizzCreatorAnswerAdapter answerAdapter;
    private int position;

    public static QuizzCreatorQuestionFragment newInstance() {
        return new QuizzCreatorQuestionFragment();
    }

    @Nullable
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_creator_question_fragment, parent, false);
        Intent intent = getActivity().getIntent();
        position = intent.getIntExtra("position", -1);
        setupView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(QuizzCreatorViewModel.class);
        testItem = mViewModel.getTestItem();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListView();
    }



    private void setupView(View view) {
        listView = view.findViewById(R.id.quizzquestioncreator_listView);
        mQuestionText = view.findViewById(R.id.quizzquestioncreator_question);
        numberPicker = view.findViewById(R.id.quizzquestioncreator_number_picker);

        mQuestionText.setText(testItem.getQuestion());
        numberPicker.setValue(testItem.getAnswers().size());
    }

    private void setupListView() {
        answerAdapter = new QuizzCreatorAnswerAdapter(getContext(), testItem.getAnswers());
        listView.setAdapter(answerAdapter);

        CustomNumberPicker customNumberPicker = new CustomNumberPicker();
        customNumberPicker.setup(answerAdapter, listView, numberPicker);
    }

    public void onBackPressed() {

        String question = mQuestionText.getText().toString();
        try {
            testItem.setQuestion(validateQuestion(question));
            List<Answer> answers = answerAdapter.getAnswersList();

            for(int i=0;i<answers.size(); i++) {
                View view = answerAdapter.getViewByPosition(i, listView);
                EditText editText = view.findViewById(R.id.quizzcreator_answer_text);
                CheckBox checkBox = view.findViewById(R.id.quizzcreator_answer_radioButton);
                // Setting the answer text
                String awnserText = editText.getText().toString();
                answers.get(i).setText(validateAnswer(awnserText));
                // Setting the correct answer flag
                boolean isCorrect = checkBox.isChecked();
                answers.get(i).setCorrect(isCorrect);
            }
            //TODO:- Run a validation for the answers
            testItem.setAnswers(answers);
            mViewModel.setTestItem(testItem);

            QuizzCreator a = (QuizzCreator) getActivity();
            a.callBack();
        } catch(InvalidAnswerQuestion | InvalidTestQuestion ex) {
            displayErrorMessage(ex.getLocalizedMessage());
        }
    }

    // ----- Private Methods ----
    private String validateQuestion(String question) throws InvalidTestQuestion {
        if (question.isEmpty()) {
            throw new InvalidTestQuestion();
        }
        return question;
    }

    private String validateAnswer(String answer) throws InvalidAnswerQuestion {
        if (answer.isEmpty()) {
            throw new InvalidAnswerQuestion();
        }
        return answer;
    }

}

class InvalidTestQuestion extends Exception {
    public InvalidTestQuestion() {
        super("No se ingreso la pregunta!");
    }
}

class InvalidAnswerQuestion extends Exception {
    public InvalidAnswerQuestion() {
        super("No se ingreso el texto de la respuesta!");
    }
}


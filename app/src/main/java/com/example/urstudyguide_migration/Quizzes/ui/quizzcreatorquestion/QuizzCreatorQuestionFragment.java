package com.example.urstudyguide_migration.Quizzes.ui.quizzcreatorquestion;

import androidx.lifecycle.ViewModelProvider;
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

import com.example.urstudyguide_migration.Common.Helpers.BackPressable;
import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.QuizzCreator;
import com.example.urstudyguide_migration.Quizzes.QuizzDetail;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.CustomNumberPicker;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorFragment;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorPrubeAdatper;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorViewModel;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class QuizzCreatorQuestionFragment extends Fragment implements BackPressable {

    private QuizzCreatorViewModel mViewModel;
    private ListView listView;
    private EditText question;
    private NumberPicker numberPicker;
    private TestItem testItem = new TestItem();
    private QuizzCreatorAnswerAdapter answerAdapter;
    private int position;

    public static QuizzCreatorQuestionFragment newInstance() {
        return new QuizzCreatorQuestionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_creator_question_fragment, container, false);
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
        question = view.findViewById(R.id.quizzquestioncreator_question);
        numberPicker = view.findViewById(R.id.quizzquestioncreator_number_picker);

        question.setText(testItem.getQuestion());
    }

    private void setupListView() {
        answerAdapter = new QuizzCreatorAnswerAdapter(getContext());
        listView.setAdapter(answerAdapter);

        CustomNumberPicker customNumberPicker = new CustomNumberPicker();
        customNumberPicker.setup(answerAdapter, listView, numberPicker);
    }

    public void onBackPressed() {
        //TODO:- Return the testItem
        testItem.setQuestion(question.getText().toString());

        List<Answer> answers = answerAdapter.getAnswersList();

        for(int i=0;i<answers.size(); i++) {
            View view = answerAdapter.getViewByPosition(i, listView);
            EditText editText = view.findViewById(R.id.quizzcreator_answer_text);
            CheckBox checkBox = view.findViewById(R.id.quizzcreator_answer_radioButton);
            // Setting the answer text
            String awnserText = editText.getText().toString();
            answers.get(i).setText(awnserText);
            // Setting the correct answer flag
            boolean isCorrect = checkBox.isChecked();
            answers.get(i).setCorrect(isCorrect);
        }
        //TODO:- Run a validation for the answers
        testItem.setAnswers(answers);
        mViewModel.setTestItem(position, testItem);
    }

}


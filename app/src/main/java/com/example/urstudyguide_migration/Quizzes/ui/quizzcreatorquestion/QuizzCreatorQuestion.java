package com.example.urstudyguide_migration.Quizzes.ui.quizzcreatorquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.urstudyguide_migration.Common.Exceptions.InvalidAnswerQuestion;
import com.example.urstudyguide_migration.Common.Exceptions.NonCorrectAnswerSelectedException;
import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.CustomNumberPicker;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorViewModel;
import com.example.urstudyguide_migration.Common.Exceptions.InvalidTestQuestion;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

public class QuizzCreatorQuestion extends AppCompatActivity {

    private QuizzCreatorViewModel mViewModel;
    private ListView listView;
    private EditText mQuestionText;
    private NumberPicker numberPicker;
    private TestItem testItem = new TestItem();
    private QuizzCreatorAnswerAdapter answerAdapter;
    private String mExceptions = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.quizz_creator_question_activity);
        setContentView(R.layout.quizz_creator_question_fragment);
//        mViewModel = ViewModelProviders.of(this).get(QuizzCreatorViewModel.class);
        Intent intent = getIntent();
        TestItem testitem = (TestItem) intent.getSerializableExtra("testItem");

        testItem = testitem;

        setupView();
        setupListView();


//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, QuizzCreatorQuestionFragment.newInstance())
//                    .commitNow();
//        }
    }

    private void setupView() {
        listView = findViewById(R.id.quizzquestioncreator_listView);
        mQuestionText = findViewById(R.id.quizzquestioncreator_question);
        numberPicker = findViewById(R.id.quizzquestioncreator_number_picker);

        mQuestionText.setText(testItem.getQuestion());
        numberPicker.setValue(testItem.getAnswers().size());
    }

    private void setupListView() {
        answerAdapter = new QuizzCreatorAnswerAdapter(this, testItem.getAnswers());
        listView.setAdapter(answerAdapter);

        CustomNumberPicker customNumberPicker = new CustomNumberPicker();
        customNumberPicker.setup(answerAdapter, listView, numberPicker);
    }

    @Override
    public void onBackPressed() {

        String question = mQuestionText.getText().toString();
        try {
            testItem.setQuestion(validateQuestion(question));
            List<Answer> answers = answerAdapter.getAnswersList();
            boolean hasCorrectAnswer = false;
            for(int i=0;i<answers.size(); i++) {
                View view = answerAdapter.getViewByPosition(i, listView);
                EditText editText = view.findViewById(R.id.quizzcreator_answer_text);
                CheckBox checkBox = view.findViewById(R.id.quizzcreator_answer_radioButton);
                // Setting the answer text
                String awnserText = editText.getText().toString();
                answers.get(i).setText(validateAnswer(awnserText));
                // Setting the correct answer flag
                boolean isCorrect = checkBox.isChecked();
                if (isCorrect)
                    hasCorrectAnswer = true;
                answers.get(i).setCorrect(isCorrect);
            }
            if(!hasCorrectAnswer)
                throw new NonCorrectAnswerSelectedException();
            //TODO:- Run a validation for the answers
            testItem.setAnswers(answers);

            Intent intent = new Intent();
            intent.putExtra("testItem", testItem);
            setResult(RESULT_OK, intent);

            super.onBackPressed();
//            QuizzCreator a = (QuizzCreator) getActivity();
//            a.callBack();
        } catch(InvalidAnswerQuestion ex) {
            mExceptions += ex.getLocalizedMessage() + "\n";
        } catch( InvalidTestQuestion ex) {
            mExceptions += ex.getLocalizedMessage() + "\n" ;
        } catch(NonCorrectAnswerSelectedException ex) {
            mExceptions += ex.getLocalizedMessage() ;
        } finally {
            if(!mExceptions.isEmpty()) {
                displayErrorMessage(mExceptions);
            }
            mExceptions = "";
        }

//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        for(Fragment f : fragments){
//            if(f != null && f instanceof QuizzCreatorQuestionFragment)
//                ((QuizzCreatorQuestionFragment)f).onBackPressed();
//        }
//        super.onBackPressed();
    }

    private void displayErrorMessage(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error)
                .setTitle("Error")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    //NOTE:- Do nothing!
                })
                .setNegativeButton("SALIR", (dialogInterface, i) -> {
                    //TODO:- Create a way to go out!
                    super.onBackPressed();
                })
                .create()
                .show();
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

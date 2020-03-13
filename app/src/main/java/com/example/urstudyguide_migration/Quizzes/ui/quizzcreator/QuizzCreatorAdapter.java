package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAdapter extends RecyclerView.Adapter<QuizzCreatorAdapter.mViewHolder> {

    private List<TestItem> testItems = new ArrayList();
    private QuizzCreatorAnswerAdapter answerAdapter;
    private List<QuizzCreatorAnswerAdapter> answerAdapters = new ArrayList();
    private QuizzCreatorHandler mQuizzCreatorHandler;
    private List<EditText> mQuestions = new ArrayList();
    private ListView mAwnserList;
    private ValueChangedListener onValueChange = new ValueChangedListener() {
        @Override
        public void valueChanged(int value, ActionEnum action) {
            switch(action) {
                case INCREMENT:
                    answerAdapter.addAnswers();
                    break;
                case DECREMENT:
                    answerAdapter.removeAnswers();
                    break;
            }
        }
    };

    public QuizzCreatorAdapter(QuizzCreatorHandler createQuizzAction) {
        TestItem testItem = new TestItem();
        testItems.add(testItem);
        mQuizzCreatorHandler = createQuizzAction;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizz_creator_item_adapater, parent, false);
        QuizzCreatorAdapter.mViewHolder vh = new QuizzCreatorAdapter.mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        answerAdapter = new QuizzCreatorAnswerAdapter();
        answerAdapters.add(answerAdapter);
        holder.answers.setAdapter(answerAdapter);

        mAwnserList = holder.answers;

        holder.numberPicker.setValueChangedListener(onValueChange);
        mQuestions.add(holder.quizzQuestion);
    }

    @Override
    public int getItemCount() {
        return testItems.size();
    }

    // ---------- Public class methods --------------

    public int addQuestions() {
        TestItem testItem = new TestItem();
        testItems.add(testItem);
        return testItems.size() - 1;
    }

    public int removeQuestions() {
        testItems.remove(testItems.size() - 1);
        return testItems.size() - 1;
    }

    public List<TestItem> getQuizz() {
        // TODO:- Create and get the quizz object
        // TODO:- Validate that the test Items
        for (int i=0; i<testItems.size(); i++) {
            String question = mQuestions.get(i).getText().toString();
            validateQuestion(question);
            TestItem testItem = testItems.get(i);

            testItem.setQuestion(question);
            for(int j=0;j<answerAdapters.get(i).getCount();j++) {
                View view = answerAdapters.get(i).getViewByPosition(j, mAwnserList);
                EditText editText = view.findViewById(R.id.quizzcreator_answer_text);
//                RadioButton radioButton = view.findViewById(R.id.quizzcreator_answer_radioButton);
                String awnserText = editText.getText().toString();
                testItem.getAnswers().get(j).setText(awnserText);
            }

        }
        return testItems;
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        public ListView answers;
        public NumberPicker numberPicker;
        public Button quizzCreatorButton;
        public EditText quizzQuestion;

        public mViewHolder(View itemView) {
            super(itemView);
            answers = itemView.findViewById(R.id.quizzcreator_listView);
            numberPicker = itemView.findViewById(R.id.answer_number_picker);
            quizzCreatorButton = itemView.findViewById(R.id.quizzcreator_button);
            quizzQuestion = itemView.findViewById(R.id.quizzcreator_question);
        }
    }

    // ----- Private Methods ----
    private void validateQuestion(String question) {
        if (question.isEmpty()) {
            mQuizzCreatorHandler.onErrorMessage("No se ingreso texto en alguna de las preguntas");
        }
    }

    // -------- Interface Comunication --------
    public interface QuizzCreatorHandler {
        void onErrorMessage(String errorMessage);
    }

}

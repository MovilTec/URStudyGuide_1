package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.bbvacontrol.uranitexpert.Common.Models.Answer;
import com.example.bbvacontrol.uranitexpert.Common.Models.TestItem;
import com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.bbvacontrol.uranitexpert.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAdapter extends RecyclerView.Adapter<QuizzCreatorAdapter.mViewHolder> {

    private List<TestItem> testItems = new ArrayList();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private QuizzCreatorAnswerAdapter answerAdapter;
    private List<QuizzCreatorAnswerAdapter> answerAdapters = new ArrayList();
    private List<EditText> mQuestions = new ArrayList();
    private QuizzCreatorHandler mQuizzCreatorHandler;

    private EditText mQuizzNameEditText;

    private Context parentContext;

    private ValueChangedListener onValueChange = new ValueChangedListener() {
        @Override
        public void valueChanged(int value, ActionEnum action) {
            switch(action) {
                case INCREMENT:
                    answerAdapter.notifyItemInserted(answerAdapter.addAnswers());
//                    notifyDataSetChanged();
                    break;
                case DECREMENT:
                    //TODO:- Implement the remove action
                    answerAdapter.removeAnswers();
                    break;
            }
        }
    };

    public QuizzCreatorAdapter(QuizzCreatorHandler quizzCreatorHandler) {
        TestItem testItem = new TestItem();
//        TestItem testItem2 = new TestItem();
        testItems.add(testItem);
//        testItems.add(testItem2);
        initializeAnswers();
        mQuizzCreatorHandler = quizzCreatorHandler;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizz_creator_item_adapater, parent, false);
        parentContext = parent.getContext();
        QuizzCreatorAdapter.mViewHolder vh = new QuizzCreatorAdapter.mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                parentContext,
                LinearLayoutManager.HORIZONTAL,
                false
        );

        layoutManager.setInitialPrefetchItemCount(testItems.get(position).getAnswers().size());
        holder.answers.setLayoutManager(layoutManager);
        holder.answers.setHasFixedSize(true);
        holder.answers.setNestedScrollingEnabled(false);

        answerAdapter = new QuizzCreatorAnswerAdapter(testItems.get(position).getAnswers());

//        answerAdapter.notifyDataSetChanged();

        answerAdapters.add(answerAdapter);

        holder.answers.setAdapter(answerAdapter);

        holder.answers.setRecycledViewPool(viewPool);

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
        return testItems;
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView answers;
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

    public List<TestItem> getTestItems() {
        // TODO:- Validate that the test Items
        for (int i=0; i<testItems.size(); i++) {
            String question = mQuestions.get(i).getText().toString();
            validateQuestion(question);
            TestItem testItem = testItems.get(i);

            testItem.setQuestion(question);
            testItem.setAnswers(answerAdapters.get(i).getAnwsers());
        }
        return testItems;
    }

    // ----- Private Methods ----

    private void initializeAnswers() {
        for (TestItem testItem :testItems) {
            List<Answer> answers = new ArrayList();
            answers.add(new Answer());
            answers.add(new Answer());
            testItem.setAnswers(answers);
        }
    }

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

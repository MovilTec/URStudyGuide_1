package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAdapter extends RecyclerView.Adapter<QuizzCreatorAdapter.mViewHolder> {

    private List<String> testItems = new ArrayList<>();
    private List<TestItem> _testItems;
    private QuizzCreatorAnswerAdapter answerAdapter;
    private QuizzCreatorHandler onCreateQuizz;
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
        testItems.add("");
        onCreateQuizz = createQuizzAction;
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
        holder.answers.setAdapter(answerAdapter);
        holder.numberPicker.setValueChangedListener(onValueChange);
//        holder.quizzCreatorButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onCreateQuizz.onCreateAction(_testItems);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return testItems.size();
    }

    // ---------- Public class methods --------------

    public int addQuestions() {
        testItems.add("New Item");
        return testItems.size() - 1;
    }

    public int removeQuestions() {
        testItems.remove(testItems.size() - 1);
        return testItems.size() - 1;
    }

    public List<TestItem> getQuizz() {
        // TODO:- Create and get the quizz object
        return _testItems;
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        public ListView answers;
        public NumberPicker numberPicker;
        public Button quizzCreatorButton;

        public mViewHolder(View itemView) {
            super(itemView);
            answers = itemView.findViewById(R.id.quizzcreator_listView);
            numberPicker = itemView.findViewById(R.id.answer_number_picker);
            quizzCreatorButton = itemView.findViewById(R.id.quizzcreator_button);
        }
    }

    public interface QuizzCreatorHandler {
        void onCreateAction(List<TestItem> testItems);
    }

}

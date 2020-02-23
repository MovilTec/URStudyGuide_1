package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.bbvacontrol.uranitexpert.Common.Models.TestItem;
import com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.bbvacontrol.uranitexpert.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAdapter extends RecyclerView.Adapter<QuizzCreatorAdapter.mViewHolder> {

    private List<String> testItems = new ArrayList<>();

    public QuizzCreatorAdapter() {
        TestItem testItem = new TestItem();
        testItems.add("");
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
        ListAdapter answerAdapter = new QuizzCreatorAnswerAdapter();
        holder.answers.setAdapter(answerAdapter);
        holder.numberPicker.setValueChangedListener(onValueChange);
    }

    @Override
    public int getItemCount() {
        return testItems.size();
    }

    public int addQuestions() {
        testItems.add("New Item");
        return testItems.size() - 1;
    }

    public int removeQuestions() {
        testItems.remove(testItems.size() - 1);
        return testItems.size() - 1;
    }

    private ValueChangedListener onValueChange = new ValueChangedListener() {
        @Override
        public void valueChanged(int value, ActionEnum action) {
            switch(action) {
                case INCREMENT:
                    break;
                case DECREMENT:
                    break;
            }
        }
    };

    public static class mViewHolder extends RecyclerView.ViewHolder {

        public ListView answers;
        public NumberPicker numberPicker;

        public mViewHolder(View itemView) {
            super(itemView);
            answers = itemView.findViewById(R.id.quizzcreator_listView);
            numberPicker = itemView.findViewById(R.id.answer_number_picker);
        }
    }

}

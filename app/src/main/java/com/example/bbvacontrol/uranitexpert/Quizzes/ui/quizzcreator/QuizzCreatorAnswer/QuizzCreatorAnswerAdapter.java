package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator.QuizzCreatorAnswer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.bbvacontrol.uranitexpert.Common.Models.Answer;
import com.example.bbvacontrol.uranitexpert.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAnswerAdapter extends RecyclerView.Adapter<QuizzCreatorAnswerAdapter.mViewHolder> {

    private List<Answer> answers = new ArrayList();

    private List<EditText> mAnswerTexts = new ArrayList();
    private List<RadioButton> mAnswerValidations = new ArrayList();

    public QuizzCreatorAnswerAdapter() {
        answers.add(new Answer());
        answers.add(new Answer());
//        answers.add(new Answer());
//        answers.add(new Answer());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    @Override
    public QuizzCreatorAnswerAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizzcreator_answers_item_listview, parent, false);
        QuizzCreatorAnswerAdapter.mViewHolder vh = new QuizzCreatorAnswerAdapter.mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuizzCreatorAnswerAdapter.mViewHolder holder, int position) {
        mAnswerTexts.add(holder.mAnswerText);
        mAnswerValidations.add(holder.mAnswerValidation);
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public EditText mAnswerText;
        public RadioButton mAnswerValidation;
        public mViewHolder(View itemView) {
            super(itemView);
            mAnswerText = itemView.findViewById(R.id.quizzcreator_answer_text);
            mAnswerValidation = itemView.findViewById(R.id.quizzcreator_answer_radioButton);
        }
    }

    public int addAnswers() {
        answers.add(new Answer());
        return answers.size() - 1;
//        notifyDataSetChanged();
    }

    public void removeAnswers() {
        if (answers.size() > 2) {
            answers.remove(answers.size()-1);
//            notifyDataSetChanged();
        }
    }

    public List<Answer> getAnwsers() {
        for(int i=0; i<answers.size(); i++) {
            String text = mAnswerTexts.get(i).toString();
            answers.get(i).setText(text);
        }
        return answers;
    }

    // ------- Private Methods ------
    private void validateAwnsers(String answer) {

    }


}

package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAnswerAdapter extends BaseAdapter {

    private List<Answer> answers = new ArrayList();

    public QuizzCreatorAnswerAdapter() {
        answers.add(new Answer());
        answers.add(new Answer());
    }

    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Object getItem(int i) {
        return answers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.quizzcreator_answers_item_listview, viewGroup, false);
        return v;
    }

    public void addAnswers() {
        answers.add(new Answer());
        notifyDataSetChanged();
    }

    public void removeAnswers() {
        if (answers.size() > 2) {
            answers.remove(answers.size()-1);
            notifyDataSetChanged();
        }
    }
}

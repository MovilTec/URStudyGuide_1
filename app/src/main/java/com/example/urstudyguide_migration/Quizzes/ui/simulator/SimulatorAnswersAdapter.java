package com.example.urstudyguide_migration.Quizzes.ui.simulator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;

public class SimulatorAnswersAdapter extends BaseAdapter {

    private List<Answer> mAnswers = new ArrayList();

    public SimulatorAnswersAdapter(List<Answer> answers) {
        mAnswers = answers;
    }

    @Override
    public int getCount() {
        return mAnswers.size();
    }

    @Override
    public Object getItem(int position) {
        return mAnswers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simulator_answers_listitem_item, parent, false);
        TextView answer = v.findViewById(R.id.simulator_answer_listiem_answer);
        answer.setText(mAnswers.get(position).getText());
        return v;
    }
}

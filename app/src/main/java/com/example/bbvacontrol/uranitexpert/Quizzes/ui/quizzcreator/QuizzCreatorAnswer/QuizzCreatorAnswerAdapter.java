package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator.QuizzCreatorAnswer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.example.bbvacontrol.uranitexpert.R;

public class QuizzCreatorAnswerAdapter extends BaseAdapter {
    // MARK:-
    private int count = 2;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.quizzcreator_answers_item_listview, viewGroup, false);
        return v;
    }

    public void addAnswers() {
        count++;
        notifyDataSetChanged();
    }

    public void removeAnswers() {
        if (count > 2) {
            count--;
            notifyDataSetChanged();
        }
    }
}

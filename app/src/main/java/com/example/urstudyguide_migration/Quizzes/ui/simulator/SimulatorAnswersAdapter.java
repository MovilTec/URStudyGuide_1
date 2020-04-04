package com.example.urstudyguide_migration.Quizzes.ui.simulator;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;

public class SimulatorAnswersAdapter extends BaseAdapter {

    private List<Answer> mAnswers = new ArrayList();
    private CheckBox checkBox;

    private CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            //TODO:-
            Answer answer = mAnswers.get(compoundButton.getId());
            if(answer.isCorrect()) {
                compoundButton.setBackgroundColor(Color.GREEN);
            } else {
                compoundButton.setBackgroundColor(Color.RED);
            }
        }
    };

    public SimulatorAnswersAdapter(List<Answer> answers, CompoundButton.OnCheckedChangeListener checkListener) {
        mAnswers = answers;
//        this.checkListener = checkListener;
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
        TextView answer = v.findViewById(R.id.simulator_answer_list_item_answer);
//        checkBox = v.findViewById(R.id.simulator_answer_list_item_checkBox);
//        checkBox.setId(position);
//        checkBox.setOnCheckedChangeListener(checkListener);
        answer.setText(mAnswers.get(position).getText());
        return v;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    //TODO:- Create an abstract class from which all of these ListViews Inherits
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}

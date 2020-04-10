package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;


import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAnswerAdapter extends ArrayAdapter<Answer> {

    private List<Answer> answers = new ArrayList();
    private final Context context;
    private ViewHolder holder;

    public QuizzCreatorAnswerAdapter(Context context) {
        super(context, R.layout.quizzcreator_answers_item_listview);
        this.context = context;
        answers.add(new Answer());
        answers.add(new Answer());
    }

    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Answer getItem(int i) {
        return answers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        protected CheckBox checkbox;
        protected EditText answerText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        System.out.println("getView(position=" + position + ", convertView=" + convertView + ", parent=" + viewGroup + ")" );

        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             view =    inflater.inflate(R.layout.quizzcreator_answers_item_listview, null);  //inflater.inflate(R.layout.quizzcreator_answers_item_listview, viewGroup, false);

            holder = new ViewHolder();
            holder.answerText = view.findViewById(R.id.quizzcreator_answer_text);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            view = convertView;
            EditText answerText = view.findViewById(R.id.quizzcreator_answer_text);
            answerText.setText(answers.get(position).getText());
            System.out.println("the answer text that is going to be sett: " + answers.get(position).getText());
        }
            holder.answerText.setText(answers.get(position).getText());
            holder.answerText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().isEmpty()) {
                        answers.get(position).setText(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) { }
            });
        return view;
    }

    // ******* ------- Public custom methods --------

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

    public List<Answer> getAnswersList() {
        return answers;
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

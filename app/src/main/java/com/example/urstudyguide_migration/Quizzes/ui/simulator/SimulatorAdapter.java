package com.example.urstudyguide_migration.Quizzes.ui.simulator;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;


public class SimulatorAdapter extends RecyclerView.Adapter<SimulatorAdapter.mViewHolder> {

    private List<TestItem> mQuestions;
    private List<SimulatorAnswersAdapter> answersAdapters = new ArrayList();
    private List<ListView> mAnswersLists = new ArrayList();
    private ListView mAnswersList;
    private Double grade;
    private CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            //TODO:-
//            Answer answer = mAnswers.get(compoundButton.getId());
//            if(answer.isCorrect()) {
//                compoundButton.setBackgroundColor(Color.GREEN);
//            } else {
//                compoundButton.setBackgroundColor(Color.RED);
//            }
        }
    };

    public SimulatorAdapter(List<TestItem> questions) {
        mQuestions = questions;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simulator_recyclerview_item, parent, false);
        SimulatorAdapter.mViewHolder vh = new SimulatorAdapter.mViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.question.setText(mQuestions.get(position).getQuestion());

        SimulatorAnswersAdapter mListAdapter = new SimulatorAnswersAdapter(mQuestions.get(position).getAnswers(), checkListener);
        answersAdapters.add(mListAdapter);
        holder.answers.setAdapter(mListAdapter);
//        setupListView(holder.answers, mListAdapter, mQuestions.get(position).getAnswers());

        mAnswersLists.add(holder.answers);
        mAnswersList = holder.answers;
        setListViewHeightBasedOnChildren(holder.answers);
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView question;
        public ListView answers;
        public mViewHolder(View v) {
            super(v);
            question = v.findViewById(R.id.quizzsimulator_item_question);
            answers = v.findViewById(R.id.simulator_item_listview);
            setListViewHeightBasedOnChildren(answers);
        }
    }

    //------ Public Methods --------

    public double getGrade() {
        int rawGrade = mQuestions.size();
        for(int i = 0; i < mQuestions.size(); i++) {
            List<Answer> reactiveAnswers = mQuestions.get(i).getAnswers();
            for(int j=0;j<reactiveAnswers.size();j++) {
                Answer respuesta =  mQuestions.get(i).getAnswers().get(j);
                // Getting each a
                View view = answersAdapters.get(i).getViewByPosition(j, mAnswersLists.get(i));
                final CheckBox checkBox = view.findViewById(R.id.simulator_answer_list_item_checkBox);
                final boolean isCorrectAnswer = respuesta.isCorrect();
                final boolean isChoosenAnswer = checkBox.isChecked();
                if (checkBox != null) {
                //Sí esta seleccionada la respuesta, pero no es la respuesta correcta se le resta puntos
                    if (isChoosenAnswer && !isCorrectAnswer) {
                        rawGrade--;
                    }
                }
            }
        }
        return (10.0 * rawGrade) / mQuestions.size();
    }

    public void showRightAnswers() {
        for(int i = 0; i < mQuestions.size(); i++) {
            List<Answer> reactiveAnswers = mQuestions.get(i).getAnswers();
            for(int j=0;j<reactiveAnswers.size();j++) {
                Answer respuesta =  mQuestions.get(i).getAnswers().get(j);
                // Getting each a
                View view = answersAdapters.get(i).getViewByPosition(j, mAnswersLists.get(i));
                final CheckBox checkBox = view.findViewById(R.id.simulator_answer_list_item_checkBox);
                final boolean isCorrectAnswer = respuesta.isCorrect();
                final boolean isChoosenAnswer = checkBox.isChecked();
                if (checkBox != null) {
                    //Sí esta seleccionada la respuesta, pero no es la respuesta correcta se le resta puntos
                    if(isCorrectAnswer)
                        checkBox.setBackgroundColor(Color.GREEN);
                    if (isChoosenAnswer && !isCorrectAnswer) {
                        checkBox.setBackgroundColor(Color.RED);
                    }
                }
            }
        }
    }

    // -------  Private Methods ---------

    private void setupListView(ListView listView, SimulatorAnswersAdapter adapter, List<Answer> answers) {
        for(int i=0;i<answers.size();i++) {
            final Answer answer = answers.get(i);
            final View view = adapter.getViewByPosition(i, listView);

            final TextView textView = view.findViewById(R.id.simulator_answer_list_item_answer);
            final CheckBox checkBox = view.findViewById(R.id.simulator_answer_list_item_checkBox);

            textView.setText(answer.getText());
        }
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight() + 24;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}

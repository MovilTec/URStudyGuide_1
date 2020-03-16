package com.example.urstudyguide_migration.Quizzes.ui.simulator;

import android.content.Context;
import android.icu.lang.UCharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.R;

import java.util.List;


public class SimulatorAdapter extends RecyclerView.Adapter<SimulatorAdapter.mViewHolder> {

    private List<TestItem> mQuesions;

    public SimulatorAdapter(List<TestItem> questions) {
        mQuesions = questions;
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
        holder.question.setText(mQuesions.get(position).getQuestion());

//        ViewGroup.LayoutParams params = holder.answers.getLayoutParams();
//        int totalHeight = 0;
//        for(int i=0; i < mQuesions.get(position).getAnswers().size(); i++) {
//            totalHeight += 100;
//        }
//        params.height = totalHeight;
//        holder.answers.setLayoutParams(params);

        BaseAdapter mListAdapter = new SimulatorAnswersAdapter(mQuesions.get(position).getAnswers());
        holder.answers.setAdapter(mListAdapter);

        setListViewHeightBasedOnChildren(holder.answers);
    }

    @Override
    public int getItemCount() {
        return mQuesions.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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

    public void getTestItems() {
        for(int i=0; i < mQuesions.size(); i++) {

        }
    }

    // -------  Private Methods ---------

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

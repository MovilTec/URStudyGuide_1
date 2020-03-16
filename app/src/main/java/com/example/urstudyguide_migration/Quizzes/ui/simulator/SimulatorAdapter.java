package com.example.urstudyguide_migration.Quizzes.ui.simulator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        }
    }
}

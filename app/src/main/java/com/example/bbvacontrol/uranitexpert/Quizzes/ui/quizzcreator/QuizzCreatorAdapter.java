package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bbvacontrol.uranitexpert.Common.Models.TestItem;
import com.example.bbvacontrol.uranitexpert.Questions;
import com.example.bbvacontrol.uranitexpert.R;

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

    }

    @Override
    public int getItemCount() {
        return testItems.size();
    }

    public int addQuestions() {
        testItems.add("New Item");
        return testItems.size() - 1;
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {


        public mViewHolder(View itemView) {
            super(itemView);
        }
    }

}

package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class QuizzCreatorPrubeAdatper extends RecyclerView.Adapter<QuizzCreatorPrubeAdatper.mViewHolder> {

    private List<TestItem> testItems = new ArrayList();
    private View.OnClickListener onClickListener;
    private QuestionCreatable questionCreatable;

    public QuizzCreatorPrubeAdatper(QuestionCreatable questionCreatable, List<TestItem> testItems) {
        //TODO:- Settup the intial test text?
//        TestItem testItem = new TestItem();
//        testItem.setQuestion("Question No. 1");
//        this.testItems.add(testItem);
        this.testItems = testItems;
        this.questionCreatable = questionCreatable;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizzcreator_prube_item, parent, false);
        QuizzCreatorPrubeAdatper.mViewHolder vh = new QuizzCreatorPrubeAdatper.mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.textView.setTag(position);
        holder.cardView.setTag(position);


        holder.textView.setText(testItems.get(position).getQuestion());
        holder.cardView.setOnClickListener(v -> {
            questionCreatable.onQuestionSelected(position);
        });
    }

    @Override
    public int getItemCount() {
        return testItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textView;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.quizzcreator_prube__cardView);
            textView = itemView.findViewById(R.id.quizzcreator_prube_answer_text);
        }
    }

    public int addQuestions() {
        TestItem testItem = new TestItem();
        int questionNo = testItems.size() + 1;
//        testItem.setQuestion("Question No. " + questionNo);
        testItems.add(testItem);
        return testItems.size() - 1;
    }

    public int removeQuestions() {
        testItems.remove(testItems.size() - 1);
        return testItems.size() - 1;
    }

    public void updateTestItem(int position, TestItem testItem) {
        testItems.set(position, testItem);
    }

    public interface QuestionCreatable {
        void onQuestionSelected(int position);
    }
}

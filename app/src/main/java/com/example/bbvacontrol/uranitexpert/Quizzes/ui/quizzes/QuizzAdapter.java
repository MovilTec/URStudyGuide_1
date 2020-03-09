package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bbvacontrol.uranitexpert.Common.Models.Quizz;
import com.example.bbvacontrol.uranitexpert.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.mViewHolder> {

    private List<Quizz> mQuizzes;
    private List<TextView> mViews = new ArrayList();
    private QuizzItemAction action;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class mViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public mViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.quizzTitle);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuizzAdapter(List<Quizz> myDataset, QuizzItemAction action) {
        mQuizzes = myDataset;
        this.action = action;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public QuizzAdapter.mViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_quizz_recycled_view, parent, false);
        mViewHolder vh = new QuizzAdapter.mViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mQuizzes.get(position).getName());
        holder.textView.setOnClickListener(onQuizzClick);
        holder.textView.setId(position);
        mViews.add(holder.textView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mQuizzes.size();
    }

    public interface QuizzItemAction {
        void onQuizzItemSelected(Quizz item);
    }

    // -------- Private Methods --------
    private View.OnClickListener onQuizzClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for(int i=0;i<mViews.size();i++) {
                if (view.getId() == mViews.get(i).getId()) {
                    action.onQuizzItemSelected(mQuizzes.get(i));
                }
            }
        }
    };

}

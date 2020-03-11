package com.example.urstudyguide_migration.Quizzes.ui.quizzes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.R;

import java.util.List;

public class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.mViewHolder> {

    private List<Quizz> mDataset;
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
        mDataset = myDataset;
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
        holder.textView.setText(mDataset.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface QuizzItemAction {
        void onQuizzItemSelected(Quizz item);
    }

}

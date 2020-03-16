package com.example.urstudyguide_migration.Quizzes.ui.quizzes;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseRealtimeDatabaseReponse;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.mViewHolder> {

    private static List<Quizz> mQuizzes;
    private static List<CardView> mViews = new ArrayList();
    private static QuizzItemAction action;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class mViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView quizzName;
        public CardView cardView;
        public TextView quizzAuthor;
        public ClipData.Item currentItem;

        public mViewHolder(View v) {
            super(v);
            v.setOnClickListener(onQuizzClick);
            cardView = v.findViewById(R.id.quizzAdapter_cardView);
            quizzName = v.findViewById(R.id.quizzTitle);
            quizzAuthor = v.findViewById(R.id.quizzAuthor);
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
        holder.quizzName.setText(mQuizzes.get(position).getName());
        mQuizzes.get(position).getAuthorName(holder.quizzAuthor);
        mViews.add(holder.cardView);
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
    private static View.OnClickListener onQuizzClick = (view ->  {
        for(int i=0;i<mViews.size();i++) {
            if (view.getVerticalScrollbarPosition() == mViews.get(i).getVerticalScrollbarPosition()) {
//            if (view.getId() == mViews.get(i).getId()) {
                action.onQuizzItemSelected(mQuizzes.get(i));
            }
        }
    });

}

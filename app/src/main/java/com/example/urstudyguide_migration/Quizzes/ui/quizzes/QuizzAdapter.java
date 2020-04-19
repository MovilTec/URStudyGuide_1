package com.example.urstudyguide_migration.Quizzes.ui.quizzes;

import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.Common.Helpers.FirebaseRealtimeDatabaseReponse;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.mViewHolder> {

    private static HashMap<String, Quizz> mQuizzes;
    private static List<Quizz> mQuizzesList;
    private static List<CardView> mViews = new ArrayList();
    private static QuizzItemAction action;

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuizzAdapter(Map<String, Quizz> myDataset, QuizzItemAction action) {
        mQuizzes = (HashMap<String, Quizz>) myDataset;
        mQuizzesList = new ArrayList(mQuizzes.values());
        this.action = action;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public QuizzAdapter.mViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_quizz_recycled_view, parent, false);
        mViewHolder vh = new QuizzAdapter.mViewHolder(v, action);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Quizz quizz = mQuizzesList.get(position);
        holder.quizzName.setText(quizz.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            FirebaseManager firebaseManager = new FirebaseManager(FirebaseDatabase.getInstance());
            String path = "Users/" + quizz.getAuthor() + "/name";
            CompletableFuture<DataSnapshot> completableFuture = firebaseManager.read(path);

                // Concurrency load
                completableFuture.handle((dataSnapshot, throwable) -> {
                    // This is called!
                    String Name = (String) dataSnapshot.getValue();
                    holder.quizzAuthor.setText(Name);
                    return throwable;
                });

        } else {
            quizz.getAuthorName(holder.quizzAuthor);
        }
        mViews.add(holder.cardView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mQuizzes.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class mViewHolder extends RecyclerView.ViewHolder {
        public TextView quizzName, quizzAuthor;
        public CardView cardView;
        QuizzItemAction quizzItemAction;

        public mViewHolder(View v, QuizzItemAction quizzItemAction) {
            super(v);
            cardView = v.findViewById(R.id.quizzAdapter_cardView);
            cardView.setOnClickListener((view) -> {
                action.onQuizzItemSelected(getAdapterPosition());
            });
            quizzName = v.findViewById(R.id.quizzTitle);
            quizzAuthor = v.findViewById(R.id.quizzAuthor);
            this.quizzItemAction = quizzItemAction;
        }
    }

    public interface QuizzItemAction {
        void onQuizzItemSelected(int position);
    }

    // --- Public Methods --- //
    public Quizz getQuizz(int position) {
        return mQuizzesList.get(position);
    }
    public String getQuizzId(int position) {
        List<String> keys = new ArrayList(mQuizzes.keySet());
        return keys.get(position);
    }
}

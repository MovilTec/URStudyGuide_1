package com.example.urstudyguide_migration.Quizzes.ui.quizzattempts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAdapter;
import com.example.urstudyguide_migration.R;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuizzAttemptsAdapter extends RecyclerView.Adapter<QuizzAttemptsAdapter.mViewHolder> {

    private List<QuizzAttempt> attempts = new ArrayList();
    public QuizzAttemptsAdapter(List<QuizzAttempt> attempts) {
        this.attempts = attempts;
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public TextView name, grade, date;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.quizzattempts_name_textView);
            grade = itemView.findViewById(R.id.quizzattempts_grade_textView);
            date = itemView.findViewById(R.id.quizzattempts_date_textView);
        }
    }

    @NonNull
    @Override
    public QuizzAttemptsAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizzattempts_item_recyclerview, parent, false);
        QuizzAttemptsAdapter.mViewHolder vh = new QuizzAttemptsAdapter.mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizzAttemptsAdapter.mViewHolder holder, int position) {
//        holder.name.setText(attempts.get(position).getStudent());
        attempts.get(position).setStudentName(holder.name);
        holder.grade.setText("Grade: " + String.valueOf(attempts.get(position).getGrade()));

        holder.date.setText("Date: " +attempts.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return attempts.size();
    }
}

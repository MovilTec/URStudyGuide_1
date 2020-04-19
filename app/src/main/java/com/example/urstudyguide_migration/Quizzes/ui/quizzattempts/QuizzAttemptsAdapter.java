package com.example.urstudyguide_migration.Quizzes.ui.quizzattempts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAdapter;
import com.example.urstudyguide_migration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.CompletableFuture;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuizzAttemptsAdapter extends RecyclerView.Adapter<QuizzAttemptsAdapter.mViewHolder> {

//    private List<QuizzAttempt> attempts = new ArrayList();
    private Map<String, List<QuizzAttempt>> attempts;
    private ArrayList<String> students_id;

    public QuizzAttemptsAdapter(Map<String, List<QuizzAttempt>> attempts) {
        this.attempts = attempts;
        students_id = new ArrayList(attempts.keySet());
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public TextView name, grade;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.quizzattempts_name_textView);
            grade = itemView.findViewById(R.id.quizzattempts_grade_textView);
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

        String student_id = students_id.get(position);
        FirebaseManager firebaseManager = new FirebaseManager(FirebaseDatabase.getInstance());
        String path = "Users/" + student_id + "/name";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<DataSnapshot> completableFuture = firebaseManager.read(path);
            completableFuture.thenAccept(dataSnapshot -> {
                String student_name = dataSnapshot.getValue().toString();
                holder.name.setText(student_name);
            });

            List<QuizzAttempt> student_attempts = attempts.get(student_id);
            String average_grade = "average grade: ";
            double total_grade = 0.0;
            for(QuizzAttempt attempt: student_attempts) {
                total_grade += attempt.getGrade();
            }
            double average = (total_grade / student_attempts.size());
            average_grade += average;
            holder.grade.setText(average_grade);
        } else {
//            students_id.get(position).setStudentName(holder.name);
        }
//        holder.grade.setText("Grade: " + String.valueOf(attempts.get(position).getGrade()));
//
//        holder.date.setText("Date: " +attempts.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return attempts.size();
    }
}

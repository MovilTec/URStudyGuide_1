package com.example.urstudyguide_migration.Quizzes.ui.quizzattempts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.Common.Models.QuizzAttempt;
import com.example.urstudyguide_migration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class QuizzAttemptsExpandableAdapter extends ExpandableRecyclerViewAdapter<StudentAverage, StudentGradeDetail> {

    private DecimalFormat decimalFormat = new DecimalFormat("#.#");

    public QuizzAttemptsExpandableAdapter(List<Student> students) {
        super(students);
    }

    @Override
    public StudentAverage onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizzattempts_item_recyclerview, parent, false);
        return new StudentAverage(view);
    }

    @Override
    public StudentGradeDetail onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizzattempts_item_expandable_recyclerview, parent, false);
        return new StudentGradeDetail(view);
    }

    @Override
    public void onBindChildViewHolder(StudentGradeDetail holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final QuizzAttempt quizzAttempt = (QuizzAttempt) group.getItems().get(childIndex);
        holder.onBind(quizzAttempt);
    }

    @Override
    public void onBindGroupViewHolder(StudentAverage holder, int flatPosition, ExpandableGroup group) {
        holder.setStudent_name(group);
    }
}


class StudentAverage extends GroupViewHolder {
    public TextView student_name, student_average_grade;
    private ImageView arrow;
    public StudentAverage(View itemView) {
        super(itemView);
        student_name = itemView.findViewById(R.id.quizzattempts_name_textView);
        student_average_grade = itemView.findViewById(R.id.quizzattempts_grade_textView);
        arrow = itemView.findViewById(R.id.quizzattempts_arrow_imageView);
    }

    public void setStudent_name(ExpandableGroup group) {
        FirebaseManager firebaseManager = new FirebaseManager(FirebaseDatabase.getInstance());
        String path = "Users/" + group.getTitle() + "/name";
        ArrayList<QuizzAttempt> student_attempts = (ArrayList<QuizzAttempt>) group.getItems();

        CompletableFuture<DataSnapshot> promise = firebaseManager.read(path);
        promise.thenAccept(dataSnapshot -> {
            String studentName = dataSnapshot.getValue().toString();
            student_name.setText(studentName);
        });

        // Setting the grade average
        String average_grade = "average grade: ";
        double total_grade = 0.0;
        for(QuizzAttempt attempt: student_attempts) {
            total_grade += attempt.getGrade();
        }
        double average = (total_grade / student_attempts.size());
        average_grade += new DecimalFormat("#.#").format(average);
        student_average_grade.setText(average_grade);
    }

    @Override
    public void expand() {
        super.expand();
        animateExpand();
    }

    @Override
    public void collapse() {
        super.collapse();
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}

class StudentGradeDetail extends ChildViewHolder {
    public TextView student_grade, attempt_date;
    public StudentGradeDetail(View itemView) {
        super(itemView);
        student_grade = itemView.findViewById(R.id.quizzattempts_expandable_grade_textview);
        attempt_date = itemView.findViewById(R.id.quizzattempts_expandable_date_textview);
    }

    public void onBind(QuizzAttempt quizzAttempt) {
        student_grade.setText("Grade: " + new DecimalFormat("#.#").format(quizzAttempt.getGrade()));
        attempt_date.setText("Date: " + quizzAttempt.getDate());
    }
}

class Student extends ExpandableGroup<QuizzAttempt> {

    public Student(String name, List<QuizzAttempt> items) {
        super(name, items);
    }
}
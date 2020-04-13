package com.example.urstudyguide_migration.Quizzes.ui.quizzes;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreator;
import com.example.urstudyguide_migration.Quizzes.QuizzDetail;
import com.example.urstudyguide_migration.Quizzes.navigators.QuizzNavigator;
import com.example.urstudyguide_migration.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

import io.reactivex.annotations.NonNull;

public class QuizzesFragment extends Fragment implements QuizzNavigator {

    private QuizzesViewModel mViewModel;
    private RecyclerView recyclerView;
    private QuizzAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton quizzButton;

    public static QuizzesFragment newInstance() {
        return new QuizzesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizzes_fragment, container, false);
        recyclerView = view.findViewById(R.id.quizzes_recycler_view);
        quizzButton = view.findViewById(R.id.quizz_floatingActionButton);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzesViewModel.class);
        mViewModel.navigator = this;
        mViewModel.getAvilableQuizzes();
        setupRecyclerView();
        setupQuizzButton();
    }

    private void setupRecyclerView() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupQuizzButton() {
        quizzButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), QuizzCreator.class);
            startActivity(intent);
        });
    }

    private void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataRetrieve(Map<String, Quizz> items) {
        if (items.size() > 0) {
            mAdapter = new QuizzAdapter(items, position -> {
                Intent intent = new Intent(getContext(), QuizzDetail.class);
                Quizz item = mAdapter.getQuizz(position);
                String quizzId = mAdapter.getQuizzId(position);
                intent.putExtra("Quizz", item);
                intent.putExtra("quizzId", quizzId);
                startActivity(intent);
            });
            recyclerView.setAdapter(mAdapter);
            return;
        }
        showErrorMessage("No Existen Questionarios por el momento");
    }

    @Override
    public void onQuizzCreated() {
        // TODO:- Refesh the view?
    }
}

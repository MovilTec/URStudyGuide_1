package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bbvacontrol.uranitexpert.Common.Models.Quizz;
import com.example.bbvacontrol.uranitexpert.Quizzes.QuizzAdapter;
import com.example.bbvacontrol.uranitexpert.Quizzes.QuizzNavigator;
import com.example.bbvacontrol.uranitexpert.R;

import java.util.List;

public class QuizzesFragment extends Fragment implements QuizzNavigator {

    private QuizzesViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static QuizzesFragment newInstance() {
        return new QuizzesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizzes_fragment, container, false);
        recyclerView = view.findViewById(R.id.quizzes_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzesViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getAvilableQuizzes();
        setupView();
    }

    void setupView() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataRetrieve(List<Quizz> items) {
        // specify an adapter (see also next example)
        if (items.size() > 0) {
            mAdapter = new QuizzAdapter(items);
            recyclerView.setAdapter(mAdapter);
            return;
        }
        showErrorMessage("No Existen Questionarios por el momento");
    }

    @Override
    public void onQuizzCreated() {

    }
}

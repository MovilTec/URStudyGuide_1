package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Helpers.AlertableFragment;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.QuizzCreatorNavigator;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

public class QuizzCreatorFragment extends AlertableFragment implements QuizzCreatorNavigator {

    private QuizzCreatorViewModel mViewModel;
    private NumberPicker mNumberPicker;
    private RecyclerView mRecyclerView;
    private QuizzCreatorAdapter mAdapter;

    public static QuizzCreatorFragment newInstance() {
        return new QuizzCreatorFragment();
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.quizz_creator_fragment, container, false);
//        mNumberPicker = view.findViewById(R.id.number_picker);
//        mRecyclerView = view.findViewById(R.id.quizzcreator_recyclerView);
//        return view;
//    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_creator_fragment, parent, false);
        mNumberPicker = view.findViewById(R.id.number_picker);
        mRecyclerView = view.findViewById(R.id.quizzcreator_recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzCreatorViewModel.class);
        setupNumberPicker();
        setupRecyclerView();
    }

    private void setupNumberPicker() {

        mNumberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                switch (action) {
                    case INCREMENT:
                        mAdapter.notifyItemInserted(mAdapter.addQuestions());
                        break;
                    case DECREMENT:
                        mAdapter.notifyItemRemoved(mAdapter.removeQuestions());
                        break;
                }
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new QuizzCreatorAdapter(new QuizzCreatorAdapter.QuizzCreatorHandler() {

            @Override
            public void onCreateAction(List<TestItem> testItems) {
//                mViewModel.createQuizz(testItems);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreatedQuizz() {
        //TODO:- Something when quizz created
    }


    public void onError(String error) {

    }
}

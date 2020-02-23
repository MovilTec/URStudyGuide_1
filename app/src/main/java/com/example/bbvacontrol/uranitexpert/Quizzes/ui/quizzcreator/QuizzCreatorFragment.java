package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator;

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

import com.example.bbvacontrol.uranitexpert.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

public class QuizzCreatorFragment extends Fragment {

    private QuizzCreatorViewModel mViewModel;
    private NumberPicker mNumberPicker;
    private RecyclerView mRecyclerView;
    private QuizzCreatorAdapter mAdapter;

    private int mCounter = 1;

    public static QuizzCreatorFragment newInstance() {
        return new QuizzCreatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_creator_fragment, container, false);
        mNumberPicker = view.findViewById(R.id.number_picker);
        mRecyclerView = view.findViewById(R.id.quizzcreator_recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzCreatorViewModel.class);
        // TODO: Use the ViewModel
        setupNumberPicker();
        setupRecyclerView();
    }

    private void setupNumberPicker() {

//        mNumberPicker.setMax(15);
//        mNumberPicker.setMin(1);
//        mNumberPicker.setUnit(2);

        mNumberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                switch (action) {
                    case INCREMENT:
                        mAdapter.notifyItemInserted(mAdapter.addQuestions());
                        break;
                    case DECREMENT:
                        break;
                }
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new QuizzCreatorAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

}

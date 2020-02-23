package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bbvacontrol.uranitexpert.R;

public class QuizzCreatorFragment extends Fragment {

    private QuizzCreatorViewModel mViewModel;

    public static QuizzCreatorFragment newInstance() {
        return new QuizzCreatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quizz_creator_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizzCreatorViewModel.class);
        // TODO: Use the ViewModel
    }

}

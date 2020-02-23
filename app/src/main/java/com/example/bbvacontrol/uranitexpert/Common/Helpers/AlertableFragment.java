package com.example.bbvacontrol.uranitexpert.Common.Helpers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

interface Alertable {
    void displayErrorMessage(String error);
}

public abstract class AlertableFragment extends Fragment implements Alertable {


    public AlertableFragment newInstance()
    {

        Bundle args = new Bundle();
        // args.putInt(ARG_PAGE, page);
        AlertableFragment fragment = provideYourFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public abstract AlertableFragment provideYourFragment();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = provideYourFragmentView(inflater,container,savedInstanceState);
        return view;
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState);


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void displayErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}

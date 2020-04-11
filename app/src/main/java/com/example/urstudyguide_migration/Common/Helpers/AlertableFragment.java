package com.example.urstudyguide_migration.Common.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

interface Alertable {
    void displayErrorMessage(String error);
}

public abstract class AlertableFragment extends Fragment implements Alertable {

    private Context mContext;
//    public AlertableFragment newInstance()
//    {
//
//        Bundle args = new Bundle();
//        // args.putInt(ARG_PAGE, page);
//        AlertableFragment fragment = provideYourFragment();
//        fragment.setArguments(args);
//        return fragment;
//
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = provideYourFragmentView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Activity getNonNullActivity() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            return activity;
        }
        return getActivity();
    }

    public void displayErrorMessage(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Error")
                .setTitle(error);

        AlertDialog dialog = builder.create();
        dialog.show();
//        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}

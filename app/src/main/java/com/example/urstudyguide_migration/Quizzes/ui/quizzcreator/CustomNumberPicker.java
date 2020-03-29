package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import androidx.recyclerview.widget.RecyclerView;

//TODO:- Make a refactor of this class. This is not good pratices!
public class CustomNumberPicker {

    private QuizzCreatorAnswerAdapter listAdapter;
    private ListView listView;
    private static int listItemHeight = 0;

    private ValueChangedListener onValueChange = new ValueChangedListener() {
        @Override
        public void valueChanged(int value, ActionEnum action) {
            switch(action) {
                case INCREMENT:
                    listAdapter.addAnswers();
                    setListViewHeightBasedOnChildren(listView);
                    changeParentHeight((View) listView.getParent(), listItemHeight);
                    break;
                case DECREMENT:
                    listAdapter.removeAnswers();
                    setListViewHeightBasedOnChildren(listView);
                    changeParentHeight((View) listView.getParent(), -listItemHeight);
                    break;
            }
        }
    };

    public void setup(QuizzCreatorAnswerAdapter listAdapter, ListView listView, NumberPicker numberPicker) {
        this.listAdapter = listAdapter;
        this.listView = listView;
        numberPicker.setValueChangedListener(onValueChange);
    }

    private void changeParentHeight(View view, int listViewHeight) {
        int totalHeight = 0;
        totalHeight += view.getMeasuredHeight() + listViewHeight;
        ViewGroup.LayoutParams params =  view.getLayoutParams();
        params.height = totalHeight;
        view.setLayoutParams(params);
        view.requestLayout();
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    private static int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return -1;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            listItemHeight = view.getMeasuredHeight();
            totalHeight += listItemHeight;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }
}

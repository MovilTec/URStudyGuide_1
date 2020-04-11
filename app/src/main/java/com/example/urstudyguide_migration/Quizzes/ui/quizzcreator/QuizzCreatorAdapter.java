package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.Answer;
import com.example.urstudyguide_migration.Common.Models.TestItem;
import com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.QuizzCreatorAnswer.QuizzCreatorAnswerAdapter;
import com.example.urstudyguide_migration.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAdapter extends RecyclerView.Adapter<QuizzCreatorAdapter.mViewHolder> {

    private List<TestItem> testItems = new ArrayList();
    private List<QuizzCreatorAnswerAdapter> answerAdapters = new ArrayList();
    private QuizzCreatorHandler mQuizzCreatorHandler;
    private List<EditText> mQuestions = new ArrayList();

    public QuizzCreatorAdapter(QuizzCreatorHandler createQuizzAction) {
        TestItem testItem = new TestItem();
        testItems.add(testItem);
        mQuizzCreatorHandler = createQuizzAction;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizz_creator_item_adapater, parent, false);
        QuizzCreatorAdapter.mViewHolder vh = new QuizzCreatorAdapter.mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        mQuestions.add(holder.quizzQuestion);
    }

    @Override
    public int getItemCount() {
        return testItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    // ---------- Public class methods --------------

    public int addQuestions() {
        TestItem testItem = new TestItem();
        testItems.add(testItem);
        return testItems.size() - 1;
    }

    public int removeQuestions() {
        testItems.remove(testItems.size() - 1);
        return testItems.size() - 1;
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        public NumberPicker numberPicker;
        public Button quizzCreatorButton;
        public EditText quizzQuestion;

        public mViewHolder(View itemView) {
            super(itemView);
            numberPicker = itemView.findViewById(R.id.answer_number_picker);
            quizzCreatorButton = itemView.findViewById(R.id.quizzcreator_button);
            quizzQuestion = itemView.findViewById(R.id.quizzcreator_question);
        }
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight() + 24;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // -------- Interface Comunication --------
    public interface QuizzCreatorHandler {
        void onErrorMessage(String errorMessage);
    }
}


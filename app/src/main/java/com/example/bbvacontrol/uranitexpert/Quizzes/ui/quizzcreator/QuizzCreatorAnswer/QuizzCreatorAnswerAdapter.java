package com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzcreator.QuizzCreatorAnswer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bbvacontrol.uranitexpert.Common.Models.Answer;
import com.example.bbvacontrol.uranitexpert.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzCreatorAnswerAdapter extends RecyclerView.Adapter<QuizzCreatorAnswerAdapter.mViewHolder> {

    private List<Answer> answers;

    private Boolean isThereTrueAwnser = false;
    private int trueAnswerIndex = -1;

    private List<EditText> mAnswerTexts = new ArrayList();
    private List<RadioButton> mAnswerValidations = new ArrayList();

    public QuizzCreatorAnswerAdapter(List<Answer> answers) {
       this.answers = answers;
//        answers.add(new Answer());
//        answers.add(new Answer());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    @Override
    public QuizzCreatorAnswerAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizzcreator_answers_item_listview, parent, false);
        QuizzCreatorAnswerAdapter.mViewHolder vh = new QuizzCreatorAnswerAdapter.mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuizzCreatorAnswerAdapter.mViewHolder holder, int position) {
        mAnswerTexts.add(holder.mAnswerText);
        mAnswerValidations.add(holder.mAnswerValidation);
        holder.mAnswerValidation.setOnCheckedChangeListener(onAnswerSetToTrue);
        holder.mAnswerValidation.setId(position);
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public EditText mAnswerText;
        public RadioButton mAnswerValidation;
        public mViewHolder(View itemView) {
            super(itemView);
            mAnswerText = itemView.findViewById(R.id.quizzcreator_answer_text);
            mAnswerValidation = itemView.findViewById(R.id.quizzcreator_answer_radioButton);
        }
    }

    public int addAnswers() {
        answers.add(new Answer());
        return answers.size() - 1;
    }

    public void removeAnswers() {
        if (answers.size() > 2) {
            answers.remove(answers.size()-1);
//            notifyDataSetChanged();
        }
    }

    public List<Answer> getAnwsers() {
        for(int i=0; i<answers.size(); i++) {
            try {
                String text = validateAwnsers(mAnswerTexts.get(i));
                answers.get(i).setText(text);
            } catch (InvalidAwnserException e) {
                //TODO:- Handle the exception

            }
        }
        return answers;
    }

    // ------- Private Methods ------
    private String validateAwnsers(EditText editText) throws InvalidAwnserException {
        String awnser = editText.getText().toString();
        if (!awnser.isEmpty()) {
            return awnser;
        }
        throw new InvalidAwnserException("");
    }

    private CompoundButton.OnCheckedChangeListener onAnswerSetToTrue = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            setTrueAwnser(compoundButton.getId());
        }
    };

    private void setTrueAwnser(int buttonId) {
        if (!isThereTrueAwnser) {
            isThereTrueAwnser = true;
            for(int i=0; i<answers.size(); i++) {
                if (mAnswerValidations.get(i).getId() == buttonId) {
                    answers.get(i).setCorrect(true);
                    trueAnswerIndex = i;
                    break;
                }
            }
        } else {
            if (mAnswerValidations.get(trueAnswerIndex).getId() != buttonId) {
                answers.get(trueAnswerIndex).setCorrect(false);
                mAnswerValidations.get(trueAnswerIndex).setChecked(false);
                for (int i = 0; i < answers.size(); i++) {
                    if (mAnswerValidations.get(i).getId() == buttonId) {
                        answers.get(i).setCorrect(true);
                        trueAnswerIndex = i;
                        break;
                    }
                }
            }
        }

    }
}

class InvalidAwnserException extends Exception {
    public InvalidAwnserException (String errorMessage) {
        super(errorMessage);
    }
}

class NotTrueAnswerSelectedException extends Exception {
    public NotTrueAnswerSelectedException () {
        super();
    }
}

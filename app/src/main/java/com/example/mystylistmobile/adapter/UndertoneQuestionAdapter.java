package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.model.UndertoneQuestion;

import java.util.List;

public class UndertoneQuestionAdapter extends ArrayAdapter<UndertoneQuestion> {

    public UndertoneQuestionAdapter(@NonNull Context context, List<UndertoneQuestion> undertoneQuestions) {
        super(context, 0, undertoneQuestions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.undertone_question_item, parent, false);
        }

        UndertoneQuestion undertoneQuestion = getItem(position);
        TextView questionTxt = listItemView.findViewById(R.id.questionText);
        TextView coolOption = listItemView.findViewById(R.id.coolOption);
        TextView warmOption = listItemView.findViewById(R.id.warmOption);
        TextView neutralOption = listItemView.findViewById(R.id.neutralOption);

        questionTxt.setText(undertoneQuestion.getQuestion());
        coolOption.setText("Cool option: " + undertoneQuestion.getCoolOption());
        warmOption.setText("Warm option: "+ undertoneQuestion.getWarmOption());
        neutralOption.setText("Neutral option: " + undertoneQuestion.getNeutralOption());

        return listItemView;
    }
}

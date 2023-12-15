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
import com.example.mystylistmobile.dto.response.UserResponseDTO;
import com.example.mystylistmobile.model.ContrastQuestion;

import java.util.List;

public class ContrastQuestionAdapter extends ArrayAdapter<ContrastQuestion> {

    public ContrastQuestionAdapter(@NonNull Context context, List<ContrastQuestion> contrastQuestions) {
        super(context, 0, contrastQuestions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.contrast_question_item, parent, false);
        }

        ContrastQuestion contrastQuestion = getItem(position);
        TextView questionText = listItemView.findViewById(R.id.questionText);
        TextView highOption = listItemView.findViewById(R.id.highOption);
        TextView lowOption = listItemView.findViewById(R.id.lowOption);
        TextView mediumOption = listItemView.findViewById(R.id.mediumOption);

        questionText.setText(contrastQuestion.getQuestion());
        highOption.setText("High option: " + contrastQuestion.getHighOption());
        lowOption.setText("Low option: " + contrastQuestion.getLowOption());
        mediumOption.setText("Medium option: " + contrastQuestion.getMediumOption());

        return listItemView;
    }
}

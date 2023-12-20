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
import com.example.mystylistmobile.model.StyleQuestion;
import com.example.mystylistmobile.model.UndertoneQuestion;

import java.util.List;

public class StyleTypeQuestionAdapter extends ArrayAdapter<StyleQuestion> {
    public StyleTypeQuestionAdapter(@NonNull Context context, List<StyleQuestion> styleQuestions) {
        super(context, 0, styleQuestions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.style_type_question_item, parent, false);
        }

        StyleQuestion styleQuestion = getItem(position);
        TextView questionTxt = listItemView.findViewById(R.id.questionText);
        TextView classicOption = listItemView.findViewById(R.id.classicOption);
        TextView naturalOption = listItemView.findViewById(R.id.naturalOption);
        TextView sexyOption = listItemView.findViewById(R.id.sexyOption);
        TextView dramaticOption = listItemView.findViewById(R.id.dramaticOption);
        TextView feminineOption = listItemView.findViewById(R.id.feminineOption);
        TextView elegantOption = listItemView.findViewById(R.id.elegantOption);


        questionTxt.setText(styleQuestion.getQuestion());
        classicOption.setText("Classic option: " + styleQuestion.getClassicOption());
        naturalOption.setText("Natural option: "+ styleQuestion.getNaturalOption());
        sexyOption.setText("Sexy option: "+ styleQuestion.getSexyOption());
        dramaticOption.setText("Dramatic option: "+ styleQuestion.getDramaticOption());
        feminineOption.setText("Feminine option: "+ styleQuestion.getFeminineOption());
        elegantOption.setText("Elegant option: "+ styleQuestion.getElegantOption());

        return listItemView;
    }
}

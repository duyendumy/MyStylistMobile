package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.model.Color;

import java.util.ArrayList;

public class ColorAdapter extends ArrayAdapter<Color> {
    public ColorAdapter(@NonNull Context context, ArrayList<Color> colors) {
        super(context, 0, colors);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.color_item, parent, false);
        }

        Color color = getItem(position);
        TextView codeTxt = listItemView.findViewById(R.id.codeTxt);
        ImageView colorImage = listItemView.findViewById(R.id.colorImage);

        codeTxt.setText(color.getCode());
        colorImage.setImageResource(color.getImgid());
        return listItemView;
    }
}

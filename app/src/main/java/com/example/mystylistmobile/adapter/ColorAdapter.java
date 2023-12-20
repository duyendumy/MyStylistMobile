package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.mystylistmobile.R;

import java.util.List;

public class ColorAdapter extends BaseAdapter {

    private List<String> colors;

    private Context context;
    public ColorAdapter(@NonNull Context context, List<String> colors) {

        this.context = context;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int i) {
       return colors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
        }

        String color = (String) getItem(position);
        TextView codeTxt = listItemView.findViewById(R.id.codeTxt);
        CardView cardView= listItemView.findViewById(R.id.colorCard);

        codeTxt.setText(color);
        int backgroundColor = parseColorSafely(color);
        cardView.setCardBackgroundColor(backgroundColor);


        return listItemView;
    }
    private int parseColorSafely(String color) {
        try {
            return Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            // If the initial parsing fails, try parsing without the # symbol
            try {
                return Color.parseColor(color.substring(1));
            } catch (Exception e2) {
                e2.printStackTrace();
                // If parsing without # symbol also fails, return a default color
                return Color.GRAY;
            }
        }
    }
}

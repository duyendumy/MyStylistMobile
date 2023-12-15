package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.activity.LoginActivity;
import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.model.Category;
import com.example.mystylistmobile.model.Color;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryResponseDTO> {

    public CategoryAdapter(@NonNull Context context, List<CategoryResponseDTO> categories) {
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }

        CategoryResponseDTO category = getItem(position);
        TextView nameTxt = listItemView.findViewById(R.id.listName);
        ImageView categoryImage = listItemView.findViewById(R.id.listImage);

        nameTxt.setText(category.getName());
        categoryImage.setImageResource(R.drawable.icon_app);

        return listItemView;
    }
}

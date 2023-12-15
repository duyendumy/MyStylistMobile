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
import com.example.mystylistmobile.model.ItemCategory;

import java.util.ArrayList;

public class ItemCategoryAdapter extends ArrayAdapter<ItemCategory> {

    public ItemCategoryAdapter(@NonNull Context context, ArrayList<ItemCategory> itemCategories) {
        super(context, 0, itemCategories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.itemcategory_item, parent, false);
        }

        ItemCategory itemCategory = getItem(position);
        TextView categoryTxt = listItemView.findViewById(R.id.categoryTxt);
        TextView nameTxt = listItemView.findViewById(R.id.nameTxt);
        ImageView itemCategoryImage = listItemView.findViewById(R.id.itemCategoryImage);

        nameTxt.setText(itemCategory.getName());
        categoryTxt.setText(itemCategory.getCategory());
        itemCategoryImage.setImageResource(itemCategory.getImage());
        return listItemView;
    }

}

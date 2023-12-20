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

import com.bumptech.glide.Glide;
import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.response.ItemResponseDTO;


import java.util.List;

public class ItemCategoryAdapter extends ArrayAdapter<ItemResponseDTO> {

    public ItemCategoryAdapter(@NonNull Context context, List<ItemResponseDTO> itemCategories) {
        super(context, 0, itemCategories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.itemcategory_item, parent, false);
        }

        ItemResponseDTO item = getItem(position);
        TextView categoryTxt = listItemView.findViewById(R.id.categoryTxt);
        TextView nameTxt = listItemView.findViewById(R.id.nameTxt);
        ImageView itemImage = listItemView.findViewById(R.id.itemCategoryImage);

        nameTxt.setText(item.getName());
        categoryTxt.setText(item.getCategoryName());

        Glide.with(getContext())
                .load(item.getImage())
                .error(R.drawable.icon_app)
                .into(itemImage);
        return listItemView;
    }

}

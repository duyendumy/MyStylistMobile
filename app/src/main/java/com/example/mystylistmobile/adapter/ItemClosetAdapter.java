package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mystylistmobile.R;
import com.example.mystylistmobile.model.UserItem;


import java.util.List;



public class ItemClosetAdapter extends BaseAdapter {

    private List<UserItem> userItems;

    private Context context;

    public ItemClosetAdapter(@NonNull Context context, List<UserItem> userItems) {
        this.userItems = userItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userItems.size();
    }

    @Override
    public Object getItem(int i) {
        return userItems.get(i);
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
            listItemView = LayoutInflater.from(context).inflate(R.layout.itemcategory_item, parent, false);
        }

        UserItem userItem = (UserItem) getItem(position);
        TextView categoryTxt = listItemView.findViewById(R.id.categoryTxt);
        TextView nameTxt = listItemView.findViewById(R.id.nameTxt);
        ImageView colorImage = listItemView.findViewById(R.id.itemCategoryImage);

        categoryTxt.setText(userItem.getCategory());
        nameTxt.setText(userItem.getName());
        Glide.with(context)
                .load(userItem.getImage())
                .error(R.drawable.icon_app)
                .into(colorImage);

        return listItemView;
    }

}

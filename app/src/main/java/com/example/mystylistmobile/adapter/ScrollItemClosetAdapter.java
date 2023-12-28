package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystylistmobile.R;
import com.example.mystylistmobile.model.UserItem;

import java.util.List;

public class ScrollItemClosetAdapter extends RecyclerView.Adapter<ScrollItemClosetHolder> {
    private List<UserItem> userItems;

    private Context context;

    public ScrollItemClosetAdapter(Context context, List<UserItem> userItems) {
        this.context = context;
        this.userItems = userItems;
    }

    @NonNull
    @Override
    public ScrollItemClosetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemcategory_item, parent, false);
        return new ScrollItemClosetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollItemClosetHolder holder, int position) {
        UserItem userItem = userItems.get(position);
        holder.categoryTxt.setText(userItem.getCategory());
        holder.nameTxt.setText(userItem.getName());
        Glide.with(context)
                .load(userItem.getImage())
                .error(R.drawable.icon_app)
                .into(holder.colorImage);
    }


    @Override
    public int getItemCount() {
        return userItems.size();
    }


}

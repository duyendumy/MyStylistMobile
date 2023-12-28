package com.example.mystylistmobile.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylistmobile.R;

public class ScrollItemClosetHolder extends RecyclerView.ViewHolder{
    TextView categoryTxt;
    TextView nameTxt;
    ImageView colorImage;

    public ScrollItemClosetHolder(@NonNull View itemView) {
        super(itemView);
        categoryTxt = itemView.findViewById(R.id.categoryTxt);
        nameTxt = itemView.findViewById(R.id.nameTxt);
        colorImage = itemView.findViewById(R.id.itemCategoryImage);
    }
}

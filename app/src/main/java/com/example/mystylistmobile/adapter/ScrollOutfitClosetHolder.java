package com.example.mystylistmobile.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylistmobile.R;

public class ScrollOutfitClosetHolder extends RecyclerView.ViewHolder{
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView deleteImage;

    public ScrollOutfitClosetHolder(@NonNull View itemView) {
        super(itemView);
        imageView1 = itemView.findViewById(R.id.imageView1);
        imageView2 = itemView.findViewById(R.id.imageView2);
        imageView3 = itemView.findViewById(R.id.imageView3);
        imageView4 = itemView.findViewById(R.id.imageView4);
        deleteImage = itemView.findViewById(R.id.deleteImage);
    }
}

package com.example.mystylistmobile.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylistmobile.R;

public class UserHolder extends RecyclerView.ViewHolder {
    TextView name, email;
    public UserHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.userListItem_name);
        email = itemView.findViewById(R.id.userListItem_email);
    }
}

package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.response.UserResponseDTO;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserResponseDTO> {

    public UserAdapter(@NonNull Context context, List<UserResponseDTO> userResponseDTOS) {
        super(context, 0, userResponseDTOS);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_user_item, parent, false);
        }

        UserResponseDTO userResponseDTO = getItem(position);
        TextView userListItem_name = listItemView.findViewById(R.id.userListItem_name);
        TextView userListItem_email = listItemView.findViewById(R.id.userListItem_email);

        userListItem_name.setText(userResponseDTO.getName());
        userListItem_email.setText(userResponseDTO.getEmail());

        return listItemView;
    }
}

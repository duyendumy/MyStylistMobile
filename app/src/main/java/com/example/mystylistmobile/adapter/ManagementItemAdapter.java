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
import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.dto.response.ItemResponseDTO;
import com.example.mystylistmobile.model.UserItem;

import java.util.List;

public class ManagementItemAdapter extends ArrayAdapter<ItemResponseDTO> {
    private List<ItemResponseDTO> itemResponseDTOS;

    private Context context;

    public ManagementItemAdapter(@NonNull Context context, List<ItemResponseDTO> itemResponseDTOS) {
        super(context, 0, itemResponseDTOS);
        this.itemResponseDTOS = itemResponseDTOS;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_user_item, parent, false);
        }

        ItemResponseDTO itemResponseDTO = getItem(position);
        TextView userListItem_name = listItemView.findViewById(R.id.userListItem_name);
        TextView userListItem_email = listItemView.findViewById(R.id.userListItem_email);
        ImageView imageView = listItemView.findViewById(R.id.imageView);

        userListItem_name.setText(itemResponseDTO.getName());
        userListItem_email.setText(itemResponseDTO.getId().toString());

        Glide.with(context)
                .load(itemResponseDTO.getImage())
                .error(R.drawable.image)
                .into(imageView);

        return listItemView;
    }
}

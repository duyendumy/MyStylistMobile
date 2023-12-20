package com.example.mystylistmobile.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystylistmobile.R;

import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.helper.OnClickParentCategory;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private List<CategoryResponseDTO> categories;
    private Context context;

    private OnClickParentCategory categoryListener;

    public void setOnClickParentCategory(OnClickParentCategory onClickParentCategory) {
        this.categoryListener = onClickParentCategory;
    }

    public CategoryAdapter(@NonNull Context context, List<CategoryResponseDTO> categories) {
       this.context = context;
       this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
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
            listItemView = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        }

        CategoryResponseDTO category = (CategoryResponseDTO) getItem(position);
        TextView nameTxt = listItemView.findViewById(R.id.listName);
        RelativeLayout relativeLayout = listItemView.findViewById(R.id.relativeLayout);
        nameTxt.setText(category.getName());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryListener != null){
                    categoryListener.OnClickParentCategory(position);
                }
            }
        });




        return listItemView;
    }
}

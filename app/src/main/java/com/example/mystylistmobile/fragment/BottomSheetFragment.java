package com.example.mystylistmobile.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.activity.ItemCategoryActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {


    private CheckBox seasonalColorCheckBox, bodyTypeCheckBox, styleCheckBox;

    private Button applyBtn, resetAllBtn;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_filter, null);
        bottomSheetDialog.setContentView(view);
        seasonalColorCheckBox = view.findViewById(R.id.seasonalColorCheckBox);
        bodyTypeCheckBox = view.findViewById(R.id.bodyTypeCheckBox);
        styleCheckBox = view.findViewById(R.id.styleCheckBox);
        applyBtn = view.findViewById(R.id.applyBtn);
        resetAllBtn = view.findViewById(R.id.resetAllBtn);
        seasonalColorCheckBox.setChecked(ItemCategoryActivity.isSelectedSeasonalColor);
        bodyTypeCheckBox.setChecked(ItemCategoryActivity.isSelectedSeasonalColor);
        styleCheckBox.setChecked(ItemCategoryActivity.isSelectedStyle);
        seasonalColorCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCategoryActivity.isSelectedSeasonalColor = true;
            }
        });
        bodyTypeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCategoryActivity.isSelectedBodyType = true;
            }
        });
        styleCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCategoryActivity.isSelectedStyle = true;
            }
        });
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        resetAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCategoryActivity.isSelectedSeasonalColor = false;
                ItemCategoryActivity.isSelectedBodyType = false;
                ItemCategoryActivity.isSelectedStyle = false;
                dismiss();
            }
        });

        return bottomSheetDialog;
    }

}

package com.example.mystylistmobile.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
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

    private int isSelectedSeasonalColor, isSelectedBodyType, isSelectedStyle;

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
        isSelectedSeasonalColor = ItemCategoryActivity.isSelectedSeasonalColor;
        isSelectedBodyType = ItemCategoryActivity.isSelectedBodyType;
        isSelectedStyle = ItemCategoryActivity.isSelectedStyle;
        seasonalColorCheckBox.setChecked((isSelectedSeasonalColor == 1) ? true : false);
        bodyTypeCheckBox.setChecked((isSelectedBodyType == 1) ? true: false);
        styleCheckBox.setChecked((isSelectedStyle == 1) ? true: false);
        seasonalColorCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectedSeasonalColor == 0){
                    isSelectedSeasonalColor = 1;
                } else {
                    isSelectedSeasonalColor = 0;
                }
            }
        });
        bodyTypeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectedBodyType == 0){
                    isSelectedBodyType = 1;
                } else {
                    isSelectedBodyType = 0;
                }
            }
        });
        styleCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectedStyle == 0){
                    isSelectedStyle = 1;
                } else {
                    isSelectedStyle = 0;
                }
            }
        });
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCategoryActivity.isSelectedSeasonalColor = isSelectedSeasonalColor;
                ItemCategoryActivity.isSelectedBodyType = isSelectedBodyType;
                ItemCategoryActivity.isSelectedStyle = isSelectedStyle;
                dismiss();
            }
        });
        resetAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCategoryActivity.isSelectedSeasonalColor = 0;
                ItemCategoryActivity.isSelectedBodyType = 0;
                ItemCategoryActivity.isSelectedStyle = 0;
                dismiss();
            }
        });

        return bottomSheetDialog;
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        ((ItemCategoryActivity) getActivity()).loadItem();
    }


}

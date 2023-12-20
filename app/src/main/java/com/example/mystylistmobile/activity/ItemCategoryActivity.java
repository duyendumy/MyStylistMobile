package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.ItemCategoryAdapter;
import com.example.mystylistmobile.dto.FilterDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ItemResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.fragment.BottomSheetFragment;
import com.example.mystylistmobile.helper.DataManager;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.ItemCategory;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.CategoryService;
import com.example.mystylistmobile.service.ItemService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemCategoryActivity extends AppCompatActivity {

    private GridView itemCategoryGridView;

    private ImageView filterButton;

    private CheckBox selectedSeasonalColor, selectedBodyType, selectedStyleType;
    private ImageView backImageView;

    private RetrofitService retrofitService;

    private LoadingAlert loadingAlert;

    public static int isSelectedSeasonalColor;
    public static int isSelectedBodyType;
    public static int isSelectedStyle;

    public CheckBox getSelectedSeasonalColor() {
        return selectedSeasonalColor;
    }

    public void setSelectedSeasonalColor(CheckBox selectedSeasonalColor) {
        this.selectedSeasonalColor = selectedSeasonalColor;
    }

    public CheckBox getSelectedBodyType() {
        return selectedBodyType;
    }

    public void setSelectedBodyType(CheckBox selectedBodyType) {
        this.selectedBodyType = selectedBodyType;
    }

    public CheckBox getSelectedStyleType() {
        return selectedStyleType;
    }

    public void setSelectedStyleType(CheckBox selectedStyleType) {
        this.selectedStyleType = selectedStyleType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar and the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar().hide();

        setContentView(R.layout.activity_item_category);
        isSelectedSeasonalColor = 1;
        isSelectedBodyType = 1;
        isSelectedStyle = 1;
        loadingAlert = new LoadingAlert(ItemCategoryActivity.this);
        itemCategoryGridView = findViewById(R.id.gridView);
        filterButton = findViewById(R.id.filterButton);
        backImageView = findViewById(R.id.image_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemCategoryActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        loadItem();
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment  bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }

    public void loadItem(){
        loadingAlert.startAlertDialog();
        Long parent = DataManager.getInstance().getParentCategoryId();
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setFilterByBodyShape(isSelectedBodyType);
        filterDTO.setFilterBySeasonalColor(isSelectedSeasonalColor);
        filterDTO.setFilterByStyleType(isSelectedStyle);
        ItemService itemService = retrofitService.createService(ItemService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        itemService.suggestItemsOfParentCategory(parent, filterDTO).enqueue(new Callback<ResponseModel<List<ItemResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> response) {
                if(response != null){
                    loadingAlert.closeDialog();
                    populateListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<ItemResponseDTO>, ErrorDTO>> call, Throwable t) {

            }
        });
    }
    public void populateListView(List<ItemResponseDTO> itemCategories){
        ItemCategoryAdapter itemCategoryAdapter = new ItemCategoryAdapter(this,itemCategories);
        itemCategoryGridView.setAdapter(itemCategoryAdapter);

    }

}
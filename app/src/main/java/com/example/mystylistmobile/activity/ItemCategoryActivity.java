package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.mystylistmobile.fragment.BottomSheetFragment;
import com.example.mystylistmobile.model.ItemCategory;
import com.example.mystylistmobile.retrofit.RetrofitService;

import java.util.ArrayList;

public class ItemCategoryActivity extends AppCompatActivity {

    private GridView itemCategoryGridView;

    private ImageView filterButton;

    private CheckBox selectedSeasonalColor, selectedBodyType, selectedStyleType;
    private ImageView backImageView;

    private RetrofitService retrofitService;

    public static boolean isSelectedSeasonalColor = true;
    public static boolean isSelectedBodyType = true;
    public static boolean isSelectedStyle = true;

    public void setSelectedSeasonalColor(boolean selectedSeasonalColor) {
        isSelectedSeasonalColor = selectedSeasonalColor;
    }

    public void setSelectedBodyType(boolean selectedBodyType) {
        isSelectedBodyType = selectedBodyType;
    }

    public void setSelectedStyle(boolean selectedStyle) {
        isSelectedStyle = selectedStyle;
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

        ArrayList<ItemCategory> itemCategories = new ArrayList<ItemCategory>();
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));
        itemCategories.add(new ItemCategory("Top","Red valentino",  R.drawable.icon_app));

        ItemCategoryAdapter itemCategoryAdapter = new ItemCategoryAdapter(this, itemCategories);
        itemCategoryGridView.setAdapter(itemCategoryAdapter);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment  bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

            }
        });
    }

    /*public void populateListView(ArrayList<ItemCategoryResponseDTO> itemCategories){
        ItemCategoryAdapter itemCategoryAdapter = new ItemCategoryAdapter(this,itemCategories);
        itemCategoryGridView.setAdapter(itemCategoryAdapter);

    }

    public void loadParentCategory(){
        ItemCategoryService itemCategoryService = retrofitService.createService(ItemCategoryService.class, SessionManager.getInstance(this).getUserToken());
        itemCategoryService.getAllItemCategory((long) -1, 0, 8, "id").enqueue(new Callback<ResponseModel<ItemCategoryPaging, ErrorDTO>>() {

            @Override
            public void onResponse(Call<ResponseModel<ItemCategoryPaging, ErrorDTO>> call, Response<ResponseModel<ItemCategoryPaging, ErrorDTO>> response) {
                if(response != null){

                    populateListView(response.body().getResponse().getCategories());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ItemCategoryPaging, ErrorDTO>> call, Throwable t) {
                Toast.makeText(ItemCategoryActivity.this,"Get all item categories failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });

    }*/
}
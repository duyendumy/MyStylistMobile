package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.OutfitUserItemAdapter;
import com.example.mystylistmobile.dto.AddNewOutfitDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.OnCheckboxStateChangedListener;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.model.UserOutfit;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.UserItemService;
import com.example.mystylistmobile.service.UserOutfitService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOutfitActivity extends AppCompatActivity implements OnCheckboxStateChangedListener {

    private ImageView backImageView;
    private GridView itemClosetGridView;

    private LoadingAlert loadingAlert;
    private RetrofitService retrofitService;
    private Button btnSaveOutfit;
    private List<UserItem> userItems;

    private Set<Long> selectedItems;


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
        setContentView(R.layout.activity_add_outfit);

        loadingAlert = new LoadingAlert(AddOutfitActivity.this);
        itemClosetGridView = findViewById(R.id.gridViewMyItems);
        backImageView = findViewById(R.id.image_back);
        btnSaveOutfit = findViewById(R.id.btnSaveOutfit);
        selectedItems = new HashSet<Long>();
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddOutfitActivity.this, ClosetActivity.class);
                startActivity(intent);
            }
        });
        loadUserItem();
        btnSaveOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewOutfitDTO outfitDTO = new AddNewOutfitDTO();
                outfitDTO.setUserItemIdList(selectedItems);
                saveOutfit(outfitDTO);
            }
        });
    }

    public void saveOutfit(AddNewOutfitDTO outfitDTO){
        loadingAlert.startAlertDialog();
        UserOutfitService userOutfitService = retrofitService.createService(UserOutfitService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userOutfitService.addUserOutfit(outfitDTO).enqueue(new Callback<ResponseModel<UserOutfit, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserOutfit, ErrorDTO>> call, Response<ResponseModel<UserOutfit, ErrorDTO>> response) {
                loadingAlert.closeDialog();
                Toast.makeText(AddOutfitActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddOutfitActivity.this, UserOutfitActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseModel<UserOutfit, ErrorDTO>> call, Throwable t) {
                Toast.makeText(AddOutfitActivity.this, "Save failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadUserItem(){
        UserItemService userItemService = retrofitService.createService(UserItemService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userItemService.getAllUserItem().enqueue(new Callback<ResponseModel<List<UserItem>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserItem>, ErrorDTO>> call, Response<ResponseModel<List<UserItem>, ErrorDTO>> response) {
                populateUserItemListView(response.body().getResponse());
                userItems = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserItem>, ErrorDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCheckboxStateChanged(int position, boolean isChecked) {
        if (isChecked) {
            UserItem checkedItem = userItems.get(position);
            selectedItems.add(checkedItem.getId());
            Toast.makeText(AddOutfitActivity.this, "Select item " + checkedItem.getId(), Toast.LENGTH_SHORT).show();
        } else {

        }
    }


    private void populateUserItemListView(List<UserItem> userItemList){
        OutfitUserItemAdapter userItemAdapter = new OutfitUserItemAdapter( this, userItemList);
        userItemAdapter.setOnCheckboxStateChangedListener(this);
        itemClosetGridView.setAdapter(userItemAdapter);
    }


}
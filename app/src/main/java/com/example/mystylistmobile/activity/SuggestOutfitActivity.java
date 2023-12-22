package com.example.mystylistmobile.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.dto.AddUserItemDTO;
import com.example.mystylistmobile.dto.response.CategoryPaging;
import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.helper.DataManager;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.model.UserOutfit;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.CategoryService;
import com.example.mystylistmobile.service.UserOutfitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestOutfitActivity extends AppCompatActivity {

    private EditText addName;
    private ImageView addImage;
    private Spinner spinnerCategory;
    private Button suggestButton,cancelButton;
    private Uri uri;
    private RetrofitService retrofitService;
    private Long selectedCategory;
    private String imageURL;
    private LoadingAlert loadingAlert;



    @SuppressLint("MissingInflatedId")
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

        setContentView(R.layout.activity_suggest_outfit);
        retrofitService = new RetrofitService();

        loadingAlert = new LoadingAlert(SuggestOutfitActivity.this);
        addName = findViewById(R.id.addName);
        addImage = findViewById(R.id.addImage);
        spinnerCategory = findViewById(R.id.addCategory);
        suggestButton = findViewById(R.id.suggestBtn);
        cancelButton = findViewById(R.id.cancelButton);


        loadParentCategory();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            addImage.setImageURI(uri);
                        } else {
                            Toast.makeText(SuggestOutfitActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        suggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickSaveItem();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(SuggestOutfitActivity.this, AIStylistActivity.class);
            }
        });

    }

    private void populateCategoryListView(List<CategoryResponseDTO> categoryList){
        List<String> categoryNameList= new ArrayList<String>();
        categoryNameList.add("Select Category");
        for(int i = 0; i < categoryList.size(); i++) {
            categoryNameList.add(categoryList.get(i).getName());
        }
        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                categoryNameList
        ){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }
            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {

                // Get the item view
                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    textView.setTextColor(Color.GRAY);
                }
                else { textView.setTextColor(Color.BLACK); }
                return view;
            }
        };


        // Set the drop down view resource
        spinnerArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );


        // Spinner on item selected listener
        this.spinnerCategory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {

                        // Get the spinner selected item text
                        String selectedItemText = (String) parent
                                .getItemAtPosition(position);

                        // If user change the default selection
                        // First item is disable and
                        // it is used for hint
                        if(position > 0){
                            // Notify the selected item text
                            selectedCategory = categoryList.get(position - 1).getId();
                            // loadSubCategory(selectedCategory);
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Selected : "
                                            + selectedItemText,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });


        // Finally, data bind the spinner object with adapter
        this.spinnerCategory.setAdapter(spinnerArrayAdapter);

    }

    public void loadParentCategory(){
        CategoryService itemCategoryService = retrofitService.createService(CategoryService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        itemCategoryService.getAllCategory().enqueue(new Callback<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> response) {
                if(response != null){
                    populateCategoryListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Throwable t) {
                Toast.makeText(SuggestOutfitActivity.this,"Get all parent categories failed!",Toast.LENGTH_SHORT).show();
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred",t);
            }
        });
    }

    private void handleClickSaveItem(){
        if (selectedCategory == null) {
            ((TextView) spinnerCategory.getSelectedView()).setError("None Category selected");
            return;
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(SuggestOutfitActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadImageToStorage();
                dialog.dismiss();
                saveUserItem();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });

    }

    private void saveUserItem() {
        loadingAlert.startAlertDialog();
        if (selectedCategory == null) {
            ((TextView) spinnerCategory.getSelectedView()).setError("None Category selected");
            return;
        }

        AddUserItemDTO userItemDTO = new AddUserItemDTO();
        userItemDTO.setItemCategory(selectedCategory);
        userItemDTO.setImage(imageURL);
        userItemDTO.setName(String.valueOf(addName.getText()));

        UserOutfitService userOutfitService = retrofitService.createService(UserOutfitService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        userOutfitService.suggestUserOutfit(userItemDTO).enqueue(new Callback<ResponseModel<UserOutfit, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserOutfit, ErrorDTO>> call, Response<ResponseModel<UserOutfit, ErrorDTO>> response) {
                DataManager.getInstance().setUserOutfit(response.body().getResponse());
                loadingAlert.closeDialog();
                Toast.makeText(SuggestOutfitActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SuggestOutfitActivity.this, HowToStyleActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<ResponseModel<UserOutfit, ErrorDTO>> call, Throwable t) {
                Toast.makeText(SuggestOutfitActivity.this, "Save failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void uploadImageToStorage(){
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference().child(currentDate)
                .setValue(imageURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SuggestOutfitActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SuggestOutfitActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
package com.example.mystylistmobile.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.mystylistmobile.dto.AddNewItemDTO;
import com.example.mystylistmobile.dto.response.BodyShapeResponseDTO;
import com.example.mystylistmobile.dto.response.CategoryResponseDTO;
import com.example.mystylistmobile.dto.response.ErrorDTO;
import com.example.mystylistmobile.dto.response.ItemResponseDTO;
import com.example.mystylistmobile.dto.response.ResponseModel;
import com.example.mystylistmobile.dto.response.SeasonalColorResponseDTO;
import com.example.mystylistmobile.dto.response.StyleTypeResponseDTO;
import com.example.mystylistmobile.helper.SessionManager;
import com.example.mystylistmobile.retrofit.RetrofitService;
import com.example.mystylistmobile.service.BodyShapeService;
import com.example.mystylistmobile.service.CategoryService;
import com.example.mystylistmobile.service.ItemService;
import com.example.mystylistmobile.service.SeasonalColorService;
import com.example.mystylistmobile.service.StyleTypeService;
import com.example.mystylistmobile.service.UserItemService;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    private Spinner addCategory, addSubCategory, addBodyShape, addSeasonalColor, addStyleType;

    private EditText addName;

    private Button saveButton, cancelButton;

    private ImageView addImage;
    private RetrofitService retrofitService;

    private List<CategoryResponseDTO> categoryResponseDTOList;

    private List<CategoryResponseDTO> parentCategoryList;

    private List<CategoryResponseDTO> childCategoryList;

    private Long selectedParentCategoryId;
    private Long selectedChildCategoryId;
    private Long selectedBodyShapeId;
    private Long selectedSeasonalColorId;
    private Long selectedStyleTypeId;

    private Uri uri;

    private String imageURL;

    private LoadingAlert loadingAlert;

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

        setContentView(R.layout.activity_add_item);
        retrofitService = new RetrofitService();
        loadingAlert = new LoadingAlert(AddItemActivity.this);

        addCategory = findViewById(R.id.addCategory);
        addSubCategory = findViewById(R.id.addSubCategory);
        addBodyShape = findViewById(R.id.addBodyShape);
        addSeasonalColor = findViewById(R.id.addSeasonalColor);
        addStyleType = findViewById(R.id.addStyleType);
        addName = findViewById(R.id.addName);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        addImage = findViewById(R.id.addImage);

        categoryResponseDTOList = new ArrayList<CategoryResponseDTO>();
        parentCategoryList = new ArrayList<CategoryResponseDTO>();
        childCategoryList = new ArrayList<CategoryResponseDTO>();

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
                            Toast.makeText(AddItemActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManagementItemActivity.class));
            }
        });

        CategoryService categoryService = retrofitService.createService(CategoryService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        categoryService.getAllCategory().enqueue(new Callback<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> response) {
                if(response.body() != null){
                    categoryResponseDTOList = response.body().getResponse();
                   for(CategoryResponseDTO categoryResponseDTO: categoryResponseDTOList){
                       if(categoryResponseDTO.getParent() == (long) -1){
                           parentCategoryList.add(categoryResponseDTO);
                       }
                   }
                    populateParentCategoryListView(parentCategoryList);
                }
            }
            @Override
            public void onFailure(Call<ResponseModel<List<CategoryResponseDTO>, ErrorDTO>> call, Throwable t) {

            }
        });

        BodyShapeService bodyShapeService = retrofitService.createService(BodyShapeService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        bodyShapeService.getAllBodyShapes().enqueue(new Callback<ResponseModel<List<BodyShapeResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<BodyShapeResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<BodyShapeResponseDTO>, ErrorDTO>> response) {
                if(response.body() != null){
                    populateBodyShapeListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<BodyShapeResponseDTO>, ErrorDTO>> call, Throwable t) {

            }
        });

        SeasonalColorService seasonalColorService =  retrofitService.createService(SeasonalColorService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        seasonalColorService.getAllSeasonalColors().enqueue(new Callback<ResponseModel<List<SeasonalColorResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<SeasonalColorResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<SeasonalColorResponseDTO>, ErrorDTO>> response) {
                if(response.body() != null){
                    populateSeasonalColorListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<SeasonalColorResponseDTO>, ErrorDTO>> call, Throwable t) {

            }
        });

        StyleTypeService styleTypeService = retrofitService.createService(StyleTypeService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        styleTypeService.getAllStyleTypes().enqueue(new Callback<ResponseModel<List<StyleTypeResponseDTO>, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<StyleTypeResponseDTO>, ErrorDTO>> call, Response<ResponseModel<List<StyleTypeResponseDTO>, ErrorDTO>> response) {
                if(response.body() != null){
                    populateStyleTypeListView(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<StyleTypeResponseDTO>, ErrorDTO>> call, Throwable t) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickSaveItem();
            }
        });
    }

    private void handleClickSaveItem(){
        if(addName.getText().toString().trim().equalsIgnoreCase("")){
            addName.setError("Please enter name");
            return;
        }
        if (selectedParentCategoryId == null) {
            ((TextView) addCategory.getSelectedView()).setError("None Category selected");
            return;
        }
        if (selectedChildCategoryId == null) {
            ((TextView) addSubCategory.getSelectedView()).setError("None Subcategory selected");
            return;
        }
        if (selectedBodyShapeId == null) {
            ((TextView) addBodyShape.getSelectedView()).setError("None Body shape selected");
            return;
        }
        if (selectedSeasonalColorId == null) {
            ((TextView) addSeasonalColor.getSelectedView()).setError("None Seasonal color selected");
            return;
        }
        if (selectedStyleTypeId == null) {
            ((TextView) addStyleType.getSelectedView()).setError("None Style type selected");
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
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
                saveItem();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
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
                            Toast.makeText(AddItemActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddItemActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveItem(){
        loadingAlert.startAlertDialog();
        AddNewItemDTO addNewItemDTO = new AddNewItemDTO();
        addNewItemDTO.setName(String.valueOf(addName.getText()));
        addNewItemDTO.setImage(imageURL);
        addNewItemDTO.setCategoryId(selectedChildCategoryId);
        addNewItemDTO.setBodyShapeId(selectedBodyShapeId);
        addNewItemDTO.setSeasonalColorId(selectedSeasonalColorId);
        addNewItemDTO.setStyleTypeId(selectedStyleTypeId);

        ItemService itemService = retrofitService.createService(ItemService.class, SessionManager.getInstance(this).getUserToken(), SessionManager.getInstance(this).getRefreshToken(), this);
        itemService.addItem(addNewItemDTO).enqueue(new Callback<ResponseModel<ItemResponseDTO, ErrorDTO>>() {
            @Override
            public void onResponse(Call<ResponseModel<ItemResponseDTO, ErrorDTO>> call, Response<ResponseModel<ItemResponseDTO, ErrorDTO>> response) {
                loadingAlert.closeDialog();
                Toast.makeText(AddItemActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddItemActivity.this, ManagementItemActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseModel<ItemResponseDTO, ErrorDTO>> call, Throwable t) {

            }
        });
    }
    private void populateStyleTypeListView(List<StyleTypeResponseDTO> styleTypeResponseDTOS){
        List<String> categoryNameList= new ArrayList<String>();
        categoryNameList.add("Select style type");
        for(int i = 0; i < styleTypeResponseDTOS.size(); i++) {
            categoryNameList.add(styleTypeResponseDTOS.get(i).getName());
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
        this.addStyleType.setOnItemSelectedListener(
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
                            selectedStyleTypeId = styleTypeResponseDTOS.get(position - 1).getId();
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
        this.addStyleType.setAdapter(spinnerArrayAdapter);
    }

    private void populateSeasonalColorListView(List<SeasonalColorResponseDTO> seasonalColorResponseDTOS){
        List<String> categoryNameList= new ArrayList<String>();
        categoryNameList.add("Select seasonal color");
        for(int i = 0; i < seasonalColorResponseDTOS.size(); i++) {
            categoryNameList.add(seasonalColorResponseDTOS.get(i).getName());
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
        this.addSeasonalColor.setOnItemSelectedListener(
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
                            selectedSeasonalColorId = seasonalColorResponseDTOS.get(position - 1).getId();
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
        this.addSeasonalColor.setAdapter(spinnerArrayAdapter);
    }


    private void populateBodyShapeListView(List<BodyShapeResponseDTO> bodyShapeResponseDTOS){
        List<String> categoryNameList= new ArrayList<String>();
        categoryNameList.add("Select body shape");
        for(int i = 0; i < bodyShapeResponseDTOS.size(); i++) {
            categoryNameList.add(bodyShapeResponseDTOS.get(i).getName());
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
        this.addBodyShape.setOnItemSelectedListener(
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
                            selectedBodyShapeId = bodyShapeResponseDTOS.get(position - 1).getId();
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
        this.addBodyShape.setAdapter(spinnerArrayAdapter);
    }



    private void populateParentCategoryListView(List<CategoryResponseDTO> categoryList){
        List<String> categoryNameList= new ArrayList<String>();
        categoryNameList.add("Select Parent Category");
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
        this.addCategory.setOnItemSelectedListener(
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
                            selectedParentCategoryId = categoryList.get(position - 1).getId();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Selected : "
                                            + selectedItemText,
                                    Toast.LENGTH_SHORT).show();
                            for(CategoryResponseDTO categoryResponseDTO: categoryResponseDTOList){
                                if(categoryResponseDTO.getParent() == selectedParentCategoryId){
                                    childCategoryList.add(categoryResponseDTO);
                                }
                            }
                            populateChildCategoryListView(childCategoryList);
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });


        // Finally, data bind the spinner object with adapter
        this.addCategory.setAdapter(spinnerArrayAdapter);
    }

    private void populateChildCategoryListView(List<CategoryResponseDTO> categoryList){
        List<String> categoryNameList= new ArrayList<String>();
        categoryNameList.add("Select Subcategory");
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
        this.addSubCategory.setOnItemSelectedListener(
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
                            selectedChildCategoryId = categoryList.get(position - 1).getId();
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
        this.addSubCategory.setAdapter(spinnerArrayAdapter);
    }
}
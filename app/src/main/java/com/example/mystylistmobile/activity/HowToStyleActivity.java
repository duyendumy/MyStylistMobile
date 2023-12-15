package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mystylistmobile.R;
import com.example.mystylistmobile.helper.DataManager;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.model.UserOutfit;

import java.util.List;

public class HowToStyleActivity extends AppCompatActivity {

    private ImageView imageBack, imageView1, imageView2, imageView3, imageView4;

    private TextView description;

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

        setContentView(R.layout.activity_how_to_style);
        description = findViewById(R.id.description);
        imageBack = findViewById(R.id.image_back);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);

        // Retrieve user outfit data from DataManager
        UserOutfit userOutfit = DataManager.getInstance().getUserOutfit();
        List<UserItem> userItemList = userOutfit.getUserItems();
        if(userItemList.size() == 4) {
            String content = userItemList.get(0).getCategory() + " " + userItemList.get(0).getName() + ",\n"
                            + userItemList.get(1).getCategory() + " " + userItemList.get(1).getName() + ",\n"
                            + userItemList.get(2).getCategory() + " " + userItemList.get(2).getName() + ",\n"
                            + userItemList.get(3).getCategory() + " " + userItemList.get(3).getName() + ",\n";
            description.setText(content);
            Glide.with(this)
                    .load(userItemList.get(0).getImage())
                    .error(R.drawable.icon_app)
                    .into(imageView1);
            Glide.with(this)
                    .load(userItemList.get(1).getImage())
                    .error(R.drawable.icon_app)
                    .into(imageView2);
            Glide.with(this)
                    .load(userItemList.get(2).getImage())
                    .error(R.drawable.icon_app)
                    .into(imageView3);
            Glide.with(this)
                    .load(userItemList.get(3).getImage())
                    .error(R.drawable.icon_app)
                    .into(imageView4);
        }

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HowToStyleActivity.this, AIStylistActivity.class);
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
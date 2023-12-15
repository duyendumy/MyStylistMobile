package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.mystylistmobile.R;
import com.example.mystylistmobile.adapter.ColorAdapter;
import com.example.mystylistmobile.model.Color;

import java.util.ArrayList;

public class ColorPaletteActivity extends AppCompatActivity {

    private GridView colorPaletteGridView;

    private ImageView backImageView;

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

        setContentView(R.layout.activity_color_palette);

        colorPaletteGridView = findViewById(R.id.gridView);
        ArrayList<Color> colors = new ArrayList<Color>();

        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));
        colors.add(new Color("#f6788", R.drawable.icon_app));
        colors.add(new Color("#78677", R.drawable.avatar));

        ColorAdapter colorAdapter = new ColorAdapter(this, colors);
        colorPaletteGridView.setAdapter(colorAdapter);

        backImageView = findViewById(R.id.image_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ColorPaletteActivity.this, SeasonalColorAboutActivity.class);
                startActivity(intent);
            }
        });

    }
}
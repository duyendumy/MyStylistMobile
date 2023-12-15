package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystylistmobile.R;

public class BodyMeasurementsActivity extends AppCompatActivity {

    private EditText inputBust, inputWaist, inputHighHip, inputHip;

    private Button cancelButton, saveButton;

    private Float userBust, userWaist, userHighHip, userHip;

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

        setContentView(R.layout.activity_body_measurements);

        inputBust = findViewById(R.id.inputBust);
        inputWaist = findViewById(R.id.inputWaist);
        inputHighHip = findViewById(R.id.inputHighHip);
        inputHip = findViewById(R.id.inputHip);

        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BodyMeasurementsActivity.this, BodyTypeActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userBust = Float.valueOf(inputBust.getText().toString());
                userWaist = Float.valueOf(inputWaist.getText().toString());
                userHighHip = Float.valueOf(inputHighHip.getText().toString());
                userHip = Float.valueOf(inputHip.getText().toString());
                Float minusOfBustHip = userBust - userHip;
                Float minusOFBustWaist = userBust - userWaist;
                Float minusOfHipWaist = userHip - userWaist;
                Float minusOfHighHipWaist = userHighHip - userWaist;
                Float minusOfHipBust= userHip - userBust;
                if(minusOfBustHip <= 1 && minusOfHipBust <= 3.6 && minusOFBustWaist >= 9 && minusOfHipWaist >= 10){
                    Toast.makeText(getApplicationContext(), "Your body shape is hourglass", Toast.LENGTH_SHORT).show();
                }
                else if(minusOfHipBust >= 3.6 && minusOfHipBust < 10 && minusOfHipWaist >= 9 && minusOfHighHipWaist >= 9){
                    Toast.makeText(getApplicationContext(), "Your body shape is bottom hourglass", Toast.LENGTH_SHORT).show();
                }
                else if(minusOfBustHip > 1 && minusOfBustHip < 10 && minusOFBustWaist >= 9){
                    Toast.makeText(getApplicationContext(), "Your body shape is top hourglass", Toast.LENGTH_SHORT).show();
                }
                else if(minusOfHipBust > 2 && minusOfHipWaist >= 7 && minusOfHighHipWaist > 1.193){
                    Toast.makeText(getApplicationContext(), "Your body shape is spoon", Toast.LENGTH_SHORT).show();
                }
                else if(minusOfHipBust >= 3.6 && minusOfHipWaist < 9){
                    Toast.makeText(getApplicationContext(), "Your body shape is triangle", Toast.LENGTH_SHORT).show();
                }
                else if(minusOfBustHip >= 3.6 && minusOFBustWaist < 9){
                    Toast.makeText(getApplicationContext(), "Your body shape is inverted triangle", Toast.LENGTH_SHORT).show();
                }
                else if(minusOfHipBust >= 3.6 && minusOfBustHip < 3.6 && minusOFBustWaist < 9 && minusOfHipWaist < 10){
                    Toast.makeText(getApplicationContext(), "Your body shape is rectangle", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
package com.example.mystylistmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mystylistmobile.R;

public class BodyMeasurementsActivity extends AppCompatActivity {

    private EditText inputBust, inputWaist, inputHighHip, inputHip;

    private Button cancelButton, saveButton;

    private Float userBust, userWaist, userHighHip, userHip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_measurements);

        inputBust = findViewById(R.id.inputBust);
        inputWaist = findViewById(R.id.inputWaist);
        inputHighHip = findViewById(R.id.inputHighHip);
        inputHip = findViewById(R.id.inputHip);

        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);

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
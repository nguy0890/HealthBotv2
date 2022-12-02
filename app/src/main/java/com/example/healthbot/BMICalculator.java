package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.healthbot.databinding.ActivityBmicalculatorBinding;
import com.example.healthbot.databinding.ActivityMainBinding;

public class BMICalculator extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "BMICalculator Activity"; //debugging message

    private ActivityBmicalculatorBinding binding;
    Dialog BMIChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBmicalculatorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button back_arrow = findViewById(R.id.bmi_back_arrow);

        ImageView redrobot = findViewById(R.id.redRobot);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Set minimum/maximum for the pickers
        binding.heightPicker.setMaxValue(250);
        binding.heightPicker.setMinValue(25);
        binding.heightPicker.setValue(150); //default value
        binding.weightPicker.setMaxValue(150);
        binding.weightPicker.setMinValue(25);
        binding.weightPicker.setValue(70); //default value

        binding.heightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                calculateBMI();
            }
        });

        binding.weightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                calculateBMI();
            }
        });

        //Warning button on click
        BMIChart = new Dialog(this);
        redrobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Warning Button");
                openBMIImage();
            }
        });



    }

    public void calculateBMI() {
        float heightValue = binding.heightPicker.getValue();
        heightValue = heightValue/100;
        float weightValue = binding.weightPicker.getValue();
        String bmiResult;

        float result = weightValue / (heightValue * heightValue);

        binding.BMIResult.setText(String.format("BMI: %.2f", result));

        if (result < 18.5) {
            bmiResult = "Underweight";
        } else if (result < 25) {
            bmiResult = "Healthy";
        } else if (result < 30) {
            bmiResult = "Overweight";
        } else {
            bmiResult = "Obese";
        }
        binding.BMIResultText.setText(bmiResult);

    }

    private void openBMIImage() {
        BMIChart.setContentView(R.layout.bmichart);
        BMIChart.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button understoodButton = BMIChart.findViewById(R.id.BMIClose);
        BMIChart.show();

        //Warning button on click
        understoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Understood Button");
                BMIChart.dismiss();
            }
        });
    }

}
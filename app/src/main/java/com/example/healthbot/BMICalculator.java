package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.healthbot.databinding.ActivityBmicalculatorBinding;
import com.example.healthbot.databinding.ActivityMainBinding;

public class BMICalculator extends AppCompatActivity {

    private ActivityBmicalculatorBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBmicalculatorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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

}
package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.healthbot.databinding.ActivityBmicalculatorBinding;
import com.example.healthbot.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class BMICalculator extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "BMICalculator";

    private ActivityBmicalculatorBinding binding;
    Dialog chart_dia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBmicalculatorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button openChart = findViewById((R.id.OpenChart));
        Button back_arrow = findViewById(R.id.bmi_back_arrow);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chart_dia = new Dialog(this); //open bmi chart
        openChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Show Chart Button");
                openChartDialog();
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

    private void openChartDialog() {
        chart_dia.setContentView(R.layout.bmi_chart);
        chart_dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button chartClose = chart_dia.findViewById(R.id.CloseChart);
        chart_dia.show();

        chartClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Close Chart Button");
                chart_dia.dismiss();
                Snackbar snack = Snackbar.make(findViewById(R.id.bmilayout), getString(R.string.CloseSnack), Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
    }

}
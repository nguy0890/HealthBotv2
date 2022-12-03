package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SharedPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        loadUserData();
    }

    public void onSaveClicked(View v){
        saveUserData();

        Toast.makeText(getApplicationContext(),
                getString(R.string.save_message), Toast.LENGTH_SHORT).show();
//        Intent mIntent = new Intent(SharedPreferencesActivity.this,
//                EditProfiles.class);
//        startActivity(mIntent);
        finish();
    }

    public void onCancelClicked(View v) {
        Toast.makeText(getApplicationContext(),
                getString(R.string.cancel), Toast.LENGTH_SHORT).show();
//        Intent mIntent = new Intent(SharedPreferencesActivity.this,
//                EditProfiles.class);
//        startActivity(mIntent);
        finish();
    }


    private void loadUserData() {

        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs = getSharedPreferences(file_name, MODE_PRIVATE);

        String  name_key = getString(R.string.key_name);
        String new_name_value = myPrefs.getString(name_key, " ");
        ((EditText) findViewById(R.id.editName)).setText(new_name_value);

        String  birth_key = getString(R.string.key_birth);
        String new_birth_value = myPrefs.getString(birth_key, " ");
        ((EditText) findViewById(R.id.editBirth)).setText(new_birth_value);

        String gender_key = getString(R.string.key_gender);
        int mIntValue = myPrefs.getInt(gender_key, -1);


        if (mIntValue >= 0) {
            // Find the radio button that should be checked.
            RadioButton radioBtn = (RadioButton) ((RadioGroup)
                    findViewById(R.id.radioGender))
                    .getChildAt(mIntValue);
            // Check the button.
            radioBtn.setChecked(true);
        }

    }


    private void saveUserData() {

        String file_name = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(file_name, MODE_PRIVATE);

        SharedPreferences.Editor myEditor = mPrefs.edit();
        myEditor.clear();
        String name_key  = getString(R.string.key_name);
        String new_name_entered = ((EditText) findViewById(R.id.editName))
                .getText().toString();
        myEditor.putString(name_key, new_name_entered);
        String birth_key  = getString(R.string.key_birth);
        String new_birth_entered = ((EditText) findViewById(R.id.editBirth))
                .getText().toString();
        myEditor.putString(birth_key, new_birth_entered);
        String gender_key = getString(R.string.key_gender);
        RadioGroup mRadioGroup = findViewById(R.id.radioGender);

        int buttonSelected = mRadioGroup.indexOfChild(findViewById(mRadioGroup
                .getCheckedRadioButtonId()));

        myEditor.putInt(gender_key, buttonSelected);
        myEditor.commit();

        Toast.makeText(getApplicationContext(), "saved name: " + new_name_entered,
                Toast.LENGTH_SHORT).show();
    }
}
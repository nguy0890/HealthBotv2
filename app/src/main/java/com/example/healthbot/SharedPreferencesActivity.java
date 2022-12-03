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
        Intent mIntent = new Intent(SharedPreferencesActivity.this,
                EditProfiles.class);
        startActivity(mIntent);
    }

    public void onCancelClicked(View v) {
        Toast.makeText(getApplicationContext(),
                getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        Intent mIntent = new Intent(SharedPreferencesActivity.this,
                EditProfiles.class);
        startActivity(mIntent);
    }


    private void loadUserData() {

        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs = getSharedPreferences(file_name, MODE_PRIVATE);

        String  name_key = getString(R.string.key_name);
        String new_name_value = myPrefs.getString(name_key, " ");
        ((EditText) findViewById(R.id.editName)).setText(new_name_value);

        String  email_key = getString(R.string.key_email);
        String new_email_value = myPrefs.getString(email_key, " ");
        ((EditText) findViewById(R.id.editEmail)).setText(new_email_value);

        String gender_key = getString(R.string.key_gender);
        int mIntValue = myPrefs.getInt(gender_key, -1);


        if (mIntValue >= 0) {
            // Find the radio button that should be checked.
            RadioButton radioBtn = (RadioButton) ((RadioGroup)
                    findViewById(R.id.radioGender))
                    .getChildAt(mIntValue);
            // Check the button.
            radioBtn.setChecked(true);
            Toast.makeText(getApplicationContext(),
                    "number of the radioButton is : " + mIntValue,
                    Toast.LENGTH_SHORT).show();
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
        String email_key  = getString(R.string.key_email);
        String new_email_entered = ((EditText) findViewById(R.id.editEmail))
                .getText().toString();
        myEditor.putString(email_key, new_email_entered);
        String gender_key = getString(R.string.key_gender);
        RadioGroup mRadioGroup = findViewById(R.id.radioGender);

        int buttonSelected = mRadioGroup.indexOfChild(findViewById(mRadioGroup
                .getCheckedRadioButtonId()));

        myEditor.putInt(gender_key, buttonSelected);
        myEditor.commit();

        Toast.makeText(getApplicationContext(), "saved name: " + gender_key,
                Toast.LENGTH_SHORT).show();
    }
}
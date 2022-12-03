package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class EditProfiles extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "EditProfiles"; //debugging message]
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap newBitmap;
    ImageButton btnImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiles);
        Log.i(ACTIVITY_NAME, "In onResume()");
        loadUserData();

        btnImg = findViewById(R.id.profile_img);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(capture, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    private void loadUserData() {
        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs = getSharedPreferences(file_name, MODE_PRIVATE);

        if(newBitmap != null) btnImg.setImageBitmap(newBitmap);

        String  name_key = getString(R.string.key_name);
        String new_name_value = myPrefs.getString(name_key, " ");
        ((TextView) findViewById(R.id.text_name)).setText(new_name_value);

        String  birth_key = getString(R.string.key_birth);
        String new_birth_value = myPrefs.getString(birth_key, " ");
        ((TextView) findViewById(R.id.text_birth)).setText(new_birth_value);

        String gender_key = getString(R.string.key_gender);
        int mIntValue = myPrefs.getInt(gender_key, -1);


        if (mIntValue >= 0) {
            // Find the radio button that should be checked.
            if(findViewById(R.id.radioGender) != null){
                RadioButton radioBtn = (RadioButton) ((RadioGroup)
                        findViewById(R.id.radioGender))
                        .getChildAt(mIntValue);
                // Check the button.
                radioBtn.setChecked(true);
            }
            if(mIntValue == 0) ((TextView) findViewById(R.id.text_gender)).setText(R.string.gender_female);
            else if(mIntValue == 1) ((TextView) findViewById(R.id.text_gender)).setText(R.string.gender_male);
            else ((TextView) findViewById(R.id.text_gender)).setText(R.string.gender_unknown);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            btnImg = findViewById(R.id.profile_img);

            int width = Math.round((float) 1 * imageBitmap.getWidth());
            int height = Math.round((float) 1 * imageBitmap.getHeight());
            newBitmap = Bitmap.createScaledBitmap(imageBitmap, width,
                    height, true);

            btnImg.setImageBitmap(newBitmap);
        }
    }

    public void onChangeInfoClicked(View v) {
        Intent intent = new Intent(EditProfiles.this,
                SharedPreferencesActivity.class);
        startActivity(intent);
    }

    public void onExitClicked(View v) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(EditProfiles.this);
        builder.setTitle("Do you want to close profile?");
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
        loadUserData();
    }
}
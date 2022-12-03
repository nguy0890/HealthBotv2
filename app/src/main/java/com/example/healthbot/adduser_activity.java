package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class adduser_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
    }

    public void onSaveClicked(View v){
        EditText usernameEditText = (EditText) findViewById(R.id.dialog_message_box);
        String sUsername = usernameEditText.getText().toString();
        EditText yearEditText = (EditText) findViewById(R.id.age_box);
        String sYear = yearEditText.getText().toString();
        if (sUsername.matches("")) {
            Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();
        }
        if (sYear.matches("")) {
            Toast.makeText(this, "You did not enter a Birth Year", Toast.LENGTH_SHORT).show();
        }
        else{
            saveUserData();
            Toast.makeText(getApplicationContext(),
                    getString(R.string.save_message), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void onCancelClicked(View v) {
        Toast.makeText(getApplicationContext(),
                getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveUserData() {
        //User newitem = new User();
//        EditText edit = findViewById(R.id.dialog_message_box);
//        String text = edit.getText().toString();
//        if(text == "") newitem.setUser("Guest"); //Default
//        else newitem.setUser(text);
//        edit = findViewById(R.id.age_box);
//        text = edit.getText().toString();
//        if(text != "") {
//            int age = Integer.parseInt(text);
//            newitem.setAge(age);
//        }
//        else newitem.setAge(0);
//
//        RadioGroup mRadioGroup = findViewById(R.id.radioGender);
//        int buttonSelected = mRadioGroup.indexOfChild(findViewById(mRadioGroup
//                .getCheckedRadioButtonId()));
//        newitem.setGender(buttonSelected);
        Intent intent = new Intent();

        EditText edit = findViewById(R.id.dialog_message_box);
        String text = edit.getText().toString();
        if(text == "" || text == null) intent.putExtra("setUser", "Guest"); //Default
        else intent.putExtra("setUser", text);
        edit = findViewById(R.id.age_box);
        text = edit.getText().toString();
        if(!text.equals("")  && text != null) {
            int birth = Integer.parseInt(text);
            intent.putExtra("setBirth", birth);
        }
        else intent.putExtra("setAge", 2000);;

        RadioGroup mRadioGroup = findViewById(R.id.radioGender);
        int buttonSelected = mRadioGroup.indexOfChild(findViewById(mRadioGroup
                .getCheckedRadioButtonId()));
        intent.putExtra("setGender", buttonSelected);
        setResult(RESULT_OK, intent);
    }
}
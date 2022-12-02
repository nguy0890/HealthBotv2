package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.List;


public class EditProfiles extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "EditProfiles"; //debugging message]
    private ItemsDataSource datasource;
    private ArrayAdapter<User> mAdapter;
    User newitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiles);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        ListView mListView = findViewById(R.id.list);

        // 1. call to create database
        datasource = new ItemsDataSource(this);

        // 2. open Database for writing

        datasource.open();

        //3. list all the items in table on the main screen

        List<User> values = datasource.getAllItem();

        mAdapter = new ArrayAdapter <>(this,
                android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(mAdapter);
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

    public void onClick(View view) {
        try {
            User item;
            switch (view.getId()) {
                case R.id.add:
                    addUser();
                    // Save the new comment to the database
                    item = datasource.createItem(newitem);
                    mAdapter.add(item);
                    break;
                case R.id.delete:
                    if (mAdapter.getCount() > 0) {
                        item = mAdapter.getItem(0);
                        datasource.deleteItem(item);
                        mAdapter.remove(item);
                    }
                    break;
                case R.id.delete_last:
                    if (mAdapter.getCount() > 0) {
                        item = mAdapter.getItem(mAdapter.getCount() - 1);
                        datasource.deleteItem(item);
                        mAdapter.remove(item);
                    }
                    break;
                case R.id.exitButton:
                    finish();
                    break;
            }
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void addUser() {
        android.app.AlertDialog.Builder customDialog =
                new android.app.AlertDialog.Builder(EditProfiles.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.adduser_dialog, null);
        customDialog.setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        newitem = new User();
                        EditText edit = view.findViewById(R.id.dialog_message_box);
                        String text = edit.getText().toString();
                        newitem.setUser(text);
                        edit = view.findViewById(R.id.age_box);
                        text = edit.getText().toString();
                        int age = Integer.parseInt(text);
                        newitem.setAge(age);
                        RadioGroup mRadioGroup = findViewById(R.id.radioGender);
                        int buttonSelected = mRadioGroup.indexOfChild(findViewById(mRadioGroup
                                .getCheckedRadioButtonId()));
                        newitem.setGender(buttonSelected);
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        Dialog dialog = customDialog.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
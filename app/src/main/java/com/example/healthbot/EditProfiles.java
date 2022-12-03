package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

        if(values.size() == 0){
            User newitem = new User();
            newitem.setUser("Guest");
            newitem.setBirth(2000);
            newitem.setGender(-1);
            // Save the new comment to the database
            User item = datasource.createItem(newitem);
            mAdapter.add(item);
            mAdapter.notifyDataSetChanged();
        }
    }

    public User getUser(int id){
        int count = mAdapter.getCount();
        User chosen_user = new User();
        boolean exists = false;
        for(int i=0; i < count; i++){
            if(mAdapter.getItemId(i) == id){
                exists = true;
                chosen_user = mAdapter.getItem(i);
            }
        }
        if(exists == true) return chosen_user;
        else return null;
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
                    Intent intent = new Intent(EditProfiles.this,
                            adduser_activity.class);
                    startActivityForResult(intent, 1);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                datasource.open();
                String setUser = data.getStringExtra("setUser");
                int setBirth = data.getIntExtra("setBirth", 0);
                int setGender = data.getIntExtra("setGender", -1);
                User newitem2 = new User();
                if(setUser == null || setUser == "" ) newitem2.setUser("Guest");
                else newitem2.setUser(setUser);
                if(setBirth < 1000) newitem2.setBirth(2000);
                else newitem2.setBirth(setBirth);
                newitem2.setGender(setGender);
                // Save the new comment to the database
                User item = datasource.createItem(newitem2);
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
}
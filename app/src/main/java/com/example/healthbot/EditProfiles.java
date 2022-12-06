package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EditProfiles extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "EditProfiles"; //debugging message]
    protected ArrayList<User> profiles = new ArrayList<User>();
    private ItemsDataSource datasource;
    private ArrayAdapter<User> mAdapter;
    protected ProfilesAdapter profileAdapter;
    protected TextView current_user;
    protected SharedPreferences current_user_sp;
    protected SharedPreferences.Editor sp_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiles);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        ListView mListView = findViewById(R.id.list);
        Button back = findViewById(R.id.profile_back_arrow);
        profileAdapter = new ProfilesAdapter(this);
        current_user_sp = getSharedPreferences("current_user_sp", MODE_PRIVATE);
        sp_editor = current_user_sp.edit();

        current_user = findViewById(R.id.current_user_tv);
        current_user.setText(current_user_sp.getString("user_name", ""));

        // 1. call to create database
        datasource = new ItemsDataSource(this);

        // 2. open Database for writing

        datasource.open();

        //3. list all the items in table on the main screen

        List<User> values = datasource.getAllItem();

        for (User entry : values) {
            profiles.add(entry);
        }

        mListView.setAdapter(profileAdapter);

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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sp_editor.putString("user_index", Integer.toString(i));
                sp_editor.putString("user_id", Long.toString(profiles.get(i).getId()));
                sp_editor.putString("user_name", profiles.get(i).getUser());
                sp_editor.commit();

                current_user.setText(current_user_sp.getString("user_name", ""));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        }

    public void onClick(View view) {
        try {
            User item;
            switch (view.getId()) {
                case R.id.add:
                    Intent intent = new Intent(EditProfiles.this,
                            adduser_activity.class);
                    startActivityForResult(intent, 1);

                    if (profileAdapter.getCount() <= 0) {
                        item = profileAdapter.getItem(0);
                    }else {
                        item = profileAdapter.getItem(profileAdapter.getCount() - 1);
                    }
                    break;
                case R.id.delete:
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(EditProfiles.this);
                    builder.setTitle(R.string.prof_delete_confirm);
                    builder.setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String current_user_index = current_user_sp.getString("user_index", null);
                                if (profileAdapter.getCount() > 1) {
                                    User item = profileAdapter.getItem(Integer.parseInt(current_user_index));
                                    datasource.deleteItem(item);
                                    profiles.remove(item);
                                    profileAdapter.notifyDataSetChanged();

                                    if (current_user_index.compareTo("0") != 0) {
                                        item = profileAdapter.getItem(Integer.parseInt(current_user_index) - 1);
                                    }
                                    else{
                                        item = profileAdapter.getItem(Integer.parseInt(current_user_index));
                                    }

                                    sp_editor.putString("user_name", item.getUser());
                                    sp_editor.putString("user_index", Integer.toString(profileAdapter.getCount() - 1));
                                    sp_editor.putString("user_id", Long.toString(item.getId()));
                                    sp_editor.commit();
                                    current_user.setText(item.getUser());
                                }else {
                                    Toast.makeText(getApplicationContext(), R.string.delete_last_user, Toast.LENGTH_SHORT).show();
                                }
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

                    break;
            }
            profileAdapter.notifyDataSetChanged();
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
                profiles.add(item);
                profileAdapter.notifyDataSetChanged();

                sp_editor.putString("user_index", Integer.toString(profileAdapter.getCount() - 1));
                sp_editor.putString("user_name", item.getUser());
                sp_editor.putString("user_id", Long.toString(item.getId()));
                sp_editor.commit();

                finish();
                startActivity(getIntent());
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
    private class ProfilesAdapter extends ArrayAdapter<User> {
        protected static final String ACTIVITY_NAME = "ProfilesAdapter";

        public ProfilesAdapter(Context ctx) {
            super(ctx, 0);
            Log.i(ACTIVITY_NAME, "in constructor");
        }

        public int getCount(){
            Log.i(ACTIVITY_NAME, "in onCount()");
            return profiles.size();
        }

        public User getItem(int position) {
            Log.i(ACTIVITY_NAME, "in getItem()");
            return profiles.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            Log.i(ACTIVITY_NAME, "in getView()");
            LayoutInflater inflater = EditProfiles.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.profiles_list_item, null); ;
            TextView message = (TextView)result.findViewById(R.id.profile_name);
            message.setText(   getItem(position).getUser()  ); // get the string at position
            return result;
        }
    }
}
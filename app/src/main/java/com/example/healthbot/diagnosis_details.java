package com.example.healthbot;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link diagnosis_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class diagnosis_details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected ArrayList<String> symptoms_list = new ArrayList<String>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public diagnosis_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment diagnosis_details.
     */
    // TODO: Rename and change types and number of parameters
    public static diagnosis_details newInstance(String param1, String param2) {
        diagnosis_details fragment = new diagnosis_details();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_diagnosis_details, container, false);
        setDiagnosisInfo(getArguments().getString("diagnosis_info"), rootview);
        // Inflate the layout for this fragment
        return rootview;
    }

    public void setDiagnosisInfo(String text, View rootview){
        TextView diagnosis = (TextView) rootview.findViewById(R.id.dh_frag_diag);
        TextView date =  (TextView) rootview.findViewById(R.id.dh_frag_date);
        diagnosis_details.SymptomsAdapter symptomsAdapter = new diagnosis_details.SymptomsAdapter(getContext());
        ListView symptoms = (ListView) rootview.findViewById(R.id.dh_frag_symptom);

        symptoms.setAdapter(symptomsAdapter);

        try {
            JSONObject diagnosis_info = new JSONObject(text);
            diagnosis.setText(diagnosis_info.getString("diagnosis"));
            date.setText(diagnosis_info.getString("date"));
            for(String entry : diagnosis_info.getString("symptoms").split(",")) {
                symptoms_list.add(entry);
            }
        }catch (JSONException e){
            Log.w("fragment", "failed to set info");
        }
    }

    private class SymptomsAdapter extends ArrayAdapter<String> {
        protected static final String ACTIVITY_NAME = "SymptomsAdapter";

        public SymptomsAdapter(Context ctx) {
            super(ctx, 0);
            Log.i(ACTIVITY_NAME, "in constructor");
        }

        public int getCount(){
            Log.i(ACTIVITY_NAME, "in onCount()");
            return symptoms_list.size();
        }

        public String getItem(int position) {
            Log.i(ACTIVITY_NAME, "in getItem()");
            return symptoms_list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            Log.i(ACTIVITY_NAME, "in getView()");
            LayoutInflater inflater = diagnosis_details.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.symptom_list_item, null); ;
            TextView message = (TextView)result.findViewById(R.id.symptom_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }
    }
}
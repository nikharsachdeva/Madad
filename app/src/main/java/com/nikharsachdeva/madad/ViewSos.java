package com.nikharsachdeva.madad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikharsachdeva.madad.adapter.ContactAdapterView;
import com.nikharsachdeva.madad.model.ContactModel;

import java.util.ArrayList;

public class ViewSos extends AppCompatActivity {

    TinyDB tinydb;
    ArrayList<ContactModel> savedList = new ArrayList<>();
    GridView viewSelectedListGv;
    ContactAdapterView customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sos);
        init();
        fetchSavedList();
    }

    private void fetchSavedList() {
        Gson gson = new Gson();
        for (int i = 0; i < tinydb.getListString("tinySelectedContacts").size(); i++) {
            savedList.add(gson.fromJson(tinydb.getListString("tinySelectedContacts").get(i), ContactModel.class));
        }
        //Log.d("thisisaved", new Gson().toJson(savedList));
        populateSelectedList();

    }

    private void populateSelectedList() {
        if (savedList != null && !savedList.isEmpty()) {

            customAdapter = new ContactAdapterView(savedList, ViewSos.this);

            viewSelectedListGv.setAdapter(customAdapter);
            //Toast.makeText(this, new Gson().toJson(savedList), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "No List Created Yet. \nआपने कोई सूची नहीं बनाई है ।", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        tinydb = new TinyDB(this);
        viewSelectedListGv = findViewById(R.id.viewSelectedListGv);

    }
}
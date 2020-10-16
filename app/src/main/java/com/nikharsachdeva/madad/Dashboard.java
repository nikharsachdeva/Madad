package com.nikharsachdeva.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikharsachdeva.madad.adapter.ContactAdapterView;
import com.nikharsachdeva.madad.model.ContactModel;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    TinyDB tinydb;
    CardView cardViewCreateSos, cardViewViewSos, cardViewCreateTrip, cardViewHistorySos;
    ArrayList<ContactModel> savedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();

        cardViewCreateSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cardViewViewSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ViewSos.class);
                startActivity(intent);
            }
        });

        cardViewCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchSavedList();
            }
        });

        cardViewHistorySos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, RecentTrips.class);
                startActivity(intent);
            }
        });


    }

    private void fetchSavedList() {
        Gson gson = new Gson();
        for (int i = 0; i < tinydb.getListString("tinySelectedContacts").size(); i++) {
            savedList.add(gson.fromJson(tinydb.getListString("tinySelectedContacts").get(i), ContactModel.class));
        }

        if (savedList != null && !savedList.isEmpty()) {

            Intent intent = new Intent(Dashboard.this, CreateTrip.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "No List Created Yet. \nआपने कोई सूची नहीं बनाई है ।", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {

        tinydb = new TinyDB(this);
        cardViewCreateSos = findViewById(R.id.cardViewCreateSos);
        cardViewViewSos = findViewById(R.id.cardViewViewSos);
        cardViewCreateTrip = findViewById(R.id.cardViewCreateTrip);
        cardViewHistorySos = findViewById(R.id.cardViewHistorySos);
    }


}
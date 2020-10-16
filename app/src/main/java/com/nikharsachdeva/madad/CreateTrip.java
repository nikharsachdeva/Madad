package com.nikharsachdeva.madad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikharsachdeva.madad.model.ContactModel;

import java.util.ArrayList;

public class CreateTrip extends AppCompatActivity {

    EditText enterDriverName, enterVehiclePlate, enterLocationName;
    Button create_trip;
    TinyDB tinydb;
    ArrayList<ContactModel> savedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        init();
        fetchSavedList();

        create_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String location = enterLocationName.getText().toString();
                String driverName = enterDriverName.getText().toString();
                String vehicle = enterVehiclePlate.getText().toString();
                if (location.length() == 0) {
                    location = "-";
                }
                if (driverName.length() == 0) {
                    driverName = "-";
                }

                if (validation()) {
                    finish();

                    for (int i = 0; i < savedList.size(); i++) {

                        String text = "Hi, \nI am travelling to " + location + ", The driver name is " + driverName + ".\nThe Vehicle Number is " + vehicle + ".";// Replace with your message.

                        String toNumber = savedList.get(i).getNumber(); // Replace with mobile phone number without +Sign or leading zeros, but with country code
                        //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
                        startActivity(intent);

                    }
                }

            }
        });
    }

    private void fetchSavedList() {
        Gson gson = new Gson();
        for (int i = 0; i < tinydb.getListString("tinySelectedContacts").size(); i++) {
            savedList.add(gson.fromJson(tinydb.getListString("tinySelectedContacts").get(i), ContactModel.class));
        }
    }

    private boolean validation() {
        boolean allow = true;

        if (enterVehiclePlate.getText().toString().length() == 0) {
            allow = false;
            Toast.makeText(this, "Vehicle Number is Mandatory", Toast.LENGTH_SHORT).show();
        }

        return allow;
    }

    private void init() {
        tinydb = new TinyDB(this);
        enterDriverName = findViewById(R.id.enterDriverName);
        enterVehiclePlate = findViewById(R.id.enterVehiclePlate);
        create_trip = findViewById(R.id.create_trip);
        enterLocationName = findViewById(R.id.enterLocationName);

    }
}
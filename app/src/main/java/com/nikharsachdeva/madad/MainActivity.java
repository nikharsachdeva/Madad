package com.nikharsachdeva.madad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikharsachdeva.madad.model.ContactModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText name_sos, contact_sos;
    Button add_button_sos;
    TinyDB tinydb;
    ArrayList<ContactModel> contactModelArrayList = new ArrayList<ContactModel>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        fetchPreviousList();
        add_button_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    addSosFunc();
                }
            }
        });

    }

    private void fetchPreviousList() {

        Gson gson = new Gson();
        for (int i = 0; i < tinydb.getListString("tinySelectedContacts").size(); i++) {
            contactModelArrayList.add(gson.fromJson(tinydb.getListString("tinySelectedContacts").get(i), ContactModel.class));
        }
    }

    private void addSosFunc() {
        ContactModel contactModel = new ContactModel(name_sos.getText().toString(),"+91"+contact_sos.getText().toString());
        contactModelArrayList.add(contactModel);
        tinydb.putListObject("tinySelectedContacts",contactModelArrayList);
        name_sos.getText().clear();
        contact_sos.getText().clear();
        name_sos.requestFocus();
        Toast.makeText(this, "Added to SOS list.", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean allow = true;

        if (name_sos.getText().length() == 0) {
            allow = false;
            name_sos.setError("Can't be empty.");
        }

        if (contact_sos.getText().length() == 0) {
            allow = false;
            contact_sos.setError("Can't be empty.");
        }

        return allow;
    }


    private void init() {
        tinydb = new TinyDB(this);
        name_sos = findViewById(R.id.name_sos);
        contact_sos = findViewById(R.id.contact_sos);
        add_button_sos = findViewById(R.id.add_button_sos);
    }

}
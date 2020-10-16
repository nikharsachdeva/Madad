package com.nikharsachdeva.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.nikharsachdeva.madad.adapter.ContactAdapter;
import com.nikharsachdeva.madad.model.ContactModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {


    public boolean is_in_actionMode = false;
    TextView counter_text;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView.Adapter adapter;
    ArrayList<ContactModel> arrayList = new ArrayList<>();
    ArrayList<ContactModel> selectedList = new ArrayList<>();
    int counter = 0;
    Button saveList_btn;
    TinyDB tinydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        init();
        requestPermissionn();

        saveList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedList != null && !selectedList.isEmpty()) {

                    Gson gson = new Gson();
                    ArrayList<String> gsonString = new ArrayList<>();
                    for (int i = 0; i < selectedList.size(); i++)
                        gsonString.add(gson.toJson(selectedList.get(i)));
                    tinydb.putListString("tinySelectedContacts", gsonString);

                    Toast.makeText(MainActivity.this, "List Saved. \n सूची बन गयी है ।", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "You can't save empty list\nख़ाली सूची नहीं बना सकते।", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
//        return true;
//
//    }

    public void prepareSelection(View view, int position) {

        if (((CheckBox) view).isChecked()) {
            selectedList.add(arrayList.get(position));
            counter = counter + 1;
            updateCounter(counter);
        } else {
            selectedList.remove(arrayList.get(position));
            counter = counter - 1;
            updateCounter(counter);
        }

    }

    public void updateCounter(int counter) {
        if (counter == 0) {
            Toast.makeText(this, "0 items selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, counter + " items selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        //counter_text = findViewById(R.id.counter_text);
        //counter_text.setVisibility(View.GONE);
        tinydb = new TinyDB(this);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        saveList_btn = findViewById(R.id.saveList_btn);
    }

    private void requestPermissionn() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);

        } else {
            loadContacts();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            loadContacts();
        } else {
            Toast.makeText(this, "Please allow access to contacts.\n कृपया फ़ोन नम्बर की सूची का प्रवेश प्रदान करें ।", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void loadContacts() {
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNumber = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        ContactModel contactModel = new ContactModel(name, phoneNumber);
                        arrayList.add(contactModel);
                        adapter = new ContactAdapter(MainActivity.this, arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    pCur.close();
                }
            }
        } else {
            Toast.makeText(this, "No Contacts Found \n आपकी फ़ोन बुक ख़ाली है ।", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onLongClick(View view) {

//        toolbar.getMenu().clear();
//        toolbar.inflateMenu(R.menu.mewn_action_mode);
//        counter_text.setVisibility(View.VISIBLE);
        is_in_actionMode = true;
        adapter.notifyDataSetChanged();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }
}
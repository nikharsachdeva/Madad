package com.nikharsachdeva.madad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nikharsachdeva.madad.R;
import com.nikharsachdeva.madad.model.ContactModel;

import java.util.List;

public class ContactAdapterView extends BaseAdapter {

    public static List<ContactModel> contactModelList;
    public Context context;

    public ContactAdapterView(List<ContactModel> contactModelList, Context context) {
        this.contactModelList = contactModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contactModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_contact_view, null);

        TextView contact_name_view = view.findViewById(R.id.contact_name_view);
        TextView contact_number_view = view.findViewById(R.id.contact_number_view);

        contact_name_view.setText(contactModelList.get(position).getName());
        contact_number_view.setText(contactModelList.get(position).getNumber());


        return view;
    }

}

package com.nikharsachdeva.madad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nikharsachdeva.madad.MainActivity;
import com.nikharsachdeva.madad.R;
import com.nikharsachdeva.madad.model.ContactModel;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    MainActivity mainActivity;
    Context context;
    ArrayList<ContactModel> contactModelArrayList = new ArrayList<>();

    public ContactAdapter(Context context, ArrayList<ContactModel> contactModelArrayList) {
        this.contactModelArrayList = contactModelArrayList;
        this.context = context;
        mainActivity = (MainActivity) context;

    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view, mainActivity);


        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        holder.contact_name.setText(contactModelArrayList.get(position).getName());
        holder.contact_number.setText(contactModelArrayList.get(position).getNumber());

        if (!mainActivity.is_in_actionMode) {
            holder.contact_checkbox.setVisibility(View.GONE);
        } else {
            holder.contact_checkbox.setVisibility(View.VISIBLE);
            holder.contact_checkbox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return contactModelArrayList.size();
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView contact_name, contact_number;
        CheckBox contact_checkbox;
        MainActivity mainActivity;
        CardView cardView;

        public ContactViewHolder(@NonNull View itemView, MainActivity mainActivity) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_checkbox = itemView.findViewById(R.id.check_list_item);
            this.mainActivity = mainActivity;
            cardView = itemView.findViewById(R.id.contact_card);
            cardView.setOnLongClickListener(mainActivity);
            contact_checkbox.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            mainActivity.prepareSelection(view,getAdapterPosition());

        }
    }
}

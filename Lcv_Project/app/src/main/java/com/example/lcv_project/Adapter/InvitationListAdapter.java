package com.example.lcv_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lcv_project.Models.Wedding;
import com.example.lcv_project.R;

import java.util.ArrayList;

public class InvitationListAdapter extends ArrayAdapter<Wedding> {
    public InvitationListAdapter(@NonNull Context context, int resource, ArrayList<Wedding> weddingArrayList) {
        super(context, resource, weddingArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Wedding wedding = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.invitation_list_data, parent, false);
        }

        TextView weddingNameTV = convertView.findViewById(R.id.invitation_list_data_TV);

        weddingNameTV.setText(wedding.getWedding_name());

        return super.getView(position, convertView, parent);
    }
}

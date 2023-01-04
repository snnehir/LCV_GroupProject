package com.example.lcv_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lcv_project.Models.Wedding;
import com.example.lcv_project.R;
import com.example.lcv_project.SeatReservationActivity;

import java.util.ArrayList;
import java.util.List;

public class InvitationListAdapter extends ArrayAdapter<Wedding> {

    private Context context;

    public InvitationListAdapter(@NonNull Context context, int resource, ArrayList<Wedding> weddingArrayList) {
        super(context, resource, weddingArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Wedding wedding = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.invitation_list_data, parent, false);
        }

        TextView weddingNameTV = convertView.findViewById(R.id.invitation_list_data_TV);

        weddingNameTV.setText(wedding.getWedding_name());

        return convertView;


    }

}

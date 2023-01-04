package com.example.lcv_project.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lcv_project.Models.Wedding;
import com.example.lcv_project.R;

import java.io.File;
import java.util.ArrayList;

public class InvitationAdapter extends ArrayAdapter<Wedding> {
    Context context;
    private int resource;
    private ArrayList<Wedding> objects;
    public InvitationAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Wedding> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);

        TextView wedding_name = convertView.findViewById(R.id.wedding_name);
        wedding_name.setText(objects.get(position).getWedding_name());

        TextView wedding_start = convertView.findViewById(R.id.wedding_date);
        wedding_start.setText(objects.get(position).getWeddingStart());

        TextView location = convertView.findViewById(R.id.location);
        location.setText(objects.get(position).getWedding_location());

        return convertView;
    }
}

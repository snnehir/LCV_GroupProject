package com.example.lcv_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lcv_project.Models.UserListItem;
import com.example.lcv_project.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<UserListItem> {
    Context context;
    private int resource;
    private ArrayList<UserListItem> objects;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserListItem> objects) {
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

        ImageView profile_img = convertView.findViewById(R.id.profile_img);
        String img_url = objects.get(position).getProfile_picture_url();
        int res_id;
        if(img_url == null || img_url.isEmpty()){
            res_id = 0;
        }else{
            // search image in drawable
            res_id = this.context.getResources().getIdentifier(objects.get(position).getProfile_picture_url(), "drawable", this.context.getPackageName());
        }
        profile_img.setImageResource(res_id == 0 ? R.drawable.account: res_id);

        TextView full_name = convertView.findViewById(R.id.full_name);
        full_name.setText(objects.get(position).getFull_name());

        TextView username = convertView.findViewById(R.id.username);
        username.setText(objects.get(position).getUsername());

        // Friend
        ImageView friend_status = convertView.findViewById(R.id.friend_status);
        boolean is_friend = objects.get(position).getIs_friend();
        friend_status.setImageResource(is_friend ? R.drawable.friend : R.drawable.not_friend);

        return convertView;
    }



}

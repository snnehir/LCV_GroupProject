package com.example.lcv_project;

import static com.example.lcv_project.MainActivity.logged_in_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.lcv_project.Adapter.CustomAdapter;
import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Models.UserListItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SearchUserActivity extends AppCompatActivity {

    ListView listView;
    EditText searchEt;
    CheckBox friends_only;
    Button invite_btn;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_user);

        ctx = this;
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_search);
        // Set an OnNavigationItemSelectedListener on the BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.bottom_nav_create_invitation:
                        System.out.println("================== NAV: create invitation");
                        startActivity(new Intent(ctx, CreateInvitationActivity.class));
                        break;
                    case R.id.bottom_nav_invitations:
                        System.out.println("================== NAV: invitations");
                        startActivity(new Intent(ctx, InvitationsActivity.class));
                        break;
                    case R.id.bottom_nav_profile:
                        System.out.println("================== NAV: my profile");
                        startActivity(new Intent(ctx, ProfileActivity.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        invite_btn = findViewById(R.id.invite_btn);
        invite_btn.setVisibility(View.GONE);
        searchEt = findViewById(R.id.searchEt);
        friends_only = findViewById(R.id.friends_only);
        ArrayList<UserListItem> userList;
        DBAdapter db = new DBAdapter(this);
        db.open();
        userList = db.getSearchedUsers("", false, logged_in_user.getUserId());
        db.close();
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.user_list_item, userList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(customAdapter);
        listView.setLongClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(" TIKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK -----------------------------");
            }
        });
        // Set an OnItemLongClickListener on the ListView
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            boolean[] longPressed = new boolean[listView.getCount()];
            int selected_user_count = 0;
            ArrayList<UserListItem> pressedUsers = new ArrayList<>();
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int pos, long id) {
                if(longPressed[pos]){
                    Log.v("==> long clicked 2","pos: " + pos);
                    LinearLayout userLayout = view.findViewById(R.id.user_layout);
                    userLayout.setBackgroundColor(Color.parseColor("white"));

                    ImageView profile_img = view.findViewById(R.id.profile_img); // src

                    profile_img.setImageResource(R.drawable.account);
                    selected_user_count--;
                    longPressed[pos] = false;
                }
                else{
                    Log.v("==> long clicked","pos: " + pos);
                    LinearLayout userLayout = view.findViewById(R.id.user_layout);
                    userLayout.setBackgroundColor(Color.parseColor("#f3e9f7"));

                    ImageView profile_img = view.findViewById(R.id.profile_img);
                    profile_img.setImageResource(R.drawable.selected);
                    selected_user_count ++;
                    longPressed[pos] = true;
                    //pressedUsers.add()
                }

                if(selected_user_count != 0){
                    invite_btn.setVisibility(View.VISIBLE);
                }
                else{
                    invite_btn.setVisibility(View.GONE);
                }
                return true;
            }
        });

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.search_btn:
                String full_name = searchEt.getText().toString().isEmpty() ? "" : searchEt.getText().toString();
                ArrayList<UserListItem> userList;
                DBAdapter db = new DBAdapter(this);
                db.open();
                userList = db.getSearchedUsers(full_name, friends_only.isChecked(), logged_in_user.getUserId());
                db.close();
                CustomAdapter customAdapter = new CustomAdapter(this, R.layout.user_list_item, userList);
                listView = findViewById(R.id.listView);
                listView.setAdapter(customAdapter);
                listView.setLongClickable(true);
                break;

            case R.id.invite_btn:
                // alert
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // Set the title of the alert box
                builder.setTitle("Invite selected users?");

                // Add edittext (message?) & spinner (select which wedding invitation is send to users)
                EditText input = new EditText(ctx);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setHint("Enter your message to guest...");
                builder.setView(input);

                Spinner spinner = new Spinner(ctx);
                // TODO: fill it from sqlite (weddings)
                ArrayList<String> items = new ArrayList<>();
                items.add("Item 1");
                items.add("Item 2");
                items.add("Item 3");

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                builder.setView(spinner);
                // TODO: edittext not displayed

                // Set the send button and its click listener
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: selected users -> guests
                    }
                });

                // Set the close button and its click listener
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Create and show the alert box
                AlertDialog dialog = builder.create();

                dialog.show();

                break;
            default:
                break;

        }
    }
}
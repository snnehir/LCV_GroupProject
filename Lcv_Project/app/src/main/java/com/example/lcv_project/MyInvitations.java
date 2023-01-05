package com.example.lcv_project;

import static com.example.lcv_project.MainActivity.logged_in_user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Adapter.InvitationAdapter;
import com.example.lcv_project.Models.Wedding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MyInvitations extends AppCompatActivity {

    Context ctx;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_invitations);
        DBAdapter db = new DBAdapter(this);
        db.open();
        ArrayList<Wedding> weddings = db.getWeddingsOfUser(logged_in_user.getUserId());
        db.close();
        ctx = this;
        InvitationAdapter invitationAdapter = new InvitationAdapter(this, R.layout.created_wedding_list_item, weddings);
        listView = findViewById(R.id.my_invitations_lv);
        listView.setAdapter(invitationAdapter);
        listView.setClickable(true);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the item that was clicked
                Wedding item = (Wedding) parent.getItemAtPosition(position);
                displayAlertBox(item.toString());
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_create_invitation);
        // Set an OnNavigationItemSelectedListener on the BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.bottom_nav_search:

                        startActivity(new Intent(ctx, SearchUserActivity.class));
                        break;
                    case R.id.bottom_nav_invitations:

                        startActivity(new Intent(ctx, InvitationsActivity.class));
                        break;
                    case R.id.bottom_nav_profile:

                        startActivity(new Intent(ctx, ProfileActivity.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.add_invitation_btn:
                startActivity(new Intent(ctx, CreateInvitationActivity.class));
                break;
        }

    }

    public void displayAlertBox (String weddingString){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(ctx);
        mydialog.setTitle("Wedding Detail");
        mydialog.setMessage(weddingString);

        mydialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        AlertDialog dialog = mydialog.create();
        dialog.show();
    }
}
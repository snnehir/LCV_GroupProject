package com.example.lcv_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lcv_project.Adapter.InvitationListAdapter;
import com.example.lcv_project.Models.Wedding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class InvitationsActivity extends AppCompatActivity {

    //Button seatReservationBtn;
    Button attendBtn;
    Button notAttendBtn;
    Button viewDetailBtn;
    TextView weddingNameTv;
    ListView invitationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_invitations);

        Context ctx = this;

        invitationList = findViewById(R.id.list_view_invitations);
        //add data from db to the listview
        ArrayList<Wedding> weddingsArraylist = new ArrayList<>();


        //we make custom adapter fro listvew
        InvitationListAdapter invitationListAdapter = new InvitationListAdapter(ctx, R.layout.invitation_list_data, weddingsArraylist);
        invitationList.setAdapter(invitationListAdapter);
        invitationList.setChoiceMode(invitationList.CHOICE_MODE_SINGLE);

        /*
        -----------------------seat reservation button-------------------------

        seatReservationBtn = findViewById(R.id.seat_reservation_button);
        seatReservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, SeatReservationActivity.class));
            }
        });


        //bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_invitations);
        // Set an OnNavigationItemSelectedListener on the BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.bottom_nav_search:
                        System.out.println("================== NAV: search users");
                        startActivity(new Intent(ctx, SearchUserActivity.class));
                        break;
                    case R.id.bottom_nav_create_invitation:
                        System.out.println("================== NAV: create invitation");
                        startActivity(new Intent(ctx, CreateInvitationActivity.class));
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
        */
    }
}
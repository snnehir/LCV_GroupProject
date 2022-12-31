package com.example.lcv_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CreateInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_invitation);
        Context ctx = this;
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

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


    }
}
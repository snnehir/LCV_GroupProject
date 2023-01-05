package com.example.lcv_project;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class ProfileActivity extends AppCompatActivity {
    private EditText username, fullName, mail;
    private MainActivity User;
    private ImageView imageView;
    static final int SELECT_IMAGE=1;
    Uri imageUri;
    public static User logged_in_user = new User();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);


        imageView=  findViewById( R.id.new_image );
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType( "image/*" );
                startActivityForResult(intent, SELECT_IMAGE);

            }
        } );

        Context ctx = this;
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_profile);
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
                    case R.id.bottom_nav_create_invitation:
                        startActivity(new Intent(ctx, MyInvitations.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
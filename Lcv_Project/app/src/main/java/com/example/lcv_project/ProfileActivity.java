package com.example.lcv_project;

import static com.example.lcv_project.Models.User.getUsername;

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
import android.widget.TextView;

import com.example.lcv_project.Adapter.CustomAdapter;
import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.ObjectInputStream;


public class ProfileActivity extends AppCompatActivity {
    private EditText password, editName;
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

        editName=findViewById( R.id.editName );
        password=findViewById( R.id.password );

        DBAdapter db = new DBAdapter(this);

       // TextView username = EditText.findViewById(R.id.editEmail);

       // editName.setText( com.example.lcv_project.Models.User.getFull_name());
       // editEmail.setText( User.getmail );
       // String editName = this.editName.getText().toString().isEmpty() ? "" : this.editName.getText().toString();

       // logged_in_user = db.loginUser(Username);
        if(logged_in_user != null) {
            String username_or_mail = this.editName.getText().toString().isEmpty() ? "" : this.editName.getText().toString();
            editName.setText(getUsername());
            /*String passwordd= this.password.getText().toString().isEmpty() ? "" : this.password.getText().toString();
            password.setText( logged_in_user.getPassword() );*/
            String passwordd= this.password.getText().toString().isEmpty() ? "" : this.password.getText().toString();
            password.setText( logged_in_user.getPassword() );
        }
        imageView=  findViewById( R.id.new_image );
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType( "image/*" );
                startActivityForResult(intent, SELECT_IMAGE);

            }
        } );
        //editEmail.setText( User.getusername_or_mail());
        /*TextView full_name = loginUser.findViewById(R.id.editName);
        full_name.setText(objects.get(position).getusername_or_mail());

        TextView username = convertView.findViewById(R.id.editEmail);
        username.setText(objects.get(position).getUsername());*/

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
                        System.out.println("================== NAV: search users");
                        startActivity(new Intent(ctx, SearchUserActivity.class));
                        break;
                    case R.id.bottom_nav_invitations:
                        System.out.println("================== NAV: invitations");
                        startActivity(new Intent(ctx, InvitationsActivity.class));
                        break;
                    case R.id.bottom_nav_create_invitation:
                        System.out.println("================== NAV: create invitation");
                        startActivity(new Intent(ctx, CreateInvitationActivity.class));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==SELECT_IMAGE && resultCode== RESULT_OK){
            imageUri=data.getData();
            imageView.setImageURI( imageUri );
        }
    }
}
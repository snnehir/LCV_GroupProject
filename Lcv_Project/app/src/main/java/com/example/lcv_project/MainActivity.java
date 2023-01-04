package com.example.lcv_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Models.User;

// Login page
public class MainActivity extends AppCompatActivity {

    public static User logged_in_user = new User();
    Button btnLogin, btnSignup;
    EditText username_or_mail, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        username_or_mail = findViewById(R.id.username_or_mail);
        password = findViewById(R.id.password);

        /* clear db*/
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.clearTable();
        // create fake data TODO: wedding
        db.createFakeData();
        db.createFakeWeddingData();
        db.createFakeWeddingGuests();
        db.createFakeWeddingOwner();
        db.close();



    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnLogin:
                //Toast.makeText(MainActivity.this, "Login attempt", Toast.LENGTH_SHORT).show();
                String username_or_mail = this.username_or_mail.getText().toString().isEmpty() ? "" : this.username_or_mail.getText().toString();
                String password = this.password.getText().toString().isEmpty() ? "" : this.password.getText().toString();
                System.out.println(" =================== username: " + username_or_mail);
                System.out.println(" =================== password: " + password);
                DBAdapter db = new DBAdapter(this);
                db.open();
                logged_in_user = db.loginUser(username_or_mail, password);
                if(logged_in_user == null){
                    Toast.makeText(MainActivity.this, "Login fail.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Login success.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, CreateInvitationActivity.class));
                }
                db.close();
                break;
            case R.id.btnSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

        }
    }
}
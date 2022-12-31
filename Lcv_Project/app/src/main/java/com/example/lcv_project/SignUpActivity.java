package com.example.lcv_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lcv_project.adapter.DBAdapter;
import com.example.lcv_project.models.User;

public class SignUpActivity extends AppCompatActivity {
    Button btnLogin, btnSignup;
    EditText fullName, username, mail, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        password = findViewById(R.id.password);
        fullName = findViewById(R.id.fullName);
        mail = findViewById(R.id.mail);
        username = findViewById(R.id.username);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSignup:
                //Toast.makeText(SignUpActivity.this, "Signup attempt", Toast.LENGTH_SHORT).show();
                String username = this.username.getText().toString();
                String password = this.password.getText().toString();
                String mail = this.mail.getText().toString();
                String full_name = fullName.getText().toString();
                DBAdapter db = new DBAdapter(this);
                db.open();
                User user = new User(full_name, username, mail, password);
                long user_id = db.addUser(user);
                db.close();
                System.out.println(" >>>>>>>>> user_id: " + user_id);
                if(user_id == -1){
                    Toast.makeText(SignUpActivity.this, "Same email or username is used before.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Signed up successfully. You can login now.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            case R.id.btnLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }
}
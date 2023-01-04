package com.example.lcv_project;

import static com.example.lcv_project.MainActivity.logged_in_user;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Models.Wedding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateInvitationActivity extends AppCompatActivity {

    Context ctx;
    final Calendar myCalendar= Calendar.getInstance();
    EditText date_picker, startTime, endTime, name, bride, groom, location, details,
            numberOfAccompanier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        /* Context ctx2 = getApplicationContext();
        Configuration.getInstance().load(ctx2, PreferenceManager.getDefaultSharedPreferences(ctx2)); */
        setContentView(R.layout.activity_create_invitation);
        ctx = this;

        name = findViewById(R.id.name);
        bride = findViewById(R.id.bride_name);
        groom = findViewById(R.id.groom_name);
        details = findViewById(R.id.detail);
        location = findViewById(R.id.location);
        numberOfAccompanier = findViewById(R.id.accompanier);

        date_picker =(EditText) findViewById(R.id.date_picker);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ctx, date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startTime = findViewById(R.id.start_time);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        endTime = findViewById(R.id.end_time);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

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

                    case R.id.bottom_nav_create_invitation:
                        System.out.println("================== NAV: my invitations");
                        startActivity(new Intent(ctx, MyInvitations.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }
    private void updateLabel(){

        SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);
        date_picker.setText(dateFormat.format(myCalendar.getTime()));
    }

<<<<<<< HEAD
    public void onClick(View view) {
    }
=======
    public void onClick(View view){
        switch (view.getId()){
            case R.id.save_btn:
                String wedding_name = name.getText().toString();
                if(wedding_name == null || wedding_name.isEmpty()){
                    Toast.makeText(ctx, "Invitation name cannot be empty.", Toast.LENGTH_SHORT).show();
                    break;
                }
                String weddingStart = date_picker.getText().toString() + " " + startTime.getText().toString();
                String weddingEnd = date_picker.getText().toString() + " " + endTime.getText().toString();
                String bride_name = bride.getText().toString();
                String groom_name = groom.getText().toString();
                String location_str = location.getText().toString();
                String details_str = details.getText().toString();
                int accompanier_num = Integer.parseInt(numberOfAccompanier.getText().toString().isEmpty() ? "0" : numberOfAccompanier.getText().toString());

                Wedding wedding = new Wedding(bride_name, groom_name, wedding_name, location_str,
                                              details_str, accompanier_num,
                                              weddingStart, weddingEnd);
                DBAdapter db = new DBAdapter(ctx);
                db.open();
                db.addWedding(wedding, logged_in_user.getUserId());
                db.close();
                Toast.makeText(ctx, "Invitation is created!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ctx, CongratualationsActivity.class));
                break;
        }
    }




>>>>>>> origin/master
}
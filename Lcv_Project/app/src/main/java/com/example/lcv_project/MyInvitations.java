package com.example.lcv_project;

import static com.example.lcv_project.MainActivity.logged_in_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Models.Wedding;

import java.util.ArrayList;

public class MyInvitations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invitations);

        DBAdapter db = new DBAdapter(this);
        db.open();
        ArrayList<Wedding> weddings = db.getWeddingsOfUser(logged_in_user.getUserId());
        db.close();
        System.out.println(" ====================================== WEDDINGS::: =======================================");
        for(Wedding w: weddings){
            System.out.println("Wedding: " + w);
        }
        System.out.println(" ====================================== WEDDINGS::: END =======================================");
    }
}
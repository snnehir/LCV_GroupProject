package com.example.lcv_project;

import static com.example.lcv_project.MainActivity.logged_in_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lcv_project.Adapter.DBAdapter;
import com.example.lcv_project.Adapter.InvitationListAdapter;
import com.example.lcv_project.Models.Wedding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class InvitationsActivity extends AppCompatActivity {

    //Button seatReservationBtn;
    Button attendBtn;
    Button notAttendBtn;
    TextView weddingNameTv;
    ListView invitationList;
    DBAdapter dbAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_invitations);

        context = this;

        invitationList = findViewById(R.id.list_view_invitations);
        //add data from db to the listview
        ArrayList<Wedding> weddingsArraylist = new ArrayList<>();

        //we make custom adapter for listview
        InvitationListAdapter invitationListAdapter = new InvitationListAdapter(context, R.layout.invitation_list_data, weddingsArraylist);
        invitationList.setAdapter(invitationListAdapter);
        invitationList.setChoiceMode(invitationList.CHOICE_MODE_SINGLE);

        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        weddingsArraylist = dbAdapter.getInvitedWeddings(logged_in_user.getUserId());
        //
        invitationListAdapter.addAll(weddingsArraylist);
        dbAdapter.close();

        invitationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Wedding selectedwedding = (Wedding) invitationList.getAdapter().getItem(i);
                displayAlertBox(selectedwedding.toString());

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

                        startActivity(new Intent(context, SearchUserActivity.class));
                        break;
                    case R.id.bottom_nav_create_invitation:

                        startActivity(new Intent(context, MyInvitations.class));
                        break;
                    case R.id.bottom_nav_profile:

                        startActivity(new Intent(context, ProfileActivity.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    public void displayAlertBox (String weddingString){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
        mydialog.setTitle("Wedding Detail");
        mydialog.setMessage(weddingString);



        mydialog.setNeutralButton("Attend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //attend button redirects to seat reservation activity
                startActivity(new Intent(context, SeatReservationActivity.class));
            }
        });

        AlertDialog dialog = mydialog.create();
        dialog.show();
    }
}
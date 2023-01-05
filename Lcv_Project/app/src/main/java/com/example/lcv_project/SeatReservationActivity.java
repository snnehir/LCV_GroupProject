package com.example.lcv_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.Toast;

public class SeatReservationActivity extends AppCompatActivity {

    GridLayout mainGrid;
    int numberOfReservedSeats;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_seat_reservation);

        mainGrid = findViewById(R.id.mainGrid);

        //on clicking to select reservation seats
                //loop all children items of main grid
                for (int i = 0; i < mainGrid.getChildCount(); i++) {
                    //reset the number of seats selected
                    numberOfReservedSeats = 0;
                    //You can see , all child item is CardView , so we just cast object to CardView
                    final CardView cardView = (CardView) mainGrid.getChildAt(i);
                    final int finalIndex = i;
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                                //change background color
                                cardView.setCardBackgroundColor(Color.parseColor("#8F5B2D63"));

                                //count the selected seats
                                numberOfReservedSeats++;
                                Toast.makeText(SeatReservationActivity.this, "You Selected a Seat", Toast.LENGTH_SHORT).show();
                            } else {
                                //Change background color
                                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                numberOfReservedSeats--;
                                Toast.makeText(SeatReservationActivity.this, "You Unselected a Seat", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
    } //onCreate end

    public void reserveButtonOnClick(View view) {
        AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
        dialogBox.setTitle("Seat Reservation");
        dialogBox.setMessage("You have selected "+numberOfReservedSeats * -1 +" number of seats.");
        dialogBox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //attend button redirects to seat reservation activity
                startActivity(new Intent(SeatReservationActivity.this, InvitationsActivity.class));
            }
        });
        dialogBox.create();
        dialogBox.show();
    }
}
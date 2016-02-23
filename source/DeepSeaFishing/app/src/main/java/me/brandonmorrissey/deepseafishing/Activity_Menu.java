package me.brandonmorrissey.deepseafishing;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * Created by: Brandon Morrissey
 * Date: 02/07/2016
 * Activity_Menu parent activity that acts as a menu for the application, it provides
 * buttons to use each feature
 */
public class Activity_Menu extends AppCompatActivity {

    int reserved;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.FABadd);
        FloatingActionButton viewBtn = (FloatingActionButton) findViewById(R.id.FABview);
        FloatingActionButton shareBtn = (FloatingActionButton) findViewById(R.id.FABshare);
        FloatingActionButton eventBtn = (FloatingActionButton) findViewById(R.id.FABevent);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReservation();
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservation();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareReservation();
            }
        });

        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventReservation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 123 && resultCode == RESULT_OK) {
                reserved = data.getIntExtra("reserved", 1);
                date = data.getStringExtra("date");
            }
        }
    }

    /**
     * Method to start a new intent for the Activity_Add_Booking class
     * and pass in the randomly generated customer ID.
     */
    public void createReservation() {
        Random random = new Random();
        int customerId = random.nextInt(999);
        Intent intent = new Intent(this, Activity_Add_Booking.class);
        intent.putExtra("customerId", customerId);
        startActivityForResult(intent, 123);
    }

    /**
     * Method to start new intent for the Activity_View_Booking class
     */
    public void showReservation() {
        Intent intent = new Intent(this, Activity_View_Booking.class);
        startActivity(intent);
    }

    /**
     * Method to show share options if user made a reservation
     */
    public void shareReservation() {
        if (reserved == 1) {
            String shareString = "I made a reservation at PEI Deep Sea Fishing!";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareString);
            startActivity(Intent.createChooser(intent, "Share With"));
        } else {
            Toast.makeText(Activity_Menu.this, "You need to make a reservation to share!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to create event in a calendar chosen by the user
     */
    public void eventReservation() {
        if (reserved == 1) {
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            intent.putExtra("beginTime", date);
            intent.putExtra("allDay", true);
            intent.putExtra("title", "Reservation at PEI Deep Sea Fishing");
            startActivity(intent);
        } else {
            Toast.makeText(Activity_Menu.this, "You need to make a reservation to create event!", Toast.LENGTH_LONG).show();
        }
    }

}
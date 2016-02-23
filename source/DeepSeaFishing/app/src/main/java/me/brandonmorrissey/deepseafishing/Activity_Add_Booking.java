package me.brandonmorrissey.deepseafishing;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import me.brandonmorrissey.deepseafishing.sqlite.DatabaseHelper;

/**
 * Created by: Brandon Morrissey
 * Date: 02/07/2016
 * Activity_Add_Booking to take user input and create SQLite entries based on the input
 */
public class Activity_Add_Booking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //SQLite objects
    SQLiteOpenHelper helper;
    SQLiteDatabase db;

    //Views
    Toolbar toolbar;
    Spinner spinner;
    CalendarView calendar;
    Button button;

    //Date & time string
    String date, timeFormatted;
    int customerId;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_booking);
        helper = new DatabaseHelper(this);

        //Intent data
        intent = getIntent();
        customerId = intent.getIntExtra("customerId", 0);

        //View setup
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        spinner = (Spinner) findViewById(R.id.spinner);
        calendar = (CalendarView) findViewById(R.id.calendar);
        final EditText et_fname = (EditText) findViewById(R.id.fname);
        final EditText et_lname = (EditText) findViewById(R.id.lname);
        final EditText et_creditcard = (EditText) findViewById(R.id.creditcard);
        final EditText et_phone = (EditText) findViewById(R.id.phone);
        final EditText et_email = (EditText) findViewById(R.id.email);
        button = (Button) findViewById(R.id.button);
        calendar.setSelected(false);

        //Toolbar View
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle(R.string.add_title);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get writable SQLite database
                db = helper.getWritableDatabase();

                //Get values from views
                String firstName = et_fname.getText().toString();
                String lastName = et_lname.getText().toString();
                String email = et_email.getText().toString();
                String phoneNumber = et_phone.getText().toString();
                String creditCard = et_creditcard.getText().toString();
                String time = spinner.getSelectedItem().toString();

                //Format the spinner value
                switch (time) {
                    case "8PM":
                        timeFormatted = "08:00:00.0";
                        break;
                    case "1PM":
                        timeFormatted = "13:00:00.0";
                        break;
                    case "6PM":
                        timeFormatted = "18:00:00.0";
                        break;
                }

                //Perform some field validation
                if (firstName.equals("") || lastName.equals("") || email.equals("") || phoneNumber.equals("") || creditCard.equals("")) {
                    Toast.makeText(Activity_Add_Booking.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (date == null) {
                    Toast.makeText(Activity_Add_Booking.this, "You must pick a date!", Toast.LENGTH_SHORT).show();
                } else {
                    try {

                        //Add row to the customer table
                        ContentValues cv = new ContentValues();
                        cv.put("customerId", customerId);
                        cv.put("firstName", firstName);
                        cv.put("lastName", lastName);
                        cv.put("creditCard", creditCard);
                        cv.put("phoneNumber", phoneNumber);
                        cv.put("email", email);

                        db.insert("customers", null, cv);
                    } catch (Exception e) {
                        //Log exception and print stack trace
                        Log.e(e.getClass().getName(), e.getMessage(), e);
                    }
                    try {

                        //Add row to the booked table
                        ContentValues cv = new ContentValues();
                        cv.put("customerId", customerId);
                        cv.put("numberOfPassengers", 18);
                        cv.put("cost", 400);
                        cv.put("dateBooked", date + " " + timeFormatted);

                        db.insert("bookings", null, cv);
                    } catch (Exception e) {
                        //Log exception and print stack trace
                        Log.e(e.getClass().getName(), e.getMessage(), e);
                    } finally {
                        //Output that the booking was successful
                        Toast.makeText(Activity_Add_Booking.this, "Booking Created!", Toast.LENGTH_SHORT).show();

                        //Pass back reserved flag
                        intent.putExtra("reserved", 1);
                        intent.putExtra("date", date + " " + timeFormatted);
                        setResult(RESULT_OK, intent);
                        db.close();
                        finish();
                    }

                    db.close();
                }
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //Format and confirm selected date
                date = year + "-" + month + "-" + dayOfMonth;
                Toast.makeText(Activity_Add_Booking.this, "Date selected: " + date, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

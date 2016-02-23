package me.brandonmorrissey.deepseafishing;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.brandonmorrissey.deepseafishing.model.Booking;
import me.brandonmorrissey.deepseafishing.sqlite.DatabaseHelper;
import me.brandonmorrissey.deepseafishing.util.BookingListViewHolder;
import me.brandonmorrissey.deepseafishing.util.BookingRecyclerAdapter;

/**
 * Created by: Brandon Morrissey
 * Date: 02/07/2016
 * Activity_View_Booking to view the contents of the REST api
 */
public class Activity_View_Booking extends AppCompatActivity {

    //SQLite and URL objects
    SQLiteOpenHelper helper;
    HttpURLConnection urlConnection;

    //ArrayList to hold bookings
    ArrayList<Booking> bookings = new ArrayList<>();

    //Views
    RecyclerView recyclerView;
    BookingRecyclerAdapter bookingRecyclerAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);


        //Toolbar View
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle(R.string.view_title);

        //Execute Async Task
        helper = new DatabaseHelper(this);
        new Task().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * AsyncTask to grab REST api data and parse it into an array which will
     * be used to create RecyclerViews for the user
     */
    private class Task extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            int result = 0;
            StringBuilder data = new StringBuilder();

            try {
                //Get JSON data from REST api
                URL url = new URL("http://bjmac.hccis.info:8080/deepseafishing/rest/dataaccess/bookings");
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    //Reading the data with a Buffered Reader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String l;
                    while ((l = reader.readLine()) != null) {
                        data.append(l);
                    }
                    processData(data.toString());
                    //Result successful
                    result = 1;
                } else {
                    //Result unsuccessful
                    result = 0;
                }
            } catch (Exception e) {
                Log.e(e.getClass().getName(), e.getMessage(), e);
            } finally {
                urlConnection.disconnect();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                bookingRecyclerAdapter = new BookingRecyclerAdapter(Activity_View_Booking.this, bookings);
                recyclerView.setAdapter(bookingRecyclerAdapter);
            } else {
                Toast.makeText(Activity_View_Booking.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Process the data being pulled from REST api
     *
     * @param data - Downloaded data to be parsed
     */
    private void processData(String data) {
        //Get JSON data into ArrayList
        try {
            //Get JSON data and SQLite database
            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = new JSONArray(json.optString("bookings"));

            //Loop through JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //Add row to the customer table
                Booking booking = new Booking();

                booking.setCustomerId(jsonObject.optInt("customerId"));
                booking.setNumberOfPassengers(jsonObject.optInt("numberOfPassengers"));
                booking.setCost(jsonObject.optDouble("cost"));
                booking.setDateBooked(jsonObject.optString("dateBooked"));

                //Add object to ArrayList
                bookings.add(booking);
            }
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }
}
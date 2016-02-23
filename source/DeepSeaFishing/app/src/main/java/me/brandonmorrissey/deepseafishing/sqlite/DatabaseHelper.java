package me.brandonmorrissey.deepseafishing.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by: Brandon Morrissey
 * Date: 02/06/2016
 * DatabaseHelper class to do all SQLite work
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Values
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "deepseafishing.db";

    //Customers Table
    public static final String TABLE_NAME_CUSTOMERS = "customers";
    public static final String COLUMN_ID_CUSTOMERS = "_id";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_CREDIT_CARD = "creditCard";
    public static final String COLUMN_PHONE = "phoneNumber";
    public static final String COLUMN_EMAIL = "email";

    //Bookings Table
    public static final String TABLE_NAME_BOOKINGS = "bookings";
    public static final String COLUMN_ID_BOOKINGS = "_id";
    public static final String COLUMN_CUSTOMER_ID = "customerId";
    public static final String COLUMN_NUMBER_OF_PASSENGERS = "numberOfPassengers";
    public static final String COLUMN_DATE = "dateBooked";
    public static final String COLUMN_COST = "cost";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * On create to check to see if tables exist, and create SQLite
     * tables to store information for bookings and customers
     *
     * @param db - Database object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create customers table
        String createCustomersTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CUSTOMERS + " ( " +
                COLUMN_ID_CUSTOMERS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CUSTOMER_ID + " int(5) DEFAULT NULL," +
                COLUMN_FIRST_NAME + " varchar(35) DEFAULT NULL," +
                COLUMN_LAST_NAME + " varchar(35) DEFAULT NULL," +
                COLUMN_CREDIT_CARD + " varchar(12) DEFAULT NULL," +
                COLUMN_PHONE + " varchar(10) DEFAULT NULL," +
                COLUMN_EMAIL + " varchar(100) DEFAULT NULL" +
                "); ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        db.execSQL(createCustomersTableQuery);

        //Create bookings table
        String createBookingsTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BOOKINGS + " ( " +
                COLUMN_ID_BOOKINGS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CUSTOMER_ID + " int(5) DEFAULT NULL," +
                COLUMN_NUMBER_OF_PASSENGERS + " int(2) DEFAULT NULL," +
                COLUMN_COST + " float DEFAULT NULL," +
                COLUMN_DATE + " varchar(100) DEFAULT NULL" +
                "); ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        db.execSQL(createBookingsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No present use...
    }

    /**
     * Method to be called when adding a new row to the Customer table
     * in the SQLite database.
     *
     * @param db       - Database object
     * @param cc       - Creditcard column
     * @param lname    - Last Name column
     * @param fname    - First Name column
     * @param phonenum - Phone number column
     * @param email    - Email column
     */
    public void addCustomerRow(SQLiteDatabase db, int cc, String lname, String fname, int phonenum, String email) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CREDIT_CARD, cc);
        values.put(COLUMN_CUSTOMER_ID, 0);
        values.put(COLUMN_LAST_NAME, lname);
        values.put(COLUMN_FIRST_NAME, fname);
        values.put(COLUMN_PHONE, phonenum);
        values.put(COLUMN_EMAIL, email);

        db.insert(TABLE_NAME_CUSTOMERS, null, values);
        db.close();
    }

    /**
     * Method to be called when adding a new row to the Booking table
     * in the SQLite database.
     *
     * @param db            - Database object
     * @param cid           - Customer ID column
     * @param numpassengers - Number of Passengers column
     * @param date          - Date Column
     * @param cost          - Cost Column
     */
    public void addBookingRow(SQLiteDatabase db, String cid, int numpassengers, String date, int cost) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_ID, cid);
        values.put(COLUMN_NUMBER_OF_PASSENGERS, numpassengers);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_COST, cost);

        db.insert(TABLE_NAME_BOOKINGS, null, values);
        db.close();
    }

    /**
     * Method to be called when trying to pull from a database to view
     * the contents
     *
     * @param db - Database object
     * @return - TBD
     */
    public String viewBookingRow(SQLiteDatabase db) {

        String dbString = "";
        db = getWritableDatabase();
        String pullBookingQuery = "SELECT * FROM " + TABLE_NAME_BOOKINGS + " WHERE 1;";

        Cursor c = db.rawQuery(pullBookingQuery, null);
        c.moveToFirst();

        long id;
        int customerId;
        int numberOfPassengers;
        String date;
        float cost;

        while (!c.isAfterLast()) {
            id = c.getLong(0);
            customerId = c.getInt(1);
            numberOfPassengers = c.getInt(2);
            date = c.getString(3);
            cost = c.getFloat(4);
        }

        c.close();
        db.close();
        return dbString;
    }

    /**
     * When called, pass in the db object and table name to remove
     * all the contents of the table
     *
     * @param db        - Database object
     * @param tableName - The name of the table to clear
     */
    public void removeAll(SQLiteDatabase db, String tableName) {
        db.delete(tableName, "1", null);
    }
}
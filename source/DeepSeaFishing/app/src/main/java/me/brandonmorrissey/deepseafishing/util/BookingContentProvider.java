package me.brandonmorrissey.deepseafishing.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import me.brandonmorrissey.deepseafishing.sqlite.DatabaseHelper;

public class BookingContentProvider extends ContentProvider {

    private static final String AUTH = " me.brandonmorrissey.deepseafishing.util.BookingContentProvider";
    public static final Uri BOOKING_URI = Uri.parse("content://" + AUTH + "/" + DatabaseHelper.TABLE_NAME_BOOKINGS);
    private static final int COMMENT = 1;

    SQLiteDatabase db;
    DatabaseHelper helper;
    Cursor cursor;

    private static final UriMatcher matcher;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTH, DatabaseHelper.TABLE_NAME_BOOKINGS, COMMENT);
    }

    public BookingContentProvider() {
    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db = helper.getReadableDatabase();

        if (matcher.match(uri) == COMMENT) {
            cursor = db.query(DatabaseHelper.TABLE_NAME_BOOKINGS, projection, selection, selectionArgs, null, null, sortOrder);
        }

        db.close();
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        helper.getWritableDatabase();

        if (matcher.match(uri) == COMMENT) {
            db.insert(DatabaseHelper.TABLE_NAME_BOOKINGS, null, values);
        }

        db.close();
        return null;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

package AppDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static AppDatabase.StoppageContract.FeedEntry.*;

public class StoppagesDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    Context context;
    protected static final String DATABASE_NAME = "stoppage_description";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StoppageContract.FeedEntry.TABLE_NAME + " (" +
                    StoppageContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_BUS_NAME + " TEXT," +
                    StoppageContract.FeedEntry.COLUMN_NAME_ARRIVAL_TIME + " TEXT," +
                    StoppageContract.FeedEntry.COLUMN_NAME_ROUTE_NAME + " TEXT," +
                    StoppageContract.FeedEntry.COLUMN_NAME_STOPPAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StoppageContract.FeedEntry.TABLE_NAME;

    public StoppagesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void addStoppages(StoppageDescription stoppageDescription){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_BUS_NAME, stoppageDescription.getBusName());
        values.put(COLUMN_NAME_ARRIVAL_TIME, stoppageDescription.getArrivalTime());
        values.put(COLUMN_NAME_ROUTE_NAME, stoppageDescription.getRouteName());
        values.put(COLUMN_NAME_STOPPAGE, stoppageDescription.getStoppage());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public StoppageDescription getStoppages(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                COLUMN_NAME_BUS_NAME,
                COLUMN_NAME_ARRIVAL_TIME,
                COLUMN_NAME_ROUTE_NAME,
                COLUMN_NAME_STOPPAGE
        };

      //  String selection = COLUMN_NAME_TITLE + " = ?";
       // String[] selectionArgs = { "My Title" };

      //  String sortOrder =
           //     FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        // 2. build query
        Cursor cursor =
                db.query(TABLE_NAME, // a. table
                        projection, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        StoppageDescription stoppageDescription = new StoppageDescription();
        stoppageDescription.setId(Integer.parseInt(cursor.getString(0)));
        stoppageDescription.setBusName(cursor.getString(1));
        stoppageDescription.setArrivalTime(cursor.getString(2));
        stoppageDescription.setRouteName(cursor.getString(3));
        stoppageDescription.setStoppage(cursor.getString(4));

        //log

        // 5. return book
        return stoppageDescription;
    }

    public ArrayList<StoppageDescription> getAllBooks(String query) {
        ArrayList<StoppageDescription> stoDes = new ArrayList<>();

        // 1. build the quer
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        StoppageDescription stoppageDescription = null;
        if (cursor.moveToFirst()) {
            do {
                stoppageDescription = new StoppageDescription();
                stoppageDescription.setId(Integer.parseInt(cursor.getString(0)));
                stoppageDescription.setBusName(cursor.getString(1));
                stoppageDescription.setArrivalTime(cursor.getString(2));
                stoppageDescription.setRouteName(cursor.getString(3));
                stoppageDescription.setStoppage(cursor.getString(4));
                // Add book to books
                stoDes.add(stoppageDescription);
            } while (cursor.moveToNext());
        }

        // return books
        return stoDes;
    }
}

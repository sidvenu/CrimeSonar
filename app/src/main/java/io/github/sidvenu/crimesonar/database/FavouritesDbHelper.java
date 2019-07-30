package io.github.sidvenu.crimesonar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Parcel;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.sidvenu.crimesonar.ParcelableUtil;
import io.github.sidvenu.crimesonar.models.Crime;

public class FavouritesDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Favourites.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavouritesContract.FavouriteEntry.TABLE_NAME + " (" +
                    FavouritesContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY," +
                    FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME + " BLOB," +
                    FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME_ID + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavouritesContract.FavouriteEntry.TABLE_NAME;

    private static SQLiteDatabase database;

    public FavouritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        AsyncTask.execute(() -> {
            database = getWritableDatabase();
        });
    }

    public List<String> getCrimeIDs() {
        Cursor cursor = database.query(
                FavouritesContract.FavouriteEntry.TABLE_NAME,
                new String[]{FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME_ID},
                null,
                null,
                null,
                null,
                null
        );
        List<String> crimeIDs = new ArrayList<>();
        while (cursor.moveToNext()) {
            String crimeID = cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME_ID)
            );
            crimeIDs.add(crimeID);
        }
        cursor.close();
        return crimeIDs;
    }

    public List<Crime> getCrimes() {
        Cursor cursor = database.query(
                FavouritesContract.FavouriteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        List<Crime> crimes = new ArrayList<>();
        while (cursor.moveToNext()) {
            byte[] crimeByteArray = cursor.getBlob(
                    cursor.getColumnIndexOrThrow(FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME)
            );
            Log.v("TAG", "Get: " + Arrays.toString(crimeByteArray));
            Parcel parcel = ParcelableUtil.unmarshall(crimeByteArray);
            crimes.add((Crime) Crime.CREATOR.createFromParcel(parcel));
//            Parcel parcel = Parcel.obtain();
//            parcel.writeByteArray(crimeByteArray);
//            crimes.add((Crime) Crime.CREATOR.createFromParcel(parcel));
            parcel.recycle();
        }
        cursor.close();
        return crimes;
    }

    public void insertCrime(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME_ID, crime.getId());
//        Parcel parcel = Parcel.obtain();
//        crime.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        byte[] crimeBytes = ParcelableUtil.marshall(crime);
        Log.v("TAG", "Insert: " + Arrays.toString(crimeBytes));
        values.put(FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME, crimeBytes);
//        parcel.recycle();

        database.insert(FavouritesContract.FavouriteEntry.TABLE_NAME, null, values);
    }

    public void deleteCrime(String crimeID) {
        String selection = FavouritesContract.FavouriteEntry.COLUMN_NAME_CRIME_ID + " LIKE ?";
        String[] selectionArgs = {crimeID};

        database.delete(FavouritesContract.FavouriteEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}

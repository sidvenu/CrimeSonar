package io.github.sidvenu.crimesonar.database;

import android.provider.BaseColumns;

public final class FavouritesContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FavouritesContract() {}

    /* Inner class that defines the table contents */
    public static class FavouriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_NAME_CRIME_ID = "crime_id";
        public static final String COLUMN_NAME_CRIME = "crime";
    }
}

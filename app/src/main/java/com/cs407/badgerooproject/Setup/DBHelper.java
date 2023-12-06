package com.cs407.badgerooproject.Setup;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs407.badgerooproject.Home.Roommate;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserDatabase";

    // User table name
    private static final String TABLE_USER = "users";

    // User Table Columns names
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FULL_NAME = "fullName";
    private static final String COLUMN_PROFILE_PICTURE = "profilePicture";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_BIO = "bio";
    private static final String COLUMN_MIN_RENT = "minRent";
    private static final String COLUMN_MAX_RENT = "maxRent";
    private static final String COLUMN_NUM_ROOMMATES = "numRoommates";
    private static final String COLUMN_ROOMMATE_GENDER = "roommateGender";
    private static final String COLUMN_Latitude = "latitude";
    private static final String COLUMN_Longitude = "Longitude";

    private static final String COLUMN_DESIRED_LOCATION = "desiredLocation";
    private static final String COLUMN_HOUSING_STYLE = "housingStyle";
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_END_DATE = "endDate";

    // SQL to create the users table
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY," // Assuming email as primary key
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_FULL_NAME + " TEXT,"
            + COLUMN_PROFILE_PICTURE + " TEXT," // Store image path or URL
            + COLUMN_AGE + " INTEGER,"
            + COLUMN_GENDER + " TEXT,"
            + COLUMN_BIO + " TEXT,"
            + COLUMN_MIN_RENT + " REAL," // Real for decimal values
            + COLUMN_MAX_RENT + " REAL,"
            + COLUMN_NUM_ROOMMATES + " INTEGER,"
            + COLUMN_ROOMMATE_GENDER + " TEXT,"
            + COLUMN_DESIRED_LOCATION + " TEXT," // Coordinates as text or two separate real columns
            + COLUMN_HOUSING_STYLE + " TEXT,"
            + COLUMN_START_DATE + " TEXT," // Text for dates or use INTEGER for timestamps
            + COLUMN_END_DATE + " TEXT" + ")";

    // SQL to drop the users table
    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public boolean insertLoginCredentials(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password); // Consider hashing the password

        long newRowId = db.insert(TABLE_USER, null, values);
        db.close();
        return newRowId != -1;
    }

    public boolean insertBasicDetails(String email, String fullName, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_GENDER, gender);

        long newRowId = db.update(TABLE_USER, values, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();
        return newRowId != -1;
    }

    public boolean updateBioAndPicture(String email, String bio, String profilePicture) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating key (column name) value (user input) pair for later insertion
        ContentValues values = new ContentValues();
        values.put(COLUMN_BIO, bio);
        values.put(COLUMN_PROFILE_PICTURE, profilePicture);

        //specify which row to update via email. Return the numbers of rows that were updated.
        int numOfRowsUpdated = db.update(TABLE_USER, values, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();
        return numOfRowsUpdated > 0;
    }

    public boolean updatePreferences(String email, double minRent, double maxRent, int numRoommates,
                                         String roommateGender, String housingStyle,
                                         String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MIN_RENT, minRent);
        values.put(COLUMN_MAX_RENT, maxRent);
        values.put(COLUMN_NUM_ROOMMATES, numRoommates);
        values.put(COLUMN_ROOMMATE_GENDER, roommateGender);
        //values.put(COLUMN_DESIRED_LOCATION, desiredLocation);
        values.put(COLUMN_HOUSING_STYLE, housingStyle);
        values.put(COLUMN_START_DATE, startDate);
        values.put(COLUMN_END_DATE, endDate);

        int numOfRowsUpdated = db.update(TABLE_USER, values, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();
        return numOfRowsUpdated > 0;
    }

    public ArrayList<Roommate> fetchUsers() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from users", null);

        int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
        int fullNameIndex = cursor.getColumnIndex(COLUMN_FULL_NAME);
        int profilePictureIndex = cursor.getColumnIndex(COLUMN_PROFILE_PICTURE);
        int ageIndex = cursor.getColumnIndex(COLUMN_AGE);
        int genderIndex = cursor.getColumnIndex(COLUMN_GENDER);
        int bioIndex = cursor.getColumnIndex(COLUMN_BIO);
        int minRentIndex = cursor.getColumnIndex(COLUMN_MIN_RENT);
        int maxRentIndex = cursor.getColumnIndex(COLUMN_MAX_RENT);
        int numRoommatesIndex = cursor.getColumnIndex(COLUMN_NUM_ROOMMATES);
        int roommateGenderIndex = cursor.getColumnIndex(COLUMN_ROOMMATE_GENDER);
        int locationIndex = cursor.getColumnIndex(COLUMN_DESIRED_LOCATION);
        int housingIndex = cursor.getColumnIndex(COLUMN_HOUSING_STYLE);
        int startDateIndex = cursor.getColumnIndex(COLUMN_START_DATE);
        int endDateIndex = cursor.getColumnIndex(COLUMN_END_DATE);

        cursor.moveToFirst();

        ArrayList<Roommate> roommates = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            String email = cursor.getString(emailIndex);
            String fullName = cursor.getString(fullNameIndex);
            String profilePicture = cursor.getString(profilePictureIndex);
            int age = cursor.getInt(ageIndex);
            String gender = cursor.getString(genderIndex);
            String bio = cursor.getString(bioIndex);
            double minRent = cursor.getDouble(minRentIndex);
            double maxRent = cursor.getDouble(maxRentIndex);
            int numRoommates = cursor.getInt(numRoommatesIndex);
            String roommateGender = cursor.getString(roommateGenderIndex);
            String desiredLocation = cursor.getString(locationIndex);
            String housingStyle = cursor.getString(housingIndex);
            String startDate = cursor.getString(startDateIndex);
            String endDate = cursor.getString(endDateIndex);

            roommates.add(new Roommate(email, fullName, profilePicture, age, gender, bio,
                    minRent, maxRent, numRoommates, roommateGender, desiredLocation,
                    housingStyle, startDate, endDate));

            cursor.moveToNext();
        }

        cursor.close();

        return roommates;
    }

}


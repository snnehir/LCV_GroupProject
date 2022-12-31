package com.example.lcv_project.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lcv_project.models.User;


public class DBAdapter {
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "Lcv_Project";
    static final int DATABASE_VERSION = 1;

    // tables

    static final String DATABASE_USER_TABLE = "users";
    static final String DATABASE_FRIEND_TABLE = "friends";
    static final String DATABASE_WEDDING_TABLE = "weddings";
    static final String DATABASE_WEDDING_GUEST_TABLE = "wedding_guests";
    static final String DATABASE_WEDDING_OWNER_TABLE = "wedding_owners";

    // create queries for each table:

    static final String DATABASE_CREATE_USER =
                    "    create table " + DATABASE_USER_TABLE +
                    "    (_id integer primary key autoincrement, full_name text, username text unique, " +
                    "    mail text unique, password text)";
    static final String DATABASE_CREATE_FRIEND =
                    "    CREATE TABLE " + DATABASE_FRIEND_TABLE +
                    "    (_id INTEGER PRIMARY KEY autoincrement,\n" +
                    "    user_id INTEGER NOT NULL,\n" +
                    "    friend_id INTEGER NOT NULL,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES users(id),\n" +
                    "    FOREIGN KEY (friend_id) REFERENCES users(id))";
    static final String DATABASE_CREATE_WEDDING =
                    "    create table " + DATABASE_WEDDING_TABLE +
                    "    (_id integer primary key autoincrement, wedding_name text, " +
                    "    weddingLocation text, weddingDetails text, bride text, groom text, " +
                    "    invitationImg text, table_capacity integer, accompanier integer, wedding_start date, wedding_end date)";
    static final String DATABASE_CREATE_WEDDING_GUEST =
                    "    CREATE TABLE " + DATABASE_WEDDING_GUEST_TABLE +
                    "    (_id INTEGER PRIMARY KEY autoincrement,\n" +
                    "    user_id INTEGER NOT NULL,\n" +
                    "    wedding_id INTEGER NOT NULL,\n" +
                    "    people_bring INTEGER,\n" +
                    "    will_come TINYINT,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES users(_id),\n" +
                    "    FOREIGN KEY (wedding_id) REFERENCES weddings(_id) )";
    static final String DATABASE_CREATE_WEDDING_OWNER =
                    "    CREATE TABLE " + DATABASE_WEDDING_OWNER_TABLE +
                    "    (_id INTEGER PRIMARY KEY autoincrement,\n" +
                    "    user_id INTEGER NOT NULL,\n" +
                    "    wedding_id INTEGER NOT NULL,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES users(_id),\n" +
                    "    FOREIGN KEY (wedding_id) REFERENCES weddings(_id) );";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE_USER);
                db.execSQL(DATABASE_CREATE_FRIEND);
                db.execSQL(DATABASE_CREATE_WEDDING);
                db.execSQL(DATABASE_CREATE_WEDDING_GUEST);
                db.execSQL(DATABASE_CREATE_WEDDING_OWNER);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version"  + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_USER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_FRIEND_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_WEDDING_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_WEDDING_GUEST_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_WEDDING_OWNER_TABLE);
            onCreate(db);
        }
    }

    // opens the database
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    // closes the database
    public void close()
    {
        DBHelper.close();
    }

    // check if same username or email is used before
    public boolean checkUsernameOrMailExist(String mail, String username){
        String query = "select * from " + DATABASE_USER_TABLE + " where (username=? or mail=?)";
        Cursor cursor = db.rawQuery(query, new String[]{mail, username});
        boolean flag = cursor.moveToFirst();
        cursor.close();
        return flag;
    }
    // insert a user
    public long addUser(User user)
    {
        if(checkUsernameOrMailExist(user.getUsername(), user.getMail())){
            Log.e("Database Error", "SQLiteConstraintException: username or mail should be unique.");
            return -1;
        }
        ContentValues initialValues = new ContentValues();
        initialValues.put("full_name", user.getFull_name());
        initialValues.put("username", user.getUsername());
        initialValues.put("mail", user.getMail());
        initialValues.put("password", user.getPassword());
        return db.insert(DATABASE_USER_TABLE, null, initialValues);
    }
    // check whether user login params are matching
    public User loginUser(String username_or_mail, String password){
        String query = "select * from " + DATABASE_USER_TABLE + " where (username=? or mail=?) and password=?";
        Cursor cursor = db.rawQuery(query, new String[]{username_or_mail, username_or_mail, password});
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3), cursor.getString(4) );
            System.out.println(" ====================== login user: " + user);
            cursor.close();
        }
        else{
            System.out.println(" ====================== login user: null ");
        }
        return user;
    }

    // clears all user data
    public void clearTable() throws SQLException
    {
        db.execSQL("DELETE FROM " + DATABASE_USER_TABLE);
    }
}

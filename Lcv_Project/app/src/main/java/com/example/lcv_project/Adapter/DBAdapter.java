package com.example.lcv_project.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lcv_project.models.User;
import com.example.lcv_project.models.UserListItem;

import java.util.ArrayList;


public class DBAdapter {
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "Lcv_Project";
    static final int DATABASE_VERSION = 2;

    // tables

    static final String DATABASE_USER_TABLE = "users";
    static final String DATABASE_FRIEND_TABLE = "friends";
    static final String DATABASE_WEDDING_TABLE = "weddings";
    static final String DATABASE_WEDDING_GUEST_TABLE = "wedding_guests";
    static final String DATABASE_WEDDING_OWNER_TABLE = "wedding_owners";

    // create queries for each table:

    static final String DATABASE_CREATE_USER =
                    "    create table " + DATABASE_USER_TABLE +
                    "    (_id integer primary key autoincrement, " +
                    "    full_name text, " +
                    "    username text unique not null, " +
                    "    mail text unique not null, " +
                    "    password text not null, " +
                    "    profile_img_url text)";
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
                            cursor.getString(2), cursor.getString(3), cursor.getString(4), "");
            System.out.println(" ====================== login user: " + user);
            cursor.close();
        }
        else{
            System.out.println(" ====================== login user: null ");
        }
        return user;
    }

    public ArrayList<UserListItem> getSearchedUsers(String filter, boolean friends_only, int user_id){
        String f = filter.isEmpty() ? "'%%'" : "'%" + filter + "%'"; // todo: string builder
        String query =  "SELECT u.full_name, u.username, u.mail, u.profile_img_url, " +
                        "       CASE\n" +
                        "           WHEN f.user_id IS NOT NULL THEN 1\n" +
                        "           ELSE 0\n" +
                        "       END AS is_friend\n" +
                        "FROM " + DATABASE_USER_TABLE + " u\n" +
                        "LEFT JOIN " + DATABASE_FRIEND_TABLE + " f\n" +
                        "    ON u._id = f.friend_id\n" +
                        "    AND f.user_id = ? \n WHERE u._id <> ? and u.full_name like " + f;
        if(friends_only){
            query += " and is_friend = 1";
        }
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id), String.valueOf(user_id)});

        ArrayList<UserListItem> userList = new ArrayList<>();
        // loop through the result set and add each row to the list
        while (cursor.moveToNext()) {
            // System.out.println("============= elements: " + cursor.getString(0));
            userList.add(new UserListItem(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            ));
        }
        return userList;

    }

    // clears all user data
    public void clearTable() throws SQLException
    {
        db.execSQL("DELETE FROM " + DATABASE_USER_TABLE);
        db.execSQL("DELETE FROM " + DATABASE_FRIEND_TABLE);
    }

    // creates fake data
    public void createFakeData() throws SQLException{
        db.execSQL("INSERT INTO users (_id, username, password, mail, full_name, profile_img_url)\n" +
                "VALUES \n" +
                "  (1, 'hkbyrktr', 'password', 'user1@example.com', 'Hatice Kubra Bayraktar', null),\n" +
                "  (2, 'selcans', 'password', 'user2@example.com', 'Selcan Sarıarslan', null),\n" +
                "  (3, 'muq_the_star', 'password', 'user3@example.com', 'Muqadasa Sherani', null),\n" +
                "  (4, 'mumbi_r', 'password', 'user4@example.com', 'Regina Mumbi Gachomba', null),\n" +
                "  (5, 'snur21', 'qwerty', 'snur21@mail.com', 'Sumeyye Nur Nehir', null),\n" +
                "  (6, 'theq', 'password', 'user6@example.com', 'Ji Chang Min', null),\n" +
                "  (7, 'm_akbas', 'password', 'user7@example.com', 'Mustafa Akbaş', null),\n" +
                "  (8, 'jia', 'password', 'user8@example.com', 'Jia Chang', null),\n" +
                "  (9, 'serhan35', 'password', 'user9@example.com', 'Serhan Günaç', null),\n" +
                "  (10, 'hjkim99', 'password', 'user10@example.com', 'Hongjoong Kim', null),\n" +
                "  (11, 'mr_chalamet', 'password', 'user11@example.com', 'Timothée Chalamet', 'timothee');\n");
        db.execSQL("INSERT INTO friends (_id, user_id, friend_id) values (1, 5, 1), (2, 5, 2), (3, 5, 3), (4, 5, 4)");
    }
}

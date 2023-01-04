package com.example.lcv_project.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lcv_project.Models.User;
import com.example.lcv_project.Models.UserListItem;
import com.example.lcv_project.Models.Wedding;

import java.util.ArrayList;


public class DBAdapter {
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "Lcv_Project";
    static final int DATABASE_VERSION = 5;

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
                    "    (_id integer primary key autoincrement, " +
                    "    wedding_name text unique not null, " +
                    "    wedding_location text, wedding_details text, bride text, groom text, " +
                    "    accompanier integer," +
                    "    wedding_start date, wedding_end date)";
    static final String DATABASE_CREATE_WEDDING_GUEST =
                    "    CREATE TABLE " + DATABASE_WEDDING_GUEST_TABLE +
                    "    (_id INTEGER PRIMARY KEY autoincrement,\n" +
                    "    user_id INTEGER NOT NULL,\n" +
                    "    wedding_id INTEGER NOT NULL,\n" +
                    "    accompanier INTEGER,\n" +
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
            System.out.println(" ******************************** VER. CHANGED **********************************");
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

    // insert a wedding
    public void addWedding(Wedding wedding, int user_id){
        // add wedding
        ContentValues initialValues = new ContentValues();
        initialValues.put("bride", wedding.getBride());
        initialValues.put("groom", wedding.getGroom());
        initialValues.put("wedding_name", wedding.getWedding_name());
        initialValues.put("wedding_location", wedding.getWedding_location());
        initialValues.put("wedding_details", wedding.getWedding_details());
        initialValues.put("accompanier", wedding.getAccompanier_num());
        initialValues.put("wedding_start", wedding.getWeddingStart());
        initialValues.put("wedding_end", wedding.getWeddingEnd());
        System.out.println(" ================ wedding added ==================");
        System.out.println(wedding);
        // add wedding-owner relation
        long wedding_id = db.insert(DATABASE_WEDDING_TABLE, null, initialValues);
        ContentValues initialValues2 = new ContentValues();
        initialValues2.put("user_id", String.valueOf(user_id));
        initialValues2.put("wedding_id", String.valueOf(wedding_id));
        db.insert(DATABASE_WEDDING_OWNER_TABLE, null, initialValues2);

        System.out.println(" ================ wedding owner added ==================");

        String query_wo = "select wedding_id, user_id from " + DATABASE_WEDDING_OWNER_TABLE;
        Cursor cursor_wo = db.rawQuery(query_wo, null);
        System.out.println(" ======================== WEDDING OWNER TABLE add ======================= ");
        while (cursor_wo.moveToNext()) {
            System.out.println("WEDDING ID: " + cursor_wo.getInt(0));
            System.out.println("USER ID: " + cursor_wo.getInt(1));
        }

        String query_w = "select _id, wedding_name from " + DATABASE_WEDDING_TABLE;
        Cursor cursor_w = db.rawQuery(query_w, null);
        System.out.println(" ======================== WEDDING TABLE add ======================= ");
        while (cursor_w.moveToNext()) {
            System.out.println("WEDDING ID: " + cursor_w.getInt(0));
            System.out.println("WEDDING NAME: " + cursor_w.getString(1));
        }

    }
    // check whether user login params are matching
    public User loginUser(String username_or_mail, String password){
        String query = "select * from " + DATABASE_USER_TABLE + " where (username=? or mail=?) and password=?";
        Cursor cursor = db.rawQuery(query, new String[]{username_or_mail, username_or_mail, password});
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3), cursor.getString(4));
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
        cursor.close();
        return userList;

    }

    public ArrayList<Wedding> getWeddingsOfUser(int user_id){
        String query_wo = "select wedding_id, user_id from " + DATABASE_WEDDING_OWNER_TABLE;
        Cursor cursor_wo = db.rawQuery(query_wo, null);
        System.out.println(" ======================== WEDDING OWNER TABLE ======================= ");
        while (cursor_wo.moveToNext()) {
            System.out.println("WEDDING ID: " + cursor_wo.getInt(0));
            System.out.println("USER ID: " + cursor_wo.getInt(1));
        }

        String query_w = "select _id, wedding_name from " + DATABASE_WEDDING_TABLE;
        Cursor cursor_w = db.rawQuery(query_w, null);
        System.out.println(" ======================== WEDDING TABLE ======================= ");
        while (cursor_w.moveToNext()) {
            System.out.println("WEDDING ID: " + cursor_w.getInt(0));
            System.out.println("WEDDING NAME: " + cursor_w.getString(1));
        }
        String query = " select w._id, w.bride, w.groom, w.wedding_name, w.wedding_location, w.wedding_details, " +
                " w.accompanier, w.wedding_start, w.wedding_end from " + DATABASE_WEDDING_TABLE + " as w left join " + DATABASE_WEDDING_OWNER_TABLE
                + " as wo on w._id = wo.wedding_id where wo.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
        ArrayList<Wedding> weddings = new ArrayList<>();
        // loop through the result set and add each row to the list
        while (cursor.moveToNext()) {
            weddings.add(new Wedding(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)
            ));
        }
        cursor.close();
        return weddings;
    }

    // clears all user data
    public void clearTable() throws SQLException
    {
        db.execSQL("DELETE FROM " + DATABASE_USER_TABLE);
        db.execSQL("DELETE FROM " + DATABASE_FRIEND_TABLE);
        db.execSQL("DELETE FROM " + DATABASE_WEDDING_TABLE);
        db.execSQL("DELETE FROM " + DATABASE_WEDDING_OWNER_TABLE);
        db.execSQL("DELETE FROM " + DATABASE_WEDDING_GUEST_TABLE);

    }

    // creates fake data
    public void createFakeData() throws SQLException{
        db.execSQL("INSERT INTO users (_id, username, password, mail, full_name, profile_img_url)\n" +
                "VALUES \n" +
                "  (1, 'hkbyrktr', 'password', 'user1@example.com', 'Hatice Kubra Bayraktar', null),\n" +
                "  (2, 'selcans', 'password', 'user2@example.com', 'Selcan Sarıarslan', null),\n" +
                "  (3, 'muq_the_star', 'password', 'user3@example.com', 'Muqadasa Sherani', null),\n" +
                "  (4, 'mumbi_r', 'password', 'user4@example.com', 'Regina Mumbi Gachomba', null),\n" +
                "  (5, 'snur21', '1234', 'snur21@mail.com', 'Sumeyye Nur Nehir', null),\n" +
                "  (6, 'theq', 'password', 'user6@example.com', 'Ji Chang Min', null),\n" +
                "  (7, 'm_akbas', 'password', 'user7@example.com', 'Mustafa Akbaş', null),\n" +
                "  (8, 'jia', 'password', 'user8@example.com', 'Jia Chang', null),\n" +
                "  (9, 'serhan35', 'password', 'user9@example.com', 'Serhan Günaç', null),\n" +
                "  (10, 'hjkim99', 'password', 'user10@example.com', 'Hongjoong Kim', null),\n" +
                "  (11, 'mr_chalamet', 'password', 'user11@example.com', 'Timothée Chalamet', 'timothee');\n");
        db.execSQL("INSERT INTO friends (_id, user_id, friend_id) values (1, 5, 1), (2, 5, 2), (3, 5, 3), (4, 5, 4)");
    }

    //create fake wedding data
    public void createFakeWeddingData(){
        db.execSQL("INSERT INTO "+DATABASE_WEDDING_TABLE+" (_id, wedding_name, wedding_location, wedding_details, bride, groom, accompanier, wedding_start, wedding_end)\n"+
                "VALUES \n" +
                "(1, 'Mehmet and Ayse', 'Ankara', 'No pets and non-alcohalic wedding', 'Ayse Bayraktar', 'Mehmet Burak', 1, 2023-01-20, 2023-01-21),\n" +
                "(2, 'Cansu and Tayfur', 'Izmir', 'Alcohalic wedding, black and white theme', 'Cansu Bahar', 'Tayfur Sen', 2, 2023-06-01, 2023-06-05),\n" +
                "(3, 'Feyza and Koray', 'Istanbul', 'Non-alcohalic wedding, No children', 'Feyza Korkmaz', 'Koray Celik', 0, 2023-05-25, 2023-05-26);\n");
    }

    //create fake wedding owner data
    public void createFakeWeddingOwner(){
        db.execSQL("INSERT INTO "+DATABASE_WEDDING_OWNER_TABLE+" (_id, user_id, wedding_id)\n"+  //user_id means the owner's id, the one who created the wedding event.
                "VALUES \n" +
                "(1, 2, 3),\n" +
                "(2, 5, 2),\n" +
                "(3, 1, 1);\n");
    }

    //create fake wedding guests data
    public void createFakeWeddingGuests(){
        db.execSQL("INSERT INTO "+DATABASE_WEDDING_GUEST_TABLE+" (_id, user_id, wedding_id, accompanier, will_come)\n"+ //user_id means the guests that haas an invitations.
                "VALUES \n" +
                "(1, 2, 3, 1, 1),\n" +  //will_come --> if 0 = not attending, if 1 = attending.
                "(2, 5, 2, 2, 1),\n" +
                "(3, 5, 1, 2, 1),\n" +
                "(4, 5, 3, 2, 1),\n" +
                "(5, 3, 3, 0, 0),\n" +
                "(6, 4, 2, 1, 1),\n" +
                "(7, 6, 1, 2, 1),\n" +
                "(8, 7, 2, 1, 1),\n" +
                "(9, 8, 3, 0, 0),\n" +
                "(10, 9, 2, 1, 1),\n" +
                "(12, 10, 1, 2, 0),\n" +
                "(13, 11, 3, 2, 1),\n" +
                "(11, 1, 1, 1, 1);\n");
    }

    //get the invited guests of a wedding
    public ArrayList<Wedding> getInvitedWeddings(int user_id){
        ArrayList<Wedding> weddingName = new ArrayList<>();

        //select w.* from wedding as w left join wedding_guest as wg on w._id = wg.wedding_id where user_id= ?

        String query_wo = "select user_id, wedding_id from " + DATABASE_WEDDING_GUEST_TABLE;
        Cursor cursor_wo = db.rawQuery(query_wo, null);
        System.out.println(" ======================== WEDDING TABLE ======================= ");
        while (cursor_wo.moveToNext()) {
            System.out.println("WEDDING ID: " + cursor_wo.getInt(0));
            System.out.println("USER ID: " + cursor_wo.getInt(1));
        }

        String query_w = "select _id, wedding_name from " + DATABASE_WEDDING_TABLE;
        Cursor cursor_w = db.rawQuery(query_w, null);
        System.out.println(" ======================== WEDDING TABLE ======================= ");
        while (cursor_w.moveToNext()) {
            System.out.println("WEDDING ID: " + cursor_w.getInt(0));
            System.out.println("WEDDING NAME: " + cursor_w.getString(1));
        }
        String query = " select w._id, w.bride, w.groom, w.wedding_name, w.wedding_location, w.wedding_details, " +
                " w.accompanier, w.wedding_start, w.wedding_end from " + DATABASE_WEDDING_TABLE + " as w left join " + DATABASE_WEDDING_GUEST_TABLE
                + " as wg on w._id = wg.wedding_id where wg.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
        // loop through the result set and add each row to the list
        while (cursor.moveToNext()) {
            Wedding wedding = new Wedding(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );
            //weddingName.add(wedding.getWedding_name());
            weddingName.add(wedding);
        }
        cursor.close();

        return weddingName;
    }

}

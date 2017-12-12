package ca.goldenphoenicks.lockerauto.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "lockerautomation";

    // Contacts table name
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ACTIONS = "actions";

    // Contacts Table Columns names
    private static final String KEY_UID = "user_id";
    private static final String KEY_UNAME = "user_name";
    private static final String KEY_UPIN = "user_pin";

    private static final String KEY_PID = "product_id";
    private static final String KEY_PNAME = "product_name";
    private static final String KEY_PTYPE = "product_type";
    private static final String KEY_PSTATUS = "product_status";

    private static final String KEY_AID = "action_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_PID + " INTEGER PRIMARY KEY," + KEY_PNAME + " TEXT,"
                + KEY_PTYPE + " CHAR(5)," + KEY_PSTATUS + " CHAR(5))";
        db.execSQL(CREATE_PRODUCT_TABLE);
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + KEY_UID + " INTEGER PRIMARY KEY," + KEY_UNAME + " TEXT," + KEY_UPIN + " INTEGER)";
        db.execSQL(CREATE_USER_TABLE);
        String CREATE_ACTION_TABLE = "CREATE TABLE " + TABLE_ACTIONS + "(" + KEY_AID + " INTEGER PRIMARY KEY," + KEY_UID + " INTEGER NOT NULL, FOREIGN KEY(" + KEY_UID
                + ") REFERENCES " + TABLE_USERS + "(" + KEY_UID + ")";
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIONS);
        // Create tables again
        onCreate(db);
    }

    void 
}
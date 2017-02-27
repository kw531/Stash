package com.example.kati.stash;

/**
 * Created by Kati on 2/17/2017.
 * +--------------------+-----------+------+
 * |   Field            |  Type     |  Key |
 * +--------------------+-----------+------+
 * |   id               |  INT      |  PRI |
 * +--------------------+-----------+------+
 * |   brandName        |  TEXT     |      |
 * +--------------------+-----------+------+
 * |   yarnName         |  TEXT     |      |
 * +--------------------+-----------+------+
 * |   color            |  TEXT     |      |
 * +--------------------+-----------+------+
 * |   fiber            |  TEXT     |      |
 * +--------------------+-----------+------+
 * |   ballsAvailable   |  DOUBLE   |      |
 * +--------------------+-----------+------+
 * |   yardage          |  DOUBLE   |      |
 * +--------------------+-----------+------+
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.y;

/** import static android.R.attr.id;
 import static android.R.attr.name;
 import static android.os.FileObserver.CREATE;
 import static android.provider.Contacts.SettingsColumns.KEY; */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "yarnInfo.db";

    // Contacts table name
    private static final String TABLE_NAME = "yarns";
    // Shops Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_BRAND_NAME = "brand_name";
    private static final String KEY_YARN_NAME = "yarn_name";
    private static final String KEY_COLOR = "color";
    private static final String KEY_FIBER = "fiber";
    private static final String KEY_BALLS_AVAILABLE = "balls_available";
    private static final String KEY_YARDAGE = "yardage";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_BRAND_NAME + " TEXT, "
                + KEY_YARN_NAME + " TEXT, "
                + KEY_COLOR + " TEXT, "
                + KEY_FIBER + " TEXT, "
                + KEY_BALLS_AVAILABLE + " DOUBLE, "
                + KEY_YARDAGE + " DOUBLE" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
// Creating tables again
        onCreate(db);
    }

    // Adding new yarn
    public void addYarn(Yarn yarn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,yarn.getID());
        values.put(KEY_BRAND_NAME, yarn.getBrandName()); // Yarn Brand Name
        values.put(KEY_YARN_NAME, yarn.getYarnName()); // Yarn Name
        values.put(KEY_COLOR, yarn.getColor()); // Yarn Color
        values.put(KEY_FIBER, yarn.getFiber()); // Yarn Fiber
        values.put(KEY_BALLS_AVAILABLE, yarn.getBallsAvailable()); // Balls Available
        values.put(KEY_YARDAGE, yarn.getYardage()); // Yarn yardage
        db.insert(TABLE_NAME, null, values);
    }

    // Getting one yarn
    public Yarn getYarn(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_BRAND_NAME, KEY_YARN_NAME, KEY_COLOR,
                        KEY_FIBER, KEY_BALLS_AVAILABLE, KEY_YARDAGE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Yarn yarn = new Yarn(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                Double.parseDouble(cursor.getString(5)),
                Double.parseDouble(cursor.getString(6)));
        // return yarn
        return yarn;
    }

    // Getting All Yarns
    public List<Yarn> getAllYarns() {
        List<Yarn> yarnList = new ArrayList<Yarn>();
        // Select All Query

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Yarn yarn = new Yarn();
                yarn.setId(Integer.parseInt(cursor.getString(0)));
                yarn.setBrandName(cursor.getString(1));
                yarn.setYarnName(cursor.getString(2));
                yarn.setColor(cursor.getString(3));
                yarn.setFiber(cursor.getString(4));
                yarn.setBallsAvailable(Double.parseDouble(cursor.getString(5)));
                yarn.setYardage(Double.parseDouble(cursor.getString(6)));
                // Adding yarn to list
                yarnList.add(yarn);
            } while (cursor.moveToNext());
        }
        // return yarn list
        return yarnList;
    }

    // Getting yarn Count
    public Cursor getCursor() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor;
    }

    // Updating a yarn
    public int updateYarn(Yarn yarn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BRAND_NAME, yarn.getBrandName());
        values.put(KEY_YARN_NAME, yarn.getYarnName());
        values.put(KEY_COLOR, yarn.getColor());
        values.put(KEY_FIBER, yarn.getFiber());
        values.put(KEY_BALLS_AVAILABLE, yarn.getBallsAvailable());
        values.put(KEY_YARDAGE, yarn.getYardage());
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(yarn.getID())});
    }


    // Deleting a yarn
    public void deleteYarn(Yarn yarn) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(yarn.getID())});
        db.close();
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void deleteAllYarn(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    public int findLastID() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();
    }

}

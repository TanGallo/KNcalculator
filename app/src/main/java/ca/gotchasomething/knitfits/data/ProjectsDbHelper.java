package ca.gotchasomething.knitfits.data;
//DatabaseHelper

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProjectsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "knitfits.db";
    private static final int DATABASE_VERSION = 1;

    public static final String ID = "_id";

    public static final String TABLE_NAME = "projects";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String UNIT = "unit";
    public static final String PWS = "pws";
    public static final String PWI = "pwi";
    public static final String PLR = "plr";
    public static final String PLI = "pli";
    public static final String GWI = "gwi";
    public static final String GLI = "gli";

    public static ProjectsDbHelper instance = null; //goes with singleton pattern constructor

    //singleton pattern constructor to prevent multiple instances of openHelper
    public static ProjectsDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ProjectsDbHelper(context);
        }
        return instance; //end of singleton pattern constructor
    }

    public ProjectsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String createQuery = "CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name TEXT NOT NULL DEFAULT 'Create new project'," +
            " image BLOB," +
            " unit TEXT NOT NULL," +
            " pws TEXT NOT NULL DEFAULT '0'," +
            " pwi TEXT NOT NULL DEFAULT '0'," +
            " plr TEXT NOT NULL DEFAULT '0'," +
            " pli TEXT NOT NULL DEFAULT '0'," +
            " gwi TEXT NOT NULL DEFAULT '0'," +
            " gli TEXT NOT NULL DEFAULT '0')";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}

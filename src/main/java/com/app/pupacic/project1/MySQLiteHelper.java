package com.app.pupacic.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	1;

    //	Database	creation	sql	statement
    private	static final String	DATABASE_CREATE	= "create table	notes"
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "	title TEXT NOT NULL,"
            + "	content TEXT NOT NULL,"
            + "	link TEXT,"
            + " image BLOB);";

    public MySQLiteHelper(Context context) {
        super(context, "notesdatabase.db",	null, DATABASE_VERSION);
    }

    @Override
    public void	onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void	onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database	from version " + oldVersion + "	to	"
                        + newVersion + ",	which	will	destroy	all	old	data");

        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

}

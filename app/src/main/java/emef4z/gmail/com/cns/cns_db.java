package emef4z.gmail.com.cns;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import static android.R.attr.name;
import static android.R.attr.version;


/**
 * Created by USER on 10-Nov-16.
 */

    //Creating Internal Database


public class cns_db extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "cns_db";
    public static final int VERSION = 1;

    public static final String CREATE_TABLE ="CREATE TABLE news_table(_id INT PRIMARY KEY,title TEXT,date TEXT,details TEXT);";


    public cns_db(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override //Creating Internal DB table
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

        //Insertion method for inserting data into the internal teable of internal DB


    public String insertInfo(String title,String date,String details)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        try
        {
            String INSERT_DATA ="INSERT INTO news_table(title,date,details)"+"VALUES('"+title+"','"+date+"','"+details+"')";
            db.execSQL(INSERT_DATA);

            return "Data Inserted Successfully!";
        }
        catch (Exception ex)
        {
            return "Error!! "+ ex.getMessage();
        }

    }


    public Cursor getInfo()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_SQL = "SELECT * FROM news_table;";
        return  db.rawQuery(SELECT_SQL,null);
    }

    }


package com.example.test.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.test.Model.Folder;

public class ImageDBHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;



    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(ImageDB.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ImageDB.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public ImageDBHelper(Context context){
        this.mCtx = context;
    }

    public ImageDBHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long insertColumn(Folder folder, String uri){
        ContentValues values = new ContentValues();
        values.put(ImageDB.CreateDB.FOLDERNAME, folder.getName());
        values.put(ImageDB.CreateDB.IMAGEURI, uri);
        return mDB.insert(ImageDB.CreateDB._TABLENAME0, null, values);
    }
    public int updateRecord(Folder folder, String name)
    {
        ContentValues values = new ContentValues();
        values.put(ImageDB.CreateDB.FOLDERNAME, folder.getName());
        return mDB.update(ImageDB.CreateDB._TABLENAME0, values, "folder_name=?", new String[] {name});
    }
    public Cursor findByFolder(Folder folder) {
        String sql="";
        sql = "select * from " + ImageDB.CreateDB._TABLENAME0
                + " where " + ImageDB.CreateDB.FOLDERNAME + "='" + folder.getName()+"'";
        Cursor cursor = mDB.rawQuery(sql,null);

        return cursor;

    }
    public String findRepImage(Folder folder) {
        String image=null;
        String sql="";
        sql = "select * from " + ImageDB.CreateDB._TABLENAME0
                + " where " + ImageDB.CreateDB.FOLDERNAME + "='" + folder.getName()+"' LIMIT 1";
        Cursor cursor = mDB.rawQuery(sql,null);
        while(cursor.moveToNext()){
            image=cursor.getString(cursor.getColumnIndex(ImageDB.CreateDB.IMAGEURI));
        }
        return image;
    }
}

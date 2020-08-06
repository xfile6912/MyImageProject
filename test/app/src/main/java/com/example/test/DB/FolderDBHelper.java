package com.example.test.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import androidx.annotation.RequiresApi;

import com.example.test.Model.Folder;

import java.time.format.DateTimeFormatter;

public class FolderDBHelper {
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
            db.execSQL(FolderDB.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+FolderDB.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public FolderDBHelper(Context context){
        this.mCtx = context;
    }

    public FolderDBHelper open() throws SQLException {
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
    public long insertColumn(Folder folder){
        ContentValues values = new ContentValues();
        values.put(FolderDB.CreateDB.NAME, folder.getName());
        values.put(FolderDB.CreateDB.PLACE, folder.getPlace());
        values.put(FolderDB.CreateDB.STARTDATE, folder.getStartDate().format(DateTimeFormatter.ISO_DATE));
        values.put(FolderDB.CreateDB.ENDDATE, folder.getEndDate().format(DateTimeFormatter.ISO_DATE));
        values.put(FolderDB.CreateDB.WITHDESCRIPTION, folder.getWithDescription());
        return mDB.insert(FolderDB.CreateDB._TABLENAME0, null, values);
    }
    public Cursor findByFolder(Folder folder) {
        String sql="";
    if(folder.getStartDate()!=null && folder.getEndDate()!=null) {
        sql = "select * from " + FolderDB.CreateDB._TABLENAME0
                + " where " + FolderDB.CreateDB.NAME + " like '%" + folder.getName() + "%' and "
                + FolderDB.CreateDB.PLACE + " like '%" + folder.getPlace() + "%' and "
                + FolderDB.CreateDB.WITHDESCRIPTION + " like '%" + folder.getWithDescription() + "%' and "
                + FolderDB.CreateDB.STARTDATE + ">='" + folder.getStartDate() + "' and "
                + FolderDB.CreateDB.STARTDATE + "<='" + folder.getEndDate() + "'"+" union "
                +"select * from "+FolderDB.CreateDB._TABLENAME0
                +" where "+FolderDB.CreateDB.NAME +" like '%"+folder.getName()+"%' and "
                +FolderDB.CreateDB.PLACE +" like '%"+folder.getPlace()+"%' and "
                +FolderDB.CreateDB.WITHDESCRIPTION +" like '%"+folder.getWithDescription()+"%' and "
                +FolderDB.CreateDB.ENDDATE+">='"+folder.getStartDate()+"' and "
                +FolderDB.CreateDB.ENDDATE+"<='"+folder.getEndDate()+"' union "
                +"select * from "+FolderDB.CreateDB._TABLENAME0
                +" where "+FolderDB.CreateDB.NAME +" like '%"+folder.getName()+"%' and "
                +FolderDB.CreateDB.PLACE +" like '%"+folder.getPlace()+"%' and "
                +FolderDB.CreateDB.WITHDESCRIPTION +" like '%"+folder.getWithDescription()+"%' and "
                +FolderDB.CreateDB.STARTDATE+"<='"+folder.getStartDate()+"' and "
                +FolderDB.CreateDB.ENDDATE+">='"+folder.getEndDate()+"'";
        //and usage_info.start_date>='$startDate' and usage_info.start_date<='$endDate'
        //usage_info.end_date>='$startDate' and usage_info.end_date<='$endDate'
        //and usage_info.start_date<='$startDate' and usage_info.end_date>='$endDate'";
        }
    if(folder.getStartDate()==null && folder.getEndDate()==null)
        sql = "select * from " + FolderDB.CreateDB._TABLENAME0
                + " where " + FolderDB.CreateDB.NAME + " like '%" + folder.getName() + "%' and "
                + FolderDB.CreateDB.PLACE + " like '%" + folder.getPlace() + "%' and "
                + FolderDB.CreateDB.WITHDESCRIPTION + " like '%" + folder.getWithDescription() + "%'";
        Cursor cursor = mDB.rawQuery(sql,null);

        return cursor;



    }
    public Cursor findAll() {

        String sql="select * from "+FolderDB.CreateDB._TABLENAME0;
        Cursor cursor = mDB.rawQuery(sql,null);

        return cursor;



    }
}

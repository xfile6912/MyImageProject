package com.example.test.DB;

import android.provider.BaseColumns;

public final class ImageDB {
    public static final class CreateDB implements BaseColumns{
        public static final String FOLDERNAME="folder_name";
        public static final String IMAGEURI="image_uri";
        public static final String _TABLENAME0= "image";//TODO:DB에 NOT NULL인거 DIALOG에서 확인누를때 빈칸일 수 없게 체크하기.
        public static final String _CREATE0="create table if not exists "+_TABLENAME0+"("
                +FOLDERNAME+" text, "
                +IMAGEURI+" text, foreign key("
                +FOLDERNAME+") references "+FolderDB.CreateDB._TABLENAME0+"("+FolderDB.CreateDB.NAME+")"
                +"on delete cascade, primary key("+ FOLDERNAME+", "+ IMAGEURI+") );";
    }
}

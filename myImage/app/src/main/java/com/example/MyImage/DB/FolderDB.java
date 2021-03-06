package com.example.MyImage.DB;

import android.provider.BaseColumns;

public final class FolderDB {
    public static final class CreateDB implements BaseColumns{
        public static final String NAME="name";
        public static final String PLACE="place";
        public static final String STARTDATE="start_date";
        public static final String ENDDATE="end_date";
        public static final String WITHDESCRIPTION="with_description";
        public static final String REPIMAGE="rep_image";
        public static final String _TABLENAME0= "folder";//TODO:DB에 NOT NULL인거 DIALOG에서 확인누를때 빈칸일 수 없게 체크하기.
        public static final String _CREATE0="create table if not exists "+_TABLENAME0+"("
                +NAME+" text primary key, "
                +PLACE+" text not null, "
                +STARTDATE+" DATE not null , "
                +ENDDATE+" DATE not null , "
                +REPIMAGE+" text null, "
                +WITHDESCRIPTION+" text );";
    }
}

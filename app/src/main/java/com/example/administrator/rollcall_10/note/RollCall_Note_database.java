package com.example.administrator.rollcall_10.note;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/5/17.
 */
public class RollCall_Note_database extends SQLiteOpenHelper {
    public static String Note_Database_Name="Note_Database";

    public static String _id = "_id";
    public static String Note_Title = "Note_Tilte";
    public static String Note_Content = "Note_Content";
    public static String Note_Create_Note_Time = "Note_Create_Note_Time";
    public static String Note_IsFinsh = "Note_IsFinsh";
    public static String Note_FinshOrNotIcon = "Note_FinshOrNotIcon";
    public static String Note_Cancel_Category = "Note_Cancel_Category";

    //鬧鐘提醒從放的list
//    public static String Note_AlarmRemindList = "Note_AlarmRemindList";


    private static RollCall_Note_database instance;



    public static RollCall_Note_database getInstance(Context ctx){
        if (instance == null)
        {
            instance = new RollCall_Note_database(ctx,RollCall_Note_database.Note_Database_Name,null,1);
        }

        return instance;
    }


    private RollCall_Note_database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+ Note_Database_Name +"("+
                _id + " INTEGER PRIMARY KEY NOT NULL ,"+
                Note_Title+" nvarchar nchar,"+
                Note_Content + " nvarchar ,"+
                Note_Create_Note_Time + " nvarchar ,"+
                Note_IsFinsh + " INTEGER ," +
                Note_FinshOrNotIcon + " INTEGER)"


        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}


package com.shikhar.weddingappsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by utkarshnath on 01/10/15.
 */
public class myHelper extends SQLiteOpenHelper {
    public static final String DataBase_Name = "mydatabase";
    public static final String Table_Name = "MYTABLE";
    public static final String Reminder_Table = "ReminderTable";
    public static final String Reminder_date = "ReminderDate";
    public static final String Reminder_month = "ReminderMonth";
    public static final String Reminder_year = "ReminderYear";
    public static final String Reminder_hour = "ReminderHour";
    public static final String Reminder_minute = "Reminderminute";
    public static final String Reminder = "Reminder";
    public static final String UID = "_id";
    public static final String InviteeName = "InviteeName";
    public static final String InviteeId = "InviteeId";
    public static final int Version = 1;
    public myHelper(Context context) {
        super(context,DataBase_Name,null,Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "  + Table_Name +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InviteeName + " VARCHAR(255)," +
                InviteeId + " VARCHAR(255)," + " UNIQUE ("+InviteeId+") ON CONFLICT REPLACE);");


        db.execSQL("CREATE TABLE " + Reminder_Table +
                "(" +UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Reminder + " VARCHAR(255)," +
                Reminder_date + " VARCHAR(255)," +
                Reminder_month + " VARCHAR(255)," +
                Reminder_hour + " VARCHAR(255)," +
                Reminder_minute+ " VARCHAR(255)," +
                Reminder_year + " VARCHAR(255)," + " UNIQUE ("+Reminder+") ON CONFLICT REPLACE);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

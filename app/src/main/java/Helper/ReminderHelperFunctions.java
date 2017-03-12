package Helper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shikhar.weddingappsample.myHelper;

import java.util.ArrayList;
import java.util.Date;

import Models.Reminder;

/**
 * Created by shikharkhetan on 1/22/16.
 */

public class ReminderHelperFunctions {

    static myHelper Helper;
    static SQLiteDatabase db;

    public ReminderHelperFunctions(Activity mActivity) {
        Helper = new myHelper(mActivity);

    }


    public void insertInDatabase(Reminder reminder){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.Reminder, reminder.title);
        contentValues.put(Helper.Reminder_year,reminder.date.getYear()+"");
        contentValues.put(Helper.Reminder_month,reminder.date.getMonth()+"");
        contentValues.put(Helper.Reminder_date,reminder.date.getDate()+"");
        contentValues.put(Helper.Reminder_hour,reminder.date.getHours()+"");
        contentValues.put(Helper.Reminder_minute,reminder.date.getMinutes()+"");
        db.insert(Helper.Reminder_Table, null, contentValues);
    }
    public void deletefromDatabase(Reminder reminder){
        db.delete(Helper.Reminder_Table, Helper.Reminder + "=?", new String[]{String.valueOf(reminder.title)});
    }
    public ArrayList<Reminder> fetchReminderFromDatabase(){
        ArrayList<Reminder>remindersList = new ArrayList<Reminder>();
        db = Helper.getWritableDatabase();
        String columns[] = {Helper.Reminder,Helper.Reminder_date,Helper.Reminder_month,Helper.Reminder_year, Helper.Reminder_hour, Helper.Reminder_minute};
        Cursor cursor = db.query(Helper.Reminder_Table,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            String Sreminder = cursor.getString(cursor.getColumnIndex(myHelper.Reminder));
            String reminderDate = cursor.getString(cursor.getColumnIndex(myHelper.Reminder_date));
            String reminderMonth = cursor.getString(cursor.getColumnIndex(myHelper.Reminder_month));
            String reminderYear = cursor.getString(cursor.getColumnIndex(myHelper.Reminder_year));
            String reminderhour = cursor.getString(cursor.getColumnIndex(myHelper.Reminder_hour));
            String remindermin = cursor.getString(cursor.getColumnIndex(myHelper.Reminder_minute));
            int rDate = Integer.valueOf(reminderDate);
            int rMonth = Integer.valueOf(reminderMonth);
            int rYear = Integer.valueOf(reminderYear);
            int rhour = Integer.valueOf(reminderhour);
            int rmin = Integer.valueOf(remindermin);
            Date date = new Date(rYear,rMonth,rDate,rhour,rmin);
            Reminder reminder = new Reminder(Sreminder,date);
            remindersList.add(reminder);
        }
        return  remindersList;
    }
}

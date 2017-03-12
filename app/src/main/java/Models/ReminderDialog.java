package Models;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.shikhar.weddingappsample.R;
import com.shikhar.weddingappsample.Receiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Helper.ReminderHelperFunctions;

/**
 * Created by shikharkhetan on 1/13/16.
 */
public class ReminderDialog {

    Activity mActivity;
    View mView;
    EditText dateText;
    Calendar myCalendar;
    EditText timeText;
    EditText title;
    Context context;
    Date date;

    public ReminderDialog(Activity mActivity, View mView, Context context) {
        this.mActivity = mActivity;
        this.mView = mView;
        this.context = context;
    }


    public void createReminderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        // Get the layout inflater
        LayoutInflater inflater = mActivity.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.reminder_layout, null);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Set Reminder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Log.d("date", myCalendar.getTime().toString());
                        Reminder reminder = new Reminder(title.getText().toString(), myCalendar.getTime());
                        ReminderHelperFunctions reminderHelperFunctions = new ReminderHelperFunctions(mActivity);
                        setAlarm(reminder);
//                        reminderHelperFunctions.insertInDatabase(reminder);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        title = (EditText) v.findViewById(R.id.title);
        dateText = (EditText) v.findViewById(R.id.date);
        timeText = (EditText) v.findViewById(R.id.time);
        myCalendar = Calendar.getInstance();
        setDatePicker();
        setTimePicker();
        builder.create().show();
    }

    void setAlarm(Reminder reminder) {
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra("Discription", reminder.title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        reminder.setPendingIntent(pendingIntent);

        Date todaysDate = (Date) Calendar.getInstance().getTime();
        Date eventDate = reminder.date;

        int days = no_of_daysLeft(eventDate,todaysDate);
        int min = no_of_min_left(eventDate, todaysDate);
        int hr = no_of_hr_left(eventDate,todaysDate);
        days = days*24*60*60;
        min = min*60;
        hr = hr*60*60;

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                (days+hr+min) * 1000, pendingIntent);
    }



    public void cancelAlarm(Reminder reminder) {
        PendingIntent pendingIntent = reminder.getPendingIntent();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }


    private void setTimePicker() {

        timeText.setOnClickListener(new View.OnClickListener() {


            final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);
                    updateTimeLabel();
                }
            };

            @Override
            public void onClick(View v) {
                if (myCalendar != null) {
                    new TimePickerDialog(mActivity, time, myCalendar
                            .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();

                }
            }
        });

    }

    private void updateTimeLabel() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        timeText.setText(sdf.format(myCalendar.getTime()));
    }

    private void setDatePicker() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(mActivity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(sdf.format(myCalendar.getTime()));
    }


    int no_of_hr_left(Date eventdate, Date todaysdate) {
        return eventdate.getHours() - todaysdate.getHours();
    }

    int no_of_min_left(Date eventdate, Date todaysdate) {
        return eventdate.getMinutes() - todaysdate.getMinutes();
    }

    int no_of_daysLeft(Date eventdate, Date todaysdate) {
        if (eventdate.getMonth() == todaysdate.getMonth()) {
            return eventdate.getDate() - todaysdate.getDate();
        }
        Date temp = todaysdate;
        int days = totaldays(temp.getMonth()) - temp.getDate();
        temp.setMonth(temp.getMonth() + 1);
        while (temp.getMonth() != eventdate.getMonth()) {
            days += totaldays(temp.getMonth());
            temp.setMonth(temp.getMonth() + 1);
        }
        days += eventdate.getDate();
        return days;
    }

    int totaldays(int month) {
        switch (month) {
            case 0:
                return 31;
            case 1:
                return 28;
            case 2:
                return 31;
            case 3:
                return 30;
            case 4:
                return 31;
            case 5:
                return 30;
            case 6:
                return 31;
            case 7:
                return 31;
            case 8:
                return 30;
            case 9:
                return 31;
            case 10:
                return 30;
            case 11:
                return 31;

            default:
                return 31;
        }
    }
}


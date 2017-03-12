package com.shikhar.weddingappsample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Helper.CircleTransform;
import Models.Reminder;

public class LoginActivity extends AppCompatActivity {

    TextView textView;
    Boolean checkLogin ;
    LoginButton loginButton;
    ImageView dp;
    Profile profile;
    myHelper Helper;
    Context context;
    SQLiteDatabase db;
    CallbackManager callbackManager;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_login);
        Helper = new myHelper(this);
        db = Helper.getWritableDatabase();
        sp = getSharedPreferences("USER", Context.MODE_PRIVATE);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends", "public_profile");
        callbackManager = CallbackManager.Factory.create();

        textView = (TextView) findViewById(R.id.login_textview);
        loginButton.registerCallback(callbackManager, mcallback);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {




            AccessToken accessToken =AccessToken.getCurrentAccessToken();
            Profile.fetchProfileForCurrentAccessToken();
            profile = Profile.getCurrentProfile();
            if(profile==null){
                textView.setText("null");
            }
            if(profile!=null){
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("UserName", profile.getFirstName());
                profile.
                editor.putString("UserFbId", profile.getId());
                if(sp.getBoolean("fileExists", false)==false){
                    File folder = new File(Environment.getExternalStorageDirectory() + "/Wedding App");
                    folder.mkdir();
                    editor.putBoolean("fileExists",true);
                }
                editor.commit();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("InviteeProfile");
                query.whereEqualTo("Id", profile.getId());
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object == null) {
                            ParseObject inviteeProfile = new ParseObject("InviteeProfile");
                            inviteeProfile.put("Name", profile.getName());
                            inviteeProfile.put("Id", profile.getId());
                            inviteeProfile.saveInBackground();


                        } else {

                        }
                    }
                });
                ImageView imageView = (ImageView) findViewById(R.id.user_dp);
                Picasso.with(getApplicationContext()).load("https://graph.facebook.com/" + profile.getId() + "/picture?type=large").transform(new CircleTransform()).into(imageView);
                fetchInviteeFromServer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 3000);

            }



//            Date sunderKanth = new Date(116,1,9,14,0);
//            Reminder reminderSunder = new Reminder("Sunder Kaand Path at 4:00 pm", sunderKanth);
//            setAlarm(reminderSunder);

            Date BiyahHaath = new Date(116,1,10,13,0);
            Reminder reminderBiyah = new Reminder("Biyah Haath at 3:00 pm", BiyahHaath);
            setAlarm(reminderBiyah);


            Date Ratjaga = new Date(116,1,10,19,30);
            Reminder reminderRatjata = new Reminder("Ratjaga at 9:30 pm", Ratjaga);
            setAlarm(reminderRatjata);


            Date BaanChadhani = new Date(116,1,11,9,0);
            Reminder reminderBaan = new Reminder("Baan Chadhani at 11:00 am", BaanChadhani);
            setAlarm(reminderBaan);


            Date RingCeremony = new Date(116,1,11,15,0);
            Reminder reminderRing = new Reminder("Ring Ceremony at 5:00 pm", RingCeremony);
            setAlarm(reminderRing);


            Date shadi = new Date(116,1,11,17,0);
            Reminder reminderShadi = new Reminder("Shaadi at 7:00 pm", shadi);
            setAlarm(reminderShadi);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {
            textView.setText("err"+e);
        }
    };


    void setAlarm(Reminder reminder) {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra("Discription", reminder.title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
    public void fetchInviteeFromServer(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InviteeProfile");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if(db!=null){
                        db.execSQL("DELETE FROM " + myHelper.Table_Name);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Helper.InviteeName,list.get(i).getString("Name"));
                        contentValues.put(Helper.InviteeId,list.get(i).getString("Id"));
                        db.insert(Helper.Table_Name, null, contentValues);
                    }
                } else {

                }
            }
        });
    }
}

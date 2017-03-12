package Models;

import android.app.PendingIntent;

import java.util.Date;

/**
 * Created by shikharkhetan on 1/13/16.
 */
public class Reminder {
    public String title;
    public Date date;
    public PendingIntent pendingIntent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPendingIntent(PendingIntent pendingIntent){
        this.pendingIntent = pendingIntent;
    }

    public PendingIntent getPendingIntent(){
        return pendingIntent;
    }
    public Reminder(String title, Date date) {
        this.title = title;
        this.date = date;
    }
}

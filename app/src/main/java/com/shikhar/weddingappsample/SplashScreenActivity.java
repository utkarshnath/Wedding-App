package com.shikhar.weddingappsample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;

import Helper.CircleTransform;

public class SplashScreenActivity extends AppCompatActivity {


    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_splash_screen);
       // Parse.initialize(this, "OCvhok5Gh0FhJhfAA6cKWysZiL53xehGC9zM3OO1", "EmROHXSF6RclFFvsZXu30qv9ZMPbWBI2a25LyCYF");
        sp = getSharedPreferences("USER", Context.MODE_PRIVATE);
        ImageView weddingCouple = (ImageView) findViewById(R.id.weddingCouple);
        Picasso.with(this).load(R.drawable.os8).centerCrop().fit().transform(new CircleTransform()).into(weddingCouple);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent;
                if(sp.getString("UserName",null)==null){
                    mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }else {
                    mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, 3000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}

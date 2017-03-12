package com.shikhar.weddingappsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class EventSpecificFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_feed);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type",0);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ArrayList<String> list = new ArrayList<>();
        list.add("Sundar Kaand Path");
        list.add("Biyah Haath");
        list.add("Ratjaga");
        list.add("Baan Chadhani");
        list.add("Ring Ceremony");
        list.add("Shaadi");
        getSupportActionBar().setTitle(list.get(type));
        EventSpecificFeedFragment fragment = new EventSpecificFeedFragment();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_type_feed_, menu);
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

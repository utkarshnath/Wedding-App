package com.shikhar.weddingappsample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import Helper.InviteeAdapter;
import Models.Invitee;


public class InviteeList extends ActionBarActivity {

    ArrayList<Invitee>inviteeList;
    RecyclerView inviteeRecycler;
    myHelper Helper;
    SQLiteDatabase db;
    SwipeRefreshLayout InviteeListSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitee_list);
        inviteeList = new ArrayList<Invitee>();
        inviteeRecycler = (RecyclerView) findViewById(R.id.invitee_recycler_view);
        InviteeListSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.InviteeList_swipe_refresh_layout);
        fetchInviteeFromDatabase();
        final InviteeAdapter inviteeAdapter = new InviteeAdapter(getApplicationContext(), inviteeList);
        inviteeAdapter.notifyDataSetChanged();
        inviteeRecycler.setAdapter(inviteeAdapter);
        inviteeRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        InviteeListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inviteeList.clear();
                fetchInviteeFromServer();
                //check here that fetching from server is happening before fetching from database
                fetchInviteeFromDatabase();
                InviteeListSwipeRefreshLayout.setRefreshing(false);
                inviteeAdapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invitee_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id==R.id.action_send_invitation){
            Uri uri = Uri.parse("android.resource://com.shikhar.weddingappsample/drawable/ic_action");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Some message");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchInviteeFromDatabase(){
            Helper=new myHelper(this);
            db = Helper.getWritableDatabase();
            String [] Column = {Helper.InviteeName,Helper.InviteeId};
            Cursor cursor = db.query(Helper.Table_Name,Column,null,null,null,null,null,null);
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(myHelper.InviteeName));
                String id = cursor.getString(cursor.getColumnIndex(myHelper.InviteeId));
                Invitee invitee  = new Invitee(name,id);
                inviteeList.add(invitee);
            }
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

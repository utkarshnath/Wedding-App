package com.shikhar.weddingappsample;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;
import Helper.InviteeAdapter;
import Models.Invitee;
/**
 * A simple {@link Fragment} subclass.
 */
public class InviteeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    ArrayList<Invitee> inviteeList;
    RecyclerView inviteeRecycler;
    myHelper Helper;
    SQLiteDatabase db;
    SwipeRefreshLayout InviteeListSwipeRefreshLayout;
    Button sendInvitation;
    public InviteeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invitee_list,null,false);
        inviteeList = new ArrayList<Invitee>();
        inviteeRecycler = (RecyclerView) view.findViewById(R.id.invitee_recycler_view);
        sendInvitation = (Button) view.findViewById(R.id.invitee_send_button);
        sendInvitation.setOnClickListener(this);
        InviteeListSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.InviteeList_swipe_refresh_layout);
        fetchInviteeFromDatabase();
        final InviteeAdapter inviteeAdapter = new InviteeAdapter(getActivity(), inviteeList);
        inviteeAdapter.notifyDataSetChanged();
        inviteeRecycler.setAdapter(inviteeAdapter);
        inviteeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        return view;
    }
    public void fetchInviteeFromDatabase(){
        Helper=new myHelper(getActivity());
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
                    for (int i = 0; i < list.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Helper.InviteeName, list.get(i).getString("Name"));
                        contentValues.put(Helper.InviteeId, list.get(i).getString("Id"));
                        db.insert(Helper.Table_Name, null, contentValues);
                    }
                } else {
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==sendInvitation.getId()){
            Uri uri = Uri.parse("android.resource://com.shikhar.weddingappsample/drawable/invitation");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
        }
    }
}
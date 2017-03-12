package com.shikhar.weddingappsample;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class OurWeddingFragment1 extends android.support.v4.app.Fragment implements View.OnClickListener {


    OurweddingTemplate template;
    OurWeddingadapter adapter;
    RecyclerView ourWeddingRecycler;
    int count;
    Button viewInvitaion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_our_wedding, container, false);
        viewInvitaion = (Button) v.findViewById(R.id.invitation_view_button);
        viewInvitaion.setOnClickListener(this);
        template = new OurweddingTemplate();
        ArrayList<String> preWedding = new ArrayList<String>();
        ArrayList<String> engagement = new ArrayList<String>();
        ArrayList<String> wedding = new ArrayList<String>();
        ArrayList<String> postWedding = new ArrayList<String>();
        ArrayList<String> sangeet = new ArrayList<String>();
        template.preWedding = preWedding;
        template.engagement = engagement;
        template.wedding = wedding;
        template.postWedding = postWedding;
        template.sangeet = sangeet;

        count = 0;
        ourWeddingRecycler = (RecyclerView) v.findViewById(R.id.ourwedding_recycler_view);
        adapter = new OurWeddingadapter(getActivity(),template);
        ourWeddingRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        ourWeddingRecycler.setAdapter(adapter);
        fetchDataFromServer();


        return v;

    }

    void putImageInType(int type,String imageUrl,int i,int size){
        switch (type){
            case 0:
                template.preWedding.add(imageUrl);
                break;
            case 1:
                template.engagement.add(imageUrl);
                break;
            case 2:
                template.wedding.add(imageUrl);
                break;
            case 3:
                template.postWedding.add(imageUrl);
                break;
            case 4:
                template.sangeet.add(imageUrl);
                break;
            default:template.wedding.add(imageUrl);
                break;
        }
        if(i==size-1){
            adapter.notifyDataSetChanged();
        }
    }


    private void fetchDataFromServer() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("OurWedding");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        ParseFile fileObject = (ParseFile) list.get(i).get("Image");

                        final String imageUrl = fileObject.getUrl();
                        final int finalI = i;
                        int type = list.get(i).getInt("Type");
                        putImageInType(type,imageUrl,i,list.size());
                        if(finalI == list.size()-1){
                            Log.d("value of lasti", finalI+" ");
                        }
                    }
                }
            }
        });

        return ;
    }

    @Override
    public void onClick(View v) {
        DialogPlus dialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(R.layout.invitation))
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setExpanded(true)
                .create();

        dialog.show();
    }
}
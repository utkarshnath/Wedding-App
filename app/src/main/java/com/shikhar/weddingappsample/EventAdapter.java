package com.shikhar.weddingappsample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by utkarshnath on 27/08/15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.myViewHolder>  {

    private ArrayList<Event> EventList;
    private LayoutInflater inflater;
    private Context context;

    public EventAdapter(Context context,ArrayList<Event> EventList){
        this.EventList = EventList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.eventitem1,viewGroup,false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventAdapter.myViewHolder myViewHolder, final int i) {
        myViewHolder.EventName.setText(EventList.get(i).EventName);
        myViewHolder.eventRelativeLayout.setBackground(EventList.get(i).EventPhoto);
        myViewHolder.eventRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,EventSpecificFeedActivity.class);
                intent.putExtra("type",i);
                context.startActivity(intent);
            }
        });

        myViewHolder.EventDate.setText(EventList.get(i).EventDate);
        myViewHolder.EventTime.setText(EventList.get(i).EventTime);
        myViewHolder.EventVenue.setText(EventList.get(i).EventVenue);
        myViewHolder.UberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PackageManager pm = context.getPackageManager();
                    pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                    String uri = new String();
                    if(i==0){
                         uri =
                                "uber://?action=setPickup&pickup=my_location&client_id=YOUR_CLIENT_ID" +
                                        "&dropoff[latitude]=28.733217&dropoff[longitude]=77.115807&" +
                                        "dropoff[nickname]=Sector-16,%20Rohini" +
                                        "&dropoff[formatted_address]=Sector-16,%20Rohini,%20New%20Delhi";
                    }
                    if(i==1 || i==2 || i==3){
                        uri =
                                "uber://?action=setPickup&pickup=my_location&client_id=YOUR_CLIENT_ID" +
                                        "&dropoff[latitude]=28.733585&dropoff[longitude]=77.116849&" +
                                        "dropoff[nickname]=SRockfield%20Public%20School" +
                                        "&dropoff[formatted_address]=Bansal%20Bhawan,%20Community%20Hall%20A-7,%20Sector%2016,%20 Rohini,%20New Delhi";
                    }
                    if(i==4){
                        uri =
                                "uber://?action=setPickup&pickup=my_location&client_id=YOUR_CLIENT_ID" +
                                        "&dropoff[latitude]=28.679991&dropoff[longitude]=77.091218&" +
                                        "dropoff[nickname]=Palace%20Of%20Dreams" +
                                        "&dropoff[formatted_address]=Palace%20Of%20Dreams,Peeragarhi,New%20Delhi";
                    }
                    if(i==5){
                        uri =
                                "uber://?action=setPickup&pickup=my_location&client_id=YOUR_CLIENT_ID" +
                                        "&dropoff[latitude]=28.804551&dropoff[longitude]=77.140720&" +
                                        "dropoff[nickname]=Blue%20Sapphire%20Motel%20&%20Resort" +
                                        "&dropoff[formatted_address]=Blue%20Sapphire%20Motel%20&%20Resort,G.T%20Karnal%20Road,New%20Delhi";
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    context.startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    // No Uber app! Open mobile website.
                    String url = "https://m.uber.com/sign-up?client_id=YOUR_CLIENT_ID";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }

            }
        });

    }



    @Override
    public int getItemCount()
    {
        return EventList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView EventName;
        TextView EventDate;
        TextView EventMonth;
        TextView EventTime;
        TextView EventVenue;
        TextView EventDiscription;
        ImageView EventPhoto;
        ImageView UberButton;
        RelativeLayout eventRelativeLayout;

        public myViewHolder(View itemView) {
            super(itemView);
            eventRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.event_relativeView);
            EventName = (TextView) itemView.findViewById(R.id.event_name);
            EventDate = (TextView) itemView.findViewById(R.id.event_date);
            EventPhoto = (ImageView) itemView.findViewById(R.id.event_Photo);
            //EventMonth = (TextView) itemView.findViewById(R.id.event_month);
            EventTime = (TextView) itemView.findViewById(R.id.event_Time);
            EventVenue = (TextView) itemView.findViewById(R.id.event_venue);
            UberButton = (ImageView) itemView.findViewById(R.id.uber_button);
        }


    }
}

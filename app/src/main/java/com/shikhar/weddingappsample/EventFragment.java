package com.shikhar.weddingappsample;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends android.support.v4.app.Fragment {

    RecyclerView eventRecycler;
    ArrayList<Event>eventList;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventList = new ArrayList<Event>();
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        AddEvents();

        eventRecycler = (RecyclerView) view.findViewById(R.id.event_recyclerview);
        EventAdapter myAdapter = new EventAdapter(getActivity(),eventList);
        eventRecycler.setAdapter(myAdapter);
        eventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return  view;
    }

    private void AddEvents() {
        String EventName = getString(R.string.sundarkaand);
        Drawable EventPhoto = getResources().getDrawable(R.drawable.diyathali);
        String EventDate = "9th February";
        String EventMonth = "February";
        String EventTime = "4:00PM";
        String EventVenue = "Home: D-1, 45-46(GF), Sector-16, Rohini, New Delhi(Near District Park)";
        Event E = new Event();
        E.EventName = EventName;
        E.EventDate = EventDate;
        E.EventMonth = EventMonth;
        E.EventPhoto = EventPhoto;
        E.EventVenue = EventVenue;
        E.EventTime = EventTime;
        E.Latitude = "28.733217";
        E.Longitude = "77.115807";
        E.LocationNickname = "Sector-16,%20Rohini";
        E.CompleteLocation = "Sector-16,%20Rohini,%20New%20Delhi";
        eventList.add(E);

        Event E1 = new Event();
        EventName = "Biyah Haath";
        EventPhoto = getResources().getDrawable(R.drawable.haldi);
        EventDate = "10th February";
        EventTime = "3:00PM";
        EventVenue = "Bansal Bhawan, Community Hall A-7, Sector 16, Rohini, New Delhi(Near Rockfield Public School)";
        E1.EventName = EventName;
        E1.EventDate = EventDate;
        E1.EventMonth = EventMonth;
        E1.EventPhoto = EventPhoto;
        E1.EventVenue = EventVenue;
        E1.EventTime = EventTime;
        E1.Latitude = "28.733585";
        E1.Longitude = "77.116849";
        E1.LocationNickname = "Rockfield%20Public%20School";
        E1.CompleteLocation = "Bansal%20Bhawan,%20Community%20Hall%20A-7,%20Sector%2016,%20 Rohini,%20New Delhi";
        eventList.add(E1);

          E1 = new Event();
        EventName = "Ratjaga";
        EventPhoto = getResources().getDrawable(R.drawable.ratjaga);
        EventDate = "10th February";
        EventTime = "9:30PM";
        EventVenue = "Bansal Bhawan, Community Hall A-7, Sector 16, Rohini, New Delhi(Near Rockfield Public School)";
        E1.EventName = EventName;
        E1.EventDate = EventDate;
        E1.EventMonth = EventMonth;
        E1.EventPhoto = EventPhoto;
        E1.EventVenue = EventVenue;
        E1.EventTime = EventTime;
        E1.Latitude = "28.733585";
        E1.Longitude = "77.116849";
        E1.LocationNickname = "Rockfield%20Public%20School";
        E1.CompleteLocation = "Bansal%20Bhawan,%20Community%20Hall%20A-7,%20Sector%2016,%20 Rohini,%20New Delhi";
        eventList.add(E1);

        E1 = new Event();
        EventName = "Baan Chadhani";
        EventPhoto = getResources().getDrawable(R.drawable.mehndi);
        EventDate = "11th February";
        EventTime = "11:00AM";
        EventVenue = "Bansal Bhawan, Community Hall A-7, Sector 16, Rohini, New Delhi(Near Rockfield Public School)";
        E1.EventName = EventName;
        E1.EventDate = EventDate;
        E1.EventMonth = EventMonth;
        E1.EventPhoto = EventPhoto;
        E1.EventVenue = EventVenue;
        E1.EventTime = EventTime;
        E1.Latitude = "28.733585";
        E1.Longitude = "77.116849";
        E1.LocationNickname = "Rockfield%20Public%20School";
        E1.CompleteLocation = "Bansal%20Bhawan,%20Community%20Hall%20A-7,%20Sector%2016,%20 Rohini,%20New Delhi";
        eventList.add(E1);

        E1 = new Event();
        EventName = "Ring Ceremony";
        EventPhoto = getResources().getDrawable(R.drawable.eg);
        EventDate = "11th February";
        EventTime = "5:00PM";
        EventVenue = "Palace Of Dreams,Peeragarhi,New Delhi";
        E1.EventName = EventName;
        E1.EventDate = EventDate;
        E1.EventMonth = EventMonth;
        E1.EventPhoto = EventPhoto;
        E1.EventVenue = EventVenue;
        E1.EventTime = EventTime;
        E1.Latitude = "28.679991";
        E1.Longitude = "77.091218";
        E1.LocationNickname = "Palace%20Of%20Dreams";
        E1.CompleteLocation = "Palace%20Of%20Dreams,Peeragarhi,New%20Delhi";
        eventList.add(E1);

        Event E2 = new Event();
        EventName = "Shaadi";
        EventPhoto = getResources().getDrawable(R.drawable.exchangingvarmalas);
        EventDate = "12th February ";
        EventTime = "7:00PM";
        EventVenue = "Blue Sapphire Motel & Resort,G.T Karnal Road,New Delhi";
        E2.EventName = EventName;
        E2.EventDate = EventDate;
        E2.EventMonth = EventMonth;
        E2.EventPhoto = EventPhoto;
        E2.EventVenue = EventVenue;
        E2.EventTime = EventTime;
        E2.Latitude = "28.804551";
        E2.Longitude = "77.140720";
        E2.LocationNickname = "Blue%20Sapphire%20Motel%20&%20Resort";
        E2.CompleteLocation = "Blue%20Sapphire%20Motel%20&%20Resort,G.T%20Karnal%20Road,New%20Delhi";
        eventList.add(E2);

    }


}

package com.shikhar.weddingappsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import Helper.EventSpecificAdapter;
import Models.Post;


/**
 * A placeholder fragment containing a simple view.
 */
public class EventSpecificFeedFragment extends Fragment {

    public EventSpecificFeedFragment() {
    }


    int type;
    EventSpecificAdapter adapter;
    RecyclerView rv;
    ArrayList<Post> mPosts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        View view = inflater.inflate(R.layout.fragment_event_type_feed, container, false);
        rv = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        mPosts = new ArrayList<Post>();
        adapter = new EventSpecificAdapter( mPosts, getActivity());
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        fetchDataFromServer();
        return view;
    }


    private void fetchDataFromServer() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FeedTemplate");
        Log.d("!@type", type+"");
        query.whereEqualTo("EventType", type);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {
                if (e == null) {
                    mPosts.clear();
                    for (int i = 0; i < list.size(); i++) {
                        ParseFile fileObject = (ParseFile) list.get(i).get("Image");
                        Log.d("fileUrl", fileObject.getUrl());
                        final String imageUrl = fileObject.getUrl();
                        final int finalI = i;

                        adapter.notifyDataSetChanged();
                        fileObject.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    Log.d("test", "We've got data in data.");

                                    Post post = new Post(list.get(finalI).getString("UploaderName"),
                                            list.get(finalI).getString("UploaderProfile"), list.get(finalI).getString("Discription"),
                                            list.get(finalI).getObjectId());
                                    post.setImageUrl(imageUrl);

                                    mPosts.add(post);


                                } else {
                                    Log.d("test",
                                            "There was a problem downloading the data.");
                                }
                                //progressDialog.dismiss();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }


                } else {

                }
            }
        });

        return ;
    }
}

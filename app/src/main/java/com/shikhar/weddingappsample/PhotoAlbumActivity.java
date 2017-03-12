package com.shikhar.weddingappsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumActivity extends AppCompatActivity {


    RecyclerView imageRecycler;
    ArrayList<String> imageUrlList;
    ImageAdapter myAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);
        imageUrlList = new ArrayList<>();
        addUrls();
        imageRecycler = (RecyclerView)findViewById(R.id.cardList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        imageRecycler.setLayoutManager(staggeredGridLayoutManager);
        myAdapter = new ImageAdapter(this, imageUrlList);
        imageRecycler.setAdapter(myAdapter);
    }

    private void addUrls() {

        Intent i = getIntent();
        Bundle b = i.getExtras();
        ArrayList<String> imageUrls = (ArrayList<String>) b.get("imageUrls");
        imageUrlList.addAll(imageUrls);
    }
}

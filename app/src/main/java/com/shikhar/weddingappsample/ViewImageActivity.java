package com.shikhar.weddingappsample;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ImageView viewImage = (ImageView) findViewById(R.id.viewImage);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String url = b.getString("imageUrl");
        Picasso.with(this).load(url).fit().centerInside().into(viewImage);
    }
}

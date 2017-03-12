package com.shikhar.weddingappsample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utkarshnath on 02/02/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {



    private LayoutInflater inflater;
    public Context context;
    private List<String> urls;

    public ImageAdapter(Context context,List<String> urls){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.urls = urls;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.image_cards,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

            Picasso.with(context).load(urls.get(i)).centerCrop().fit().into(viewHolder.image);
            viewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, ViewImageActivity.class);
                    intent.putExtra("imageUrl", urls.get(i));
                    context.startActivity(intent);
                }

            });

    }



    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);


            image = (ImageView) itemView.findViewById(R.id.background_image_view);
        }
    }

}
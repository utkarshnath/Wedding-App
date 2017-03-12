package com.shikhar.weddingappsample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by utkarshnath on 02/02/16.
 */
public class OurWeddingadapter extends RecyclerView.Adapter<OurWeddingadapter.ViewHolder> {



    private LayoutInflater inflater;
    public Context context;
    private OurweddingTemplate template;

    public OurWeddingadapter(Context context,OurweddingTemplate template){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.template = template;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Log.d("!@#preweddingadpter", template.preWedding.size() + "  ");
        if(i==0){
            Picasso.with(context).load(template.preWedding.get(0)).centerCrop().fit().into(viewHolder.image1);
            Picasso.with(context).load(template.preWedding.get(1)).centerCrop().fit().into(viewHolder.image2);
            Picasso.with(context).load(template.preWedding.get(2)).centerCrop().fit().into(viewHolder.image3);
            Picasso.with(context).load(template.preWedding.get(3)).centerCrop().fit().into(viewHolder.image4);
            viewHolder.albumName.setText("Pre-Wedding Shoot");
            viewHolder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.preWedding);
                    context.startActivity(i);
                }
            });
            viewHolder.albumName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.preWedding);
                    context.startActivity(i);
                    Log.d("AlbumView", template.preWedding.toString());
                }
            });
            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.preWedding.get(0));
                    context.startActivity(i);
                }
            });
            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.preWedding.get(1));
                    context.startActivity(i);
                }
            });
            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.preWedding.get(2));
                    context.startActivity(i);
                }
            });
            viewHolder.image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.preWedding.get(3));
                    context.startActivity(i);
                }
            });

        }
        if(i==1){
            Picasso.with(context).load(template.engagement.get(0)).centerCrop().fit().into(viewHolder.image1);
            Picasso.with(context).load(template.engagement.get(1)).centerCrop().fit().into(viewHolder.image2);
            Picasso.with(context).load(template.engagement.get(2)).centerCrop().fit().into(viewHolder.image3);
            Picasso.with(context).load(template.engagement.get(3)).centerCrop().fit().into(viewHolder.image4);
            viewHolder.albumName.setText("Engagement");
            viewHolder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.engagement);
                    context.startActivity(i);
                }
            });
            viewHolder.albumName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.engagement);
                    context.startActivity(i);
                }
            });
            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.engagement.get(0));
                    context.startActivity(i);
                }
            });
            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.engagement.get(1));
                    context.startActivity(i);
                }
            });
            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.engagement.get(2));
                    context.startActivity(i);
                }
            });
            viewHolder.image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.engagement.get(3));
                    context.startActivity(i);
                }
            });
        }
        if(i==2){
            Picasso.with(context).load(template.wedding.get(0)).centerCrop().fit().into(viewHolder.image1);
            Picasso.with(context).load(template.wedding.get(1)).centerCrop().fit().into(viewHolder.image2);
            Picasso.with(context).load(template.wedding.get(2)).centerCrop().fit().into(viewHolder.image3);
            Picasso.with(context).load(template.wedding.get(3)).centerCrop().fit().into(viewHolder.image4);
            viewHolder.albumName.setText("The Wedding");
            viewHolder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.wedding);
                    context.startActivity(i);
                }
            });
            viewHolder.albumName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.wedding);
                    context.startActivity(i);
                }
            });
            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.wedding.get(0));
                    context.startActivity(i);
                }
            });
            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.wedding.get(1));
                    context.startActivity(i);
                }
            });
            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.wedding.get(2));
                    context.startActivity(i);
                }
            });
            viewHolder.image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.wedding.get(3));
                    context.startActivity(i);
                }
            });
        }
        if(i==3){
            Picasso.with(context).load(template.postWedding.get(0)).centerCrop().fit().into(viewHolder.image1);
            Picasso.with(context).load(template.postWedding.get(1)).centerCrop().fit().into(viewHolder.image2);
            Picasso.with(context).load(template.postWedding.get(2)).centerCrop().fit().into(viewHolder.image3);
            Picasso.with(context).load(template.postWedding.get(3)).centerCrop().fit().into(viewHolder.image4);
            viewHolder.albumName.setText("Post-Wedding Album");
            viewHolder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.postWedding);
                    context.startActivity(i);
                }
            });
            viewHolder.albumName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.postWedding);
                    context.startActivity(i);
                }
            });
            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.postWedding.get(0));
                    context.startActivity(i);
                }
            });
            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.postWedding.get(1));
                    context.startActivity(i);
                }
            });
            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.postWedding.get(2));
                    context.startActivity(i);
                }
            });
            viewHolder.image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.postWedding.get(3));
                    context.startActivity(i);
                }
            });
        }
        if(i==4){
            Picasso.with(context).load(template.sangeet.get(0)).centerCrop().fit().into(viewHolder.image1);
            Picasso.with(context).load(template.sangeet.get(1)).centerCrop().fit().into(viewHolder.image2);
            Picasso.with(context).load(template.sangeet.get(2)).centerCrop().fit().into(viewHolder.image3);
            Picasso.with(context).load(template.sangeet.get(3)).centerCrop().fit().into(viewHolder.image4);
            viewHolder.albumName.setText("The Sangeet");
            viewHolder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.sangeet);
                    context.startActivity(i);
                }
            });
            viewHolder.albumName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, PhotoAlbumActivity.class);
                    i.putExtra("imageUrls", template.sangeet);
                    context.startActivity(i);
                }
            });
            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.sangeet.get(0));
                    context.startActivity(i);
                }
            });
            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.sangeet.get(1));
                    context.startActivity(i);
                }
            });
            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.sangeet.get(2));
                    context.startActivity(i);
                }
            });
            viewHolder.image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewImageActivity.class);
                    i.putExtra("imageUrl", template.sangeet.get(3));
                    context.startActivity(i);
                }
            });
        }


    }



    @Override
    public int getItemCount() {
        return noOfItem(template);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image1;
        public ImageView image2;
        public ImageView image3;
        public ImageView image4;
        public TextView albumName;
        public Button viewAll;

        public ViewHolder(View itemView) {
            super(itemView);


            image1 = (ImageView) itemView.findViewById(R.id.wedding_image1);
            image2 = (ImageView) itemView.findViewById(R.id.wedding_image2);
            image3 = (ImageView) itemView.findViewById(R.id.wedding_image3);
            image4 = (ImageView) itemView.findViewById(R.id.wedding_image4);
            albumName = (TextView) itemView.findViewById(R.id.albumName);
            viewAll = (Button) itemView.findViewById(R.id.view_all);
        }
    }

    int noOfItem(OurweddingTemplate template){
        int x = picPresentOrNot(template.engagement)+
                picPresentOrNot(template.postWedding)+
                picPresentOrNot(template.preWedding)+
                picPresentOrNot(template.wedding)+
                picPresentOrNot(template.sangeet);
        Log.d("!@#itemno", x + "");
        Log.d("!@#prewedding", template.preWedding.size() + "");

        return x;
    }

    int picPresentOrNot(ArrayList<String> list){
        if(list.size()>0){
            return 1;
        }else return 0;
    }
}
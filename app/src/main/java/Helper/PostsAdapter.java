package Helper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shikhar.weddingappsample.Comments;
import com.shikhar.weddingappsample.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import Models.Post;
import Models.Reminder;

/**
 * Created by shikharkhetan on 8/25/15.
 */
public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public PostsAdapter( ArrayList<Object> posts, Activity context) {
        this.posts = posts;
        this.context = context;
    }


    private byte[] LoadByteArrayFromFile(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
        byte[] image = stream.toByteArray();
        return image;
    }

    private final int REMINDER = 0, POST = 1;
    int position;
    ArrayList<Object> posts;
    Activity context;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case REMINDER:
                View v2 = inflater.inflate(R.layout.reminder_layout_home, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
            case POST:
                View v1 = inflater.inflate(R.layout.post_layout, parent, false);
                viewHolder = new ViewHolder(v1);
                break;
            default:
                return null;
        }
        return viewHolder;

//        ViewHolder pvh = new ViewHolder(v);
//        return pvh;

    }

    void getBytesFromPost(final String feedId, final int type){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("FeedTemplate");
        query.getInBackground(feedId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                ParseFile fileObject = object.getParseFile("Image");
                if (fileObject != null) {
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null && data != null) {

                                // Decode the Byte[] into
                                // Bitmap
                                downloadImage(data,feedId,type);
                                Log.d("!@#test", "We've got data in data.");
                            } else {
                                Log.d("test",
                                        "There was a problem downloading the data.");
                                //getContentResolver().insert(DatabaseContract.CONTENT_URI, contentValues);
                            }

                        }
                    });
                }
            }
        });
    }



    private void downloadImage(byte[] data,String feedId,int type) {


        try {
            //First we create a new Folder named Speed Share if it does not exist
             File folder = new File(Environment.getExternalStorageDirectory() + "/Wedding App");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }

            //Now prepare to receive data
            if (success) {
                //Toast.makeText(context,post.getFeedId()+"",Toast.LENGTH_SHORT).show();
                //Initialize output Stream and Output file's full path
                FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Wedding App" + "/" + feedId+".jpg");
                BufferedOutputStream bufOutputStream = new BufferedOutputStream(fileOutputStream);

                //Byte Buffer to read from the input stream
                byte[] contents = new byte[1024];

                //No. of bytes in one read() call
                int bytesRead = 0;
                InputStream myInputStream = new ByteArrayInputStream(data);

                //Start reading from inputStream and write on the output Stream
                while ((bytesRead = myInputStream.read(contents)) != -1) {
                    bufOutputStream.write(contents, 0, bytesRead);
                }

                //Hopefully we are done receiving so lets do the housekeeping stuff
                bufOutputStream.flush();
                myInputStream.close();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File folder1 = new File(Environment.getExternalStorageDirectory() + "/Wedding App" + "/" + feedId+".jpg");
        if(type == 0 ){
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            Uri screenshotUri = Uri.parse(folder1.getPath());
            sharingIntent.setType("image/jpeg");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        }
        if(type == 1){
            Toast.makeText(context,"downloading file",Toast.LENGTH_SHORT).show();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(folder1);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        }
        return ;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewholder, final int position) {

        switch (viewholder.getItemViewType()) {
            case REMINDER:
                ViewHolder2 vh2 = (ViewHolder2) viewholder;
                final Reminder reminder = (Reminder) posts.get(position);
                vh2.reminderTitle.setText(reminder.getTitle());
                String myFormat = "HH:mm"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String dateFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat, Locale.US);
                vh2.date.setText(sdf1.format(reminder.date)+"");
                vh2.time.setText(sdf.format(reminder.getDate()) + "");
                vh2.reminderTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReminderHelperFunctions reminderHelperFunctions = new ReminderHelperFunctions(context);
                        reminderHelperFunctions.deletefromDatabase(reminder);
                    }
                });
                break;
            case POST:
                ViewHolder holder = (ViewHolder) viewholder;
                final Post post = (Post) posts.get(position);
                holder.Uploadername.setText(post.getUser());
                //Toast.makeText(context, "efff", Toast.LENGTH_SHORT).show();
                holder.description.setText(post.getDescription());
//        holder.uploadedPhoto.setImageBitmap(posts.get(position).getImage());

                Picasso.with(context).load(post.getImageUrl()).fit().into(holder.uploadedPhoto);


                Picasso.with(context).load("https://graph.facebook.com/" + post.getUploaderFbId() + "/picture?type=small")
                        .transform(new CircleTransform())
                        .into(holder.UploaderPhoto);
                holder.commentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(context, Comments.class);
                        intent.putExtra("postId", post.getFeedId());
                        context.startActivity(intent);
                    }
                });
//        SharePhoto photo = new SharePhoto.Builder()
//                .setBitmap(posts.get(position).getImage())
//                .build();
//        SharePhotoContent content = new SharePhotoContent.Builder()
//                .addPhoto(photo)
//                .build();
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(post.getImage())
                        .build();
                final SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
//        holder.shareButton.setShareContent(content);
                holder.shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ShareDialog.show((Activity) context, content);
                        Toast.makeText(context,"Just a moment",Toast.LENGTH_LONG).show();

//                holder.shareButton.setShareContent(content);
                    }
                });
                holder.shareExternalButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getBytesFromPost(post.getFeedId(),0);
                    }
                });
                holder.downloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getBytesFromPost(post.getFeedId(),1);
                    }
                });

                break;
            default:

                break;
        }


        this.position = position;
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (posts.get(position) instanceof Reminder) {
            return REMINDER;
        } else if (posts.get(position) instanceof Post) {
            return POST;
        }
        return -1;    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout postCard;
        public ImageView UploaderPhoto;
        public TextView Uploadername;
        public TextView description;
        public ImageView uploadedPhoto;
        public ImageView shareButton;
        public ImageView commentButton;
        public ImageView downloadButton;
        public ImageView shareExternalButton;
        public ViewHolder(View itemView) {
            super(itemView);
            postCard = (FrameLayout) itemView.findViewById(R.id.cv);
            Uploadername = (TextView) itemView.findViewById(R.id.uploaderName);
            UploaderPhoto = (ImageView) itemView.findViewById(R.id.uploaderdp);
            description = (TextView) itemView.findViewById(R.id.discription);
            uploadedPhoto = (ImageView) itemView.findViewById(R.id.uploadedImage);
            commentButton = (ImageView) itemView.findViewById(R.id.commentbutton);
            downloadButton = (ImageView) itemView.findViewById(R.id.downloadbutton);
            shareButton = (ImageView) itemView.findViewById(R.id.share_button);
            shareExternalButton = (ImageView) itemView.findViewById(R.id.share_external_button);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView reminderTitle;
        public TextView date;
        public TextView time;
        public ViewHolder2(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            reminderTitle = (TextView) itemView.findViewById(R.id.reminderTitle);
            time = (TextView) itemView.findViewById(R.id.time);

        }
    }
}
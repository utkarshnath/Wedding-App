package Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
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
import com.shikhar.weddingappsample.Comments;
import com.shikhar.weddingappsample.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Models.Post;

/**
 * Created by shikharkhetan on 1/17/16.
 */
public class EventSpecificAdapter extends RecyclerView.Adapter<EventSpecificAdapter.ViewHolder> {


    int position;
    ArrayList<Post> posts;
    Context context;

    public EventSpecificAdapter( ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    private File downloadImage(Post post) {


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
                FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Wedding App" + "/" + post.getFeedId()+".jpg");
                BufferedOutputStream bufOutputStream = new BufferedOutputStream(fileOutputStream);

                //Byte Buffer to read from the input stream
                byte[] contents = new byte[1024];

                //No. of bytes in one read() call
                int bytesRead = 0;
                InputStream myInputStream = new ByteArrayInputStream(post.getBytes());

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

        File folder1 = new File(Environment.getExternalStorageDirectory() + "/Wedding App" + "/" + post.getFeedId()+".jpg");
        return folder1;
    }

    @Override
    public EventSpecificAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        EventSpecificAdapter.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.post_layout, parent, false);
        viewHolder = new EventSpecificAdapter.ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventSpecificAdapter.ViewHolder holder, int position) {
        final Post post = (Post) posts.get(position);
        holder.Uploadername.setText(post.getUser());
        //Toast.makeText(context, "efff", Toast.LENGTH_SHORT).show();
        holder.description.setText(post.getDescription());
//        holder.uploadedPhoto.setImageBitmap(posts.get(position).getImage());
        Picasso.with(context).load(post.getImageUrl()).into(holder.uploadedPhoto);

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
                Toast.makeText(context, "Just a moment", Toast.LENGTH_LONG).show();

//                holder.shareButton.setShareContent(content);
            }
        });
        holder.shareExternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                File file = downloadImage(post);
                Uri screenshotUri = Uri.parse(file.getPath());
                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
//                file.delete();
            }
        });
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = downloadImage(post);
                Toast.makeText(context,"downloading file",Toast.LENGTH_SHORT).show();
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);

            }
        });

    }




    @Override
    public int getItemCount() {
        return posts.size();
    }

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
}

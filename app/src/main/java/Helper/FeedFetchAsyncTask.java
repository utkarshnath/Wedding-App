package Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import Models.Post;

/**
 * Created by shikharkhetan on 1/10/16.
 */
public class FeedFetchAsyncTask extends AsyncTask<Context, Void, ArrayList<Post> > {


    Context context;
    ArrayList<Post> mPosts;
    public FeedFetchedListener listener;

    public interface FeedFetchedListener{
        public void feedFetched(ArrayList<Post> posts);
    }
    @Override
    protected ArrayList<Post> doInBackground(Context... params) {
        context = params[0];
        mPosts = new ArrayList<Post>();
        //Fetch data from server by creating method given below
        //final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Downloading Image...", true);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FeedTemplate");
        query.orderByDescending("createdAt");
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {
                if (e == null) {
//                    mPosts.clear();
                    for (int i = 0; i < list.size(); i++) {
                        ParseFile fileObject = (ParseFile) list.get(i).get("Image");
                        Log.d("fileUrl", fileObject.getUrl());
                        final String imageUrl = fileObject.getUrl();
                        final int finalI = i;
                        fileObject.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    Log.d("test", "We've got data in data.");
                                    // Decode the Byte[] into
                                    // Bitmap
//                                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    // Close progress dialog
//                                    byte[] bytes = LoadByteArrayFromFile(bmp);
                                    Post post = new Post(list.get(finalI).getString("UploaderName"),
                                            list.get(finalI).getString("UploaderProfile"), list.get(finalI).getString("Discription"),
                                            list.get(finalI).getObjectId());
                                    post.setImageUrl(imageUrl);
//                                            mPosts.add(new Post(list.get(finalI).getString("UploaderName"),
//                                                    list.get(finalI).getString("UploaderProfile"), list.get(finalI).getString("Discription"),
//                                                    list.get(finalI).getObjectId(), bmp, bytes));
                                    mPosts.add(post);


                                } else {
                                    Log.d("test",
                                            "There was a problem downloading the data.");
                                }
                                //progressDialog.dismiss();
//                                adapter.notifyDataSetChanged();
                            }
                        });
                    }


                } else {

                }
            }
        });
        return mPosts;
    }

    @Override
    protected void onPostExecute(ArrayList<Post> posts) {
        listener.feedFetched(posts);
    }

    private byte[] LoadByteArrayFromFile(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
        byte[] image = stream.toByteArray();
        return image;
    }
}

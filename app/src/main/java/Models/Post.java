package Models;

import android.graphics.Bitmap;

/**
 * Created by shikharkhetan on 8/25/15.
 */
public class Post {

    String uploaderName;
    String description;
    String feedId;
    Bitmap image;
    String uploaderFbId;
    byte[] bytes;
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Post(String uploaderName,String uploaderFbId,String description,String feedId) {
        this.uploaderName = uploaderName;
        //this.bytes = bytes;
        this.uploaderFbId = uploaderFbId;
        this.description = description;
        this.feedId = feedId;
       // this.image = image;
    }



    public Bitmap getImage() {
        return image;
    }

    public byte[] getBytes(){
        return bytes;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }





    public String getDescription() {
        return description;
    }

    public String getUploaderFbId() {
        return uploaderFbId;
    }

    public String getFeedId() {
        return feedId;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return uploaderName;
    }

    public void setUser(String user) {
        this.uploaderName = user;
    }


}
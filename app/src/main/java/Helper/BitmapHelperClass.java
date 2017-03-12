package Helper;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by utkarshnath on 16/10/15.
 */
public class BitmapHelperClass extends AsyncTask<Void,Void,Uri> {

    Uri inputUri;
    ContentResolver contentResolver;
    Context context;
    public  String path;
    String realPath;
    public myHelper listener;

    public interface myHelper{
        public void passUri(Uri uri);
    }

    public BitmapHelperClass(Uri inputUri, ContentResolver contentResolver, Context context,String actualPath) {
        this.inputUri = inputUri;
        this.contentResolver = contentResolver;
        this.context = context;
        path = actualPath;
    }


    @Override
    protected Uri doInBackground(Void... params) {

        if (Build.VERSION.SDK_INT < 11)
            realPath = getRealPathFromURI_BelowAPI11(context, inputUri);

            // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            realPath = getRealPathFromURI_API11to18(context, inputUri);

            // SDK > 19 (Android 4.4)
        else
          //  realPath = getRealPathFromURI_API19(context,inputUri);
            realPath = path;
        Bitmap bitmap1 = decodeSampledBitmapFromfile(realPath, 1000, 1000);
        Uri finalInputUri = getImageUri(bitmap1);
        return finalInputUri;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        Log.v("@#$", "filepath:   " + filePath.toString());
        return filePath;
    }


    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromfile(String filepath,
                                                     int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        BitmapFactory.decodeFile(filepath,options);


        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath,options);
    }



    public Uri getImageUri( Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] image = bytes.toByteArray();
        // String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        File file = downloadImage(image);
        return  Uri.fromFile(file);

    }

    private File downloadImage(byte[] imagebytes) {


        try {
            //First we create a new Folder named Speed Share if it does not exist
            File folder = new File(Environment.getExternalStorageDirectory() + "/Wedding App");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }

            //Now prepare to receive data
            if (success) {

                //Initialize output Stream and Output file's full path
                FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Wedding App" + "/compressed.jpg");
                BufferedOutputStream bufOutputStream = new BufferedOutputStream(fileOutputStream);

                //Byte Buffer to read from the input stream
                byte[] contents = new byte[1024];

                //No. of bytes in one read() call
                int bytesRead = 0;
                InputStream myInputStream = new ByteArrayInputStream(imagebytes);

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

        File folder1 = new File(Environment.getExternalStorageDirectory() + "/Wedding App" + "/compressed.jpg");
        return folder1;
    }

    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);
        listener.passUri(uri);
        return ;
    }
}

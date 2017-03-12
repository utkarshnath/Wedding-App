package com.shikhar.weddingappsample;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Helper.FeedFetchAsyncTask;
import Helper.PostsAdapter;
import Helper.ReminderHelperFunctions;
import Models.Post;
import Models.ReminderDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends android.support.v4.app.Fragment implements FeedFetchAsyncTask.FeedFetchedListener {


    public FeedFragment() {
        // Required empty public constructor
    }
    PostsAdapter adapter;
    RecyclerView rv;
    ArrayList<Object> mPosts;
    SharedPreferences sp;
    FrameLayout fl;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View v;
    //View opaqueCover;
    boolean isFamOpen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosts = new ArrayList<Object>();
//        mPosts.addAll(fetchRemindersFromDatabase());
        fetchDataFromServer();
        adapter = new PostsAdapter( mPosts, getActivity() );
    }

    private ArrayList fetchRemindersFromDatabase() {
        ReminderHelperFunctions reminderHelperFunctions = new ReminderHelperFunctions(getActivity());
        if(reminderHelperFunctions.fetchReminderFromDatabase().size() == 0) {
            return mPosts;
        } else
            return reminderHelperFunctions.fetchReminderFromDatabase();
//        Reminder reminder = new Reminder("Set a Demo Reminder", new Date(2016, 12, 22));
//        mPosts.add(reminder);
//        return mPosts;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_feed, container, false);
        rv = (RecyclerView) v.findViewById(R.id.home_recycler_view);
        sp = this.getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.Feed_swipe_refresh_layout);
        //setColorSchemeResources();
        //opaqueCover = v.findViewById(R.id.opaque_view);
        setUpFloatingActionMenu(v);
        fl = (FrameLayout) v.findViewById(R.id.fl);
        rv.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPosts = new ArrayList<Object>();
                mPosts.addAll(fetchRemindersFromDatabase());
                fetchDataFromServer();
//                adapter.notifyDataSetChanged();
            }

        });





        return v;
    }



    private void fetchCountdown(){

        Date todaysDate = (Date) Calendar.getInstance().getTime();
        Date eventDate = new Date(2016,0,22,18,20);
        int x = no_of_daysLeft(eventDate,todaysDate);
        if(x==0){
            if(no_of_hr_left(eventDate,todaysDate)==0){
                x = no_of_min_left(eventDate,todaysDate);
            }else
                x = no_of_hr_left(eventDate,todaysDate);
        }
        Toast.makeText(getActivity(),x+"",Toast.LENGTH_LONG).show();
    }

    private byte[] LoadByteArrayFromFile(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
        byte[] image = stream.toByteArray();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2 && requestCode == 2)  {
            adapter.notifyDataSetChanged();
        }
        else if (requestCode == 1 && resultCode == getActivity().RESULT_OK && null != data) {
            if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {

                Bitmap thumbnail = data.getParcelableExtra("data");
                Uri fullPhotoUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Uri selectedImage = data.getData();
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Intent postAdd = new Intent();
                postAdd.setClass(getActivity(), CropActivity.class);
                postAdd.putExtra("fullPhotoUri", fullPhotoUri);
                // content://media/external/images/media/82398
                Log.d("UriCameraStillFinal!@", fullPhotoUri + "");
                Log.d("UriCameraStillFinal!@#", picturePath + "");
                postAdd.putExtra("ActualPath",picturePath);
               // /storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20160123-WA0001.jpg
                startActivityForResult(postAdd, 2);

                        }
                    }

        if (requestCode == TAKE_PICTURE && resultCode == getActivity().RESULT_OK) {

//            String absoluteFilePath = data.getStringExtra("Absolutefilepath");
//            String imageUri = data.getStringExtra(MediaStore.EXTRA_OUTPUT);

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

//            Intent postAdd = new Intent();
//            postAdd.setClass(getActivity(), CropActivity.class);
//            Log.d("UriCameraFinal", imageUri.toString());
//            postAdd.putExtra("fullPhotoUri", imageUri);
//            postAdd.putExtra("ActualPath",absoluteFilePath);
//            startActivityForResult(postAdd, 2);

//            Bundle extras = data.getExtras();
            Bitmap imageBitmap ;
            if(data.getData()==null){
                imageBitmap = (Bitmap)data.getExtras().get("data");
            }else{
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //imageView.setImageBitmap(imageBitmap);
        }

    }





    private File createImageFile() throws IOException {
        // Create an image file name
        String mCurrentPhotoPath;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        galleryAddPic(mCurrentPhotoPath);
        return image;
    }

    private void galleryAddPic(String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                takePictureIntent.putExtra("Absolutefilepath", photoFile.getAbsolutePath() + "");
                Log.d("UriCameraFinal!@", Uri.fromFile(photoFile) + "");
                // file:///storage/emulated/0/Pictures/JPEG_20160126_112728_125572620.jpg
                Log.d("pathCameraFinal!@", photoFile.getAbsolutePath() + "");
                // /storage/emulated/0/Pictures/JPEG_20160126_112728_125572620.jpg
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = null;
        try {
            photo = createImageFile();
        } catch (IOException e) {
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        Log.d("current1", Uri.fromFile(photo).toString());

        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }



    int no_of_hr_left(Date eventdate,Date todaysdate){
        return eventdate.getHours() - todaysdate.getHours();
    }

    int no_of_min_left(Date eventdate,Date todaysdate){
        return eventdate.getMinutes()-todaysdate.getMinutes();
    }
    int no_of_daysLeft(Date eventdate,Date todaysdate){
        if(eventdate.getMonth()==todaysdate.getMonth()){
            return eventdate.getDate()-todaysdate.getDate();
        }
        Date temp = todaysdate;
        int days =totaldays(temp.getMonth())-temp.getDate();
        temp.setMonth(temp.getMonth()+1);
        while(temp.getMonth()!=eventdate.getMonth()){
            days += totaldays(temp.getMonth());
            temp.setMonth(temp.getMonth()+1);
        }
        days += eventdate.getDate();
        return days;
    }

    int totaldays(int month){
        switch (month){
            case 0:
                return 31;
            case 1:
                return 28;
            case 2:
                return 31;
            case 3:
                return 30;
            case 4:
                return 31;
            case 5:
                return 30;
            case 6:
                return 31;
            case 7:
                return 31;
            case 8:
                return 30;
            case 9:
                return 31;
            case 10:
                return 30;
            case 11:
                return 31;

            default: return 31;
        }
    }




    Uri imageUri;
    private static final int TAKE_PICTURE = 10;

private void setUpFloatingActionMenu(View v) {
//
//    final com.getbase.floatingactionbutton.FloatingActionButton cameraAction =
//            (com.getbase.floatingactionbutton.FloatingActionButton) v.findViewById(R.id.camera_action);
//    cameraAction.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            dispatchTakePictureIntent();
//        }
//    });
    final com.getbase.floatingactionbutton.FloatingActionButton galleryAction =
            (com.getbase.floatingactionbutton.FloatingActionButton)v.findViewById(R.id.gallery_action);
    galleryAction.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            galleryAction.setTitle("Opening Gallery");
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        }
    });
    final com.getbase.floatingactionbutton.FloatingActionButton reminderAddAction =
            (com.getbase.floatingactionbutton.FloatingActionButton)v.findViewById(R.id.reminder_add_action);
    reminderAddAction.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ReminderDialog reminderDialog = new ReminderDialog(getActivity(), v, getActivity());
            reminderDialog.createReminderDialog();

        }
    });
    final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);

}

    private void fetchDataFromServer() {

//        FeedFetchAsyncTask task = new FeedFetchAsyncTask();
//        task.listener = this;
//        task.execute(getActivity());

                //Fetch data from server by creating method given below
//                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Downloading Image...", true);
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FeedTemplate");
                query.orderByDescending("createdAt");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            //mPosts.clear();
                            for (int i = 0; i < list.size(); i++) {
                                ParseFile fileObject = (ParseFile) list.get(i).get("Image");
                                Log.d("fileUrl", fileObject.getUrl());
                                final String imageUrl = fileObject.getUrl();
                                final int finalI = i;
                                Post post = new Post(list.get(finalI).getString("UploaderName"),
                                        list.get(finalI).getString("UploaderProfile"),
                                        list.get(finalI).getString("Discription"),
                                        list.get(finalI).getObjectId());
                                post.setImageUrl(imageUrl);
//                                            mPosts.add(new Post(list.get(finalI).getString("UploaderName"),
//                                                    list.get(finalI).getString("UploaderProfile"), list.get(finalI).getString("Discription"),
//                                                    list.get(finalI).getObjectId(), bmp, bytes));
                                mPosts.add(post);
//                                progressDialog.dismiss();
//                                fileObject.getDataInBackground(new GetDataCallback() {
//                                    public void done(byte[] data, ParseException e) {
//                                        if (e == null) {
//                                            Log.d("test", "We've got data in data.");
//                                            // Decode the Byte[] into
//                                            // Bitmap
////                                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                                            // Close progress dialog
////                                            byte[] bytes = LoadByteArrayFromFile(bmp);
//
//
//
//                                        } else {
//                                            Log.d("test",
//                                                    "There was a problem downloading the data.");
//                                        }
//
//                                    }
//                                });
                            }


                            adapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                        } else {

                        }
                    }
                });

                return ;
            }


    @Override
    public void feedFetched(ArrayList<Post> posts) {
        mPosts.add(posts);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
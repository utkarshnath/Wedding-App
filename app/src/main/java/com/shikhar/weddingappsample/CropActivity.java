package com.shikhar.weddingappsample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;

import Helper.BitmapHelperClass;

public class CropActivity extends Activity implements BitmapHelperClass.myHelper {

    private ImageView resultView;
    Uri outputUri;
    Uri inputUri;
    Uri finalInputUri;
    String actualPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        resultView = (ImageView) findViewById(R.id.result_image);
        Intent i = getIntent();
        inputUri = (Uri) i.getExtras().get("fullPhotoUri");
        actualPath = i.getExtras().getString("ActualPath");
        BitmapHelperClass task = new BitmapHelperClass(inputUri, this.getContentResolver(), this,actualPath);
        task.listener = this;
        task.execute();
        outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));

        Crop.of(finalInputUri, outputUri).asSquare().start(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            intent.setClass(this, PostAddActivity.class);
            intent.putExtra("fullPhotoUri", outputUri);
            startActivity(intent);
            finish();
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void passUri(Uri uri) {
        finalInputUri = uri;
        Crop.of(finalInputUri, outputUri).asSquare().start(this);
    }
}
package com.shikhar.weddingappsample;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.parse.Parse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by utkarshnath on 16/09/15.
 */
public class Myapplication extends Application {

    @Override

    public void onCreate() {

        super.onCreate();
        Parse.initialize(this, "OCvhok5Gh0FhJhfAA6cKWysZiL53xehGC9zM3OO1", "EmROHXSF6RclFFvsZXu30qv9ZMPbWBI2a25LyCYF");

        try {
            PackageInfo info = null;
            try {
                info = getPackageManager().getPackageInfo(
                        "com.shikhar.weddingappsample",
                        PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Utkarsh", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {

        }

    }
}
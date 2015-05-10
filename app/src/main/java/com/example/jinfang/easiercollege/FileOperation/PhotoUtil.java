package com.example.jinfang.easiercollege.FileOperation;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jinfang on 5/2/15.
 */
public class PhotoUtil {

    private static final int MEDIA_TYPE_IMAGE = 1;

    private static final String APP_FOLDER = "EasierCollege";

    public static Uri getOutputMediaFileUri(int type, int id){
        return Uri.fromFile(getOutputMediaFile(type, id));
    }


    /** Create a File for saving an image */
    public static File getOutputMediaFile(int type, int id){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MyEasierCollegeApp");
        File path = new File(Environment.getExternalStorageDirectory().getPath()+"/"+APP_FOLDER+"/");

        // Create the storage directory if it does not exist
        if (! path.exists()){
            if (! path.mkdirs()){
                Log.d("MyEasierCollegeApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(path.getPath() + File.separator +
                    "IMG_"+id+ ".png");
        }
        else {
            return null;
        }



        return mediaFile;
    }

    public static boolean deleteUri(int id)
    {

        File path = new File(Environment.getExternalStorageDirectory().getPath()+"/"+APP_FOLDER+"/");
        if (path.exists())
        {
            File mediaFile = new File(path.getPath() + File.separator +
                    "IMG_"+id+ ".png");
            return mediaFile.delete();
        }

        return false;
    }

    public static Uri getUri(int id)
    {
        File path = new File(Environment.getExternalStorageDirectory().getPath()+"/"+APP_FOLDER+"/");
        File mediaFile = new File(path.getPath() + File.separator +
                "IMG_"+id+ ".png");
        return Uri.fromFile(mediaFile);
    }


}

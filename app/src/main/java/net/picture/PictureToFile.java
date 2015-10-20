package net.picture;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by kisstheraik on 15/10/14.
 */
public class PictureToFile {

    public static File bitmapToFile(Bitmap bitmap,String path){

        if(bitmap==null)return new File("");
        System.out.println("bit to file:"+bitmap);

        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" +path);
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

       bitmap.compress(format, quality, stream);


        System.out.println("file path:"+"/sdcard/" +path);


        return new File("/sdcard/" +path);



    }
}

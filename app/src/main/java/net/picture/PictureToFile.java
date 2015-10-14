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
        System.out.println("bit to file:"+bitmap);

        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" +"qw");
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

       bitmap.compress(format, quality, stream);


        return new File("/sdcard/" +"qw");



    }
}

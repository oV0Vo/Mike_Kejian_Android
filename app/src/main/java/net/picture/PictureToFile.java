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

        File destDir = new File("/sdcard/mike/user/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }



        if(path!=null){

            path=path.replaceAll("\\/","#");
            path=path.replaceAll("\\.","#");
        }

        if(bitmap==null)return new File("");

        System.out.println("/sdcard/mike/user/" +path);


        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/mike/user/" +path);
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block

            System.out.println("error in path:"+path);
            e.printStackTrace();
        }



   if(stream!=null){

       System.out.println("icon:存到本地 "+"/sdcard/mike/user/" +path);
       bitmap.compress(format, quality, stream);}


        System.out.println("file path:"+"/sdcard/mike/user/" +path);


        return new File("/sdcard/mike/user/" +path);



    }
}

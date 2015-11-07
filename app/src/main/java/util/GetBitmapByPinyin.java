package util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.kejian.mike.mike_kejian_android.R;

import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kisstheraik on 15/10/26.
 */
public class GetBitmapByPinyin {

    public static int num;

   // public static Hashtable<String,SoftReference<Bitmap>> bitmapHashtable=new Hashtable<>();







    //线程池
   // public static ExecutorService executorService= Executors.newCachedThreadPool();
   // ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void getBitmapByPinyin(String words,Context context,ImageView imageView){

        Random random=new Random();

        String p="course"+(random.nextInt(9)+1);

//        String p="course5";



        /*System.out.println("words :"+words);


        System.out.println("拼音:"+HanziToPinyin.getPinYin("国家地理"));*/

        String pinyin=HanziToPinyin.getPinYin(words);


        String path=null;

        if(pinyin.length()>0) {

            char tem=pinyin.charAt(0);

            if(tem>='a'&&tem<='z') {

                path = "char_" + tem;

                if(tem=='q'){
                    path = "no_pic";
                }
            }else{
                path="no_pic";
            }
        }
        else{

            path="no_pic";
        }



//        if(bitmapHashtable.containsKey(path)){
//
//           SoftReference<Bitmap> temreference= bitmapHashtable.get(path);
//
//            Bitmap bitmap=temreference.get();
//
//            imageView.setImageBitmap(bitmap);
//
//
//        }else{


            // GetBitmapFromLocal getBitmapFromLocal=new GetBitmapFromLocal();


        String packageName = context.getApplicationInfo().packageName;

        path=p;

        int sourceId= context.getResources().getIdentifier(path, "drawable", packageName);

        BitmapFactory.Options b=new BitmapFactory.Options();

        b.inSampleSize=2;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), sourceId,b);

        if(bitmap == null) {
            System.out.println("bitmap null");
        } else if(bitmap.getByteCount() == 0){
            System.out.println("bitmap size 0");
        }
        imageView.setImageBitmap(bitmap);

           // new GetBitmapFromLocal().execute("");






      //  }


        return;

    }


}

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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kisstheraik on 15/10/26.
 */
public class GetBitmapByPinyin {

    public static Hashtable<String,SoftReference<Bitmap>> bitmapHashtable=new Hashtable<>();

    public static int maxSize=500;

    public static Context context;

    public  static  ImageView imageView;

    public static  String picPath;


    //线程池
    public static ExecutorService executorService= Executors.newCachedThreadPool();
   // ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void getBitmapByPinyin(String words,Context context,ImageView imageView){

        GetBitmapByPinyin.context=context;
        GetBitmapByPinyin.imageView=imageView;


        System.out.println("拼音:"+HanziToPinyin.getPinYin(words).charAt(0));

        String path="char_"+HanziToPinyin.getPinYin(words).charAt(0);

        GetBitmapByPinyin.picPath=path;

        if(bitmapHashtable.containsKey(path)){

           SoftReference<Bitmap> temreference= bitmapHashtable.get(path);

            Bitmap bitmap=temreference.get();

            imageView.setImageBitmap(bitmap);


        }else{


             GetBitmapFromLocal getBitmapFromLocal=new GetBitmapFromLocal();

            new GetBitmapFromLocal().execute("");






        }


        return;

    }

    public static class GetBitmapFromLocal extends AsyncTask<String,Integer,Bitmap>{



        @Override
        public Bitmap doInBackground(String...Para){



            String packageName = context.getApplicationInfo().packageName;

            int sourceId= context.getResources().getIdentifier(picPath, "drawable", packageName);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), sourceId);

            return bitmap;
        }

        @Override
        public void onPostExecute(Bitmap bitmap){

            imageView.setImageBitmap(bitmap);

            if(maxSize<0){
                bitmapHashtable.clear();
                maxSize=500;
            }else {

                maxSize--;

                bitmapHashtable.put(picPath,new SoftReference<Bitmap>(bitmap));


            }



        }
    }
}

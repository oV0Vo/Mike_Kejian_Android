package net.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

/**
 * Created by kisstheraik on 15/10/15.
 */
public class DownloadPicture {

    private Context context;
    private Bitmap bitmap = null;
    private ImageView imageView;
    private static Hashtable<String,SoftReference<Bitmap>> memoryCache = new Hashtable<>();
    public static int maxMapSize = 500;
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public DownloadPicture(Context context){

        this.context=context;

    }
    public DownloadPicture(Context context,ImageView imageView,String url,String path){

        this.context=context;
        this.imageView=imageView;

        Bitmap bit=getBitMapFromNet(url,path);

        imageView.setImageBitmap(bit);

    }




    public Bitmap getBitMapFromNet(String url,String picturePath){



        MessagePrint.print("Start getBitMapFromNet");

        if(memoryCache.containsKey(picturePath)){
            SoftReference<Bitmap> softReference = memoryCache.get(picturePath);
            bitmap = softReference.get();
            if(bitmap != null){
                return null;
            }
        }

        Thread task=new Thread(new DownloadPicTask(url,picturePath));

        cachedThreadPool.execute(task);

//        task.start();

        return null;

    }

    private class DownloadPicTask implements   Runnable{

        private String picUrl;
        private String picturePath;

        public DownloadPicTask(String url,String path){

            MessagePrint.print("Start Download pic");

            this.picturePath=path;
            this.picUrl=url;

        }


        public void run(){

            if(picturePath!=null&&(!picturePath.equals(""))) {

                picturePath = picturePath.replaceAll("\\/", "#");
                picturePath = picturePath.replaceAll("\\.", "#");

            }
            else{
                picturePath="temp";
            }

            File  file=new File("/sdcard/mike/user/"+picturePath);


            try {

                FileInputStream inputStream = new FileInputStream(file);

                BitmapFactory.Options b=new BitmapFactory.Options();
                b.inSampleSize=7;
                bitmap=BitmapFactory.decodeStream(inputStream,null,b);
                if(memoryCache.size() > maxMapSize){
                    //超过数量直接清空
                    memoryCache.clear();
                }
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                memoryCache.put(picturePath,softReference);
                inputStream.close();

            }catch (Exception e){

                e.printStackTrace();
            }

            if(bitmap!=null){

                System.out.println("get bitmap from local");
                UIHandler handler=new UIHandler(context.getMainLooper());

                Message message=handler.obtainMessage(1,1,1,1);

                handler.sendMessage(message);
                return ;

            }


            MessagePrint.print("Start Download pic");

            InputStream inputStream=null;

            try {

                URL url = new URL(picUrl);

                URLConnection connection=url.openConnection();

                connection.connect();

                inputStream=connection.getInputStream();

                BitmapFactory.Options bit=new BitmapFactory.Options();

                bit.inSampleSize=4;

                 bitmap= BitmapFactory.decodeStream(inputStream);
                if(memoryCache.size() > maxMapSize){
                    //超过数量直接清空
                    memoryCache.clear();
                }
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                memoryCache.put(picturePath,softReference);


            }catch(Exception e){
                e.printStackTrace();
            }

            MessagePrint.print("save the pic to local ");

            PictureToFile.bitmapToFile(bitmap, picturePath);


            UIHandler handler=new UIHandler(context.getMainLooper());

            Message message=handler.obtainMessage(1,1,1,1);

            handler.sendMessage(message);




        }
    }

    public void updateView(Bitmap bitmap,ImageView imageView){

        imageView.setImageBitmap(bitmap);

    }

    public void updateView(Bitmap bitmap){

        MessagePrint.print("Start update view");

    }
    public void updateView(ImageView imageView){

        imageView.setImageBitmap(bitmap);

    }
    private class UIHandler extends android.os.Handler{

        public UIHandler(Looper looper){
            super(looper);

        }

        @Override
        public void handleMessage(Message message){

            MessagePrint.print("Start handle bitmap");

            switch(message.what){

                case 1:

                    if(imageView!=null)updateView(bitmap,imageView);
                    updateView(bitmap);break;

                default:super.handleMessage(message);

            }

        }

    }
}




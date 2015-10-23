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
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Handler;

/**
 * Created by kisstheraik on 15/10/15.
 */
public class DownloadPicture {

    private Context context;
    private Bitmap bitmap;
    private ImageView imageView;

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

        picturePath=picturePath.replaceAll("\\/","#");
        picturePath=picturePath.replaceAll("\\.","#");

        File  file=new File("/sdcard/mike/user/"+picturePath);
        Bitmap bitmap=null;

        try {

            FileInputStream inputStream = new FileInputStream(file);
            bitmap=BitmapFactory.decodeStream(inputStream);

        }catch (Exception e){

            e.printStackTrace();
        }

        if(bitmap!=null){

            System.out.println("get bitmap from local");

            return bitmap;

        }


        MessagePrint.print("Start getBitMapFromNet");

        Thread task=new Thread(new DownloadPicTask(url,picturePath));

        task.start();

        return null;

    }

    private class DownloadPicTask implements   Runnable{

        private String picUrl;
        private String picPath;

        public DownloadPicTask(String url,String path){

            MessagePrint.print("Start Download pic");

            this.picPath=path;
            this.picUrl=url;

        }


        public void run(){

            MessagePrint.print("Start Download pic");

            InputStream inputStream=null;

            try {

                URL url = new URL(picUrl);

                URLConnection connection=url.openConnection();

                connection.connect();

                inputStream=connection.getInputStream();

                 bitmap= BitmapFactory.decodeStream(inputStream);



            }catch(Exception e){
                e.printStackTrace();
            }

            MessagePrint.print("save the pic to local ");

            PictureToFile.bitmapToFile(bitmap, picPath);


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




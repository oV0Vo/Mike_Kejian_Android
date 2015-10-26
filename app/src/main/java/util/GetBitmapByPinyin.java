package util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by kisstheraik on 15/10/26.
 */
public class GetBitmapByPinyin {

    public static Bitmap getBitmapByPinyin(String words,Context context){


        System.out.println("拼音:"+HanziToPinyin.getPinYin(words).charAt(0));



        String packageName = context.getApplicationInfo().packageName;

        int sourceId= context.getResources().getIdentifier(HanziToPinyin.getPinYin(words).charAt(0)+"", "drawable", packageName);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), sourceId);

        return bitmap;

    }
}

package com.kejian.mike.mike_kejian_android.ui.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by violetMoon on 2015/10/28.
 */
public class MyImageCache implements ImageLoader.ImageCache {

    private static MyImageCache instance;

    private LruCache<String, Bitmap> cache;

    private MyImageCache(Context context) {

        int memClass = ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = 1024 * 1024 * memClass / 8;

        cache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    public static MyImageCache getInstance(Context context) {
        if(instance == null)
            instance = new MyImageCache(context);
        return instance;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}


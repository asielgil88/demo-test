package com.demo.utils;

import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by asielgil on 3/14/16.
 */
public class AppImageUtil {



    public static void loadImage(String uri, ImageView imageView) {
        if (uri != null) {

            try {

                ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisc(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .resetViewBeforeLoading(true)
                        .displayer(new FadeInBitmapDisplayer(300))
                        .build();

                //download and display image from url
                imageLoader.displayImage(uri, imageView, options);

            } catch (Exception ex) {
                Log.e("EX", ex.getMessage());
            }

        }
    }

    public static void displayImageRounded(String uri, ImageView imageView){

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new RoundedBitmapDisplayer(10))
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
                .build();


        //download and display image from url
        imageLoader.displayImage(uri, imageView, options);
    }


}


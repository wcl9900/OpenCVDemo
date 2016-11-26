package com.wcl.java.opencv;

import android.app.Application;

import org.opencv.android.OpenCVLoader;

/**
 * <p>Describe:
 * <p>Author:王春龙
 * <p>CreateTime:2016/11/11
 */
public class MyAplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        OpenCVLoader.initDebug();
    }
}

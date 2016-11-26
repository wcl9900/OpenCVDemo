package com.wcl.jniapi;

/**
 * Created by WCL on 2016/11/10.
 */
public class MyOpenCV {
    public static native int[] ImgFun(int[] buf, int w, int h);
    public static native void detectFeatures(long buf, long w, long h);
}

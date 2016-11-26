package com.wcl.java.opencv;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wcl.jniapi.MyOpenCV;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;


public class MainActivity extends Activity {

    private ImageView iv_image;
    private Button btn_edge, btn_restore;

    static {
        System.loadLibrary("myopencv");
    }

    private Button btnJava;
    private Button btnMatch;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("use NDK to convert image");
        iv_image = (ImageView) findViewById(R.id.iv_image);
        btn_edge = (Button) findViewById(R.id.btn_edge);
        btn_restore = (Button) findViewById(R.id.btn_restore);
        btnJava = (Button) findViewById(R.id.opencvjava);
        btnMatch = (Button) findViewById(R.id.opencvmatch);

        tvInfo = (TextView) findViewById(R.id.tv_info);

        btn_edge.setOnClickListener(new ClickEvent());
        btn_restore.setOnClickListener(new ClickEvent());
        btnJava.setOnClickListener(new ClickEvent());
        btnMatch.setOnClickListener(new ClickEvent());
        iv_image.setImageResource(R.mipmap.ic_launcher);
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(v == btn_edge){
                Bitmap img1 = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.splash)).getBitmap();

                int w = img1.getWidth(), h = img1.getHeight();

                int width = w;
                int height = h;
                Mat mRgba = new Mat(height, width, CvType.CV_8U, new Scalar(4));
                Mat mGray = new Mat(height, width, CvType.CV_8U, new Scalar(4));
                Mat output = new Mat();

                Utils.bitmapToMat(img1, mRgba);

                // do sth
                // Converts an image from one color space to another.
                Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY, 4);

                MyOpenCV.detectFeatures(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr(), output.getNativeObjAddr());

                // Then convert the processed Mat to Bitmap
                Bitmap resultBitmap = Bitmap.createBitmap(output.cols(),
                        output.rows(), Bitmap.Config.ARGB_8888);

                Utils.matToBitmap(output, resultBitmap);

                iv_image.setImageBitmap(resultBitmap);
            }
            else if(v == btnJava){
                Bitmap img1 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.splash1)).getBitmap();

                int width = img1.getWidth();
                int height = img1.getHeight();
                Mat mRgba = new Mat(height, width, CvType.CV_8U, new Scalar(4));
                Mat mGray = new Mat(height, width, CvType.CV_8U, new Scalar(4));
                Mat output = new Mat();

                Utils.bitmapToMat(img1, mRgba);
                Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY, 4);

                Mat desc = new Mat();
                FeatureDetector fd = FeatureDetector.create(FeatureDetector.ORB);
                MatOfKeyPoint mkp =new MatOfKeyPoint();
                fd.detect(mGray, mkp);
                DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.ORB);
                de.compute(mGray,mkp,desc );
                Features2d.drawKeypoints(mGray, mkp, output, new Scalar(
                    2, 254, 255), Features2d.DRAW_RICH_KEYPOINTS);
//                Features2d.drawKeypoints(mGray, mkp, output);

                Bitmap resultBitmap = Bitmap.createBitmap( mGray.width(), mGray.height(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(output, resultBitmap);

                iv_image.setImageBitmap(resultBitmap);
                de.write(Environment.getExternalStorageDirectory().getAbsolutePath() + "/desc.txt");
            }
            else if(v == btnMatch){
                Bitmap image_1 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.splash)).getBitmap();

                int width_1 = image_1.getWidth();
                int height_1 = image_1.getHeight();
                Mat mRgba_1 = new Mat(height_1, width_1, CvType.CV_8U, new Scalar(4));
                Mat mGray_1 = new Mat(height_1, width_1, CvType.CV_8U, new Scalar(4));
                Mat output_1 = new Mat();

                Utils.bitmapToMat(image_1, mRgba_1);
                Imgproc.cvtColor(mRgba_1, mGray_1, Imgproc.COLOR_RGB2GRAY, 4);

                Mat desc_1 = new Mat();
                FeatureDetector featureDetector_1 = FeatureDetector.create(FeatureDetector.ORB);
                MatOfKeyPoint matOfKeyPoint_1 =new MatOfKeyPoint();
                featureDetector_1.detect(mGray_1, matOfKeyPoint_1);
                DescriptorExtractor descriptorExtractor_1 = DescriptorExtractor.create(DescriptorExtractor.ORB);
                descriptorExtractor_1.compute(mGray_1,matOfKeyPoint_1,desc_1 );

                Features2d.drawKeypoints(mGray_1, matOfKeyPoint_1, output_1, new Scalar(
                        2, 254, 255), Features2d.DRAW_RICH_KEYPOINTS);
//                Features2d.drawKeypoints(mGray, mkp, output);

                Bitmap resultBitmap_1 = Bitmap.createBitmap( mGray_1.width(), mGray_1.height(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(output_1, resultBitmap_1);

                iv_image.setImageBitmap(resultBitmap_1);
                descriptorExtractor_1.write(Environment.getExternalStorageDirectory().getAbsolutePath() + "/desc_1.txt");

                Bitmap image_2 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.splash2)).getBitmap();

                int width_2 = image_2.getWidth();
                int height_2 = image_2.getHeight();
                Mat mRgba_2 = new Mat(height_2, width_2, CvType.CV_8U, new Scalar(4));
                Mat mGray_2 = new Mat(height_2, width_2, CvType.CV_8U, new Scalar(4));
                Mat output_2 = new Mat();

                Utils.bitmapToMat(image_2, mRgba_2);
                Imgproc.cvtColor(mRgba_2, mGray_2, Imgproc.COLOR_RGB2GRAY, 4);

                Mat desc_2 = new Mat();
                FeatureDetector featureDetector_2 = FeatureDetector.create(FeatureDetector.ORB);
                MatOfKeyPoint matOfKeyPoint_2 =new MatOfKeyPoint();
                featureDetector_2.detect(mGray_2, matOfKeyPoint_2);
                DescriptorExtractor descriptorExtractor_2 = DescriptorExtractor.create(DescriptorExtractor.ORB);
                descriptorExtractor_2.compute(mGray_2,matOfKeyPoint_2,desc_2 );

                Features2d.drawKeypoints(mGray_2, matOfKeyPoint_2, output_2, new Scalar(
                        2, 254, 255), Features2d.DRAW_RICH_KEYPOINTS);
//                Features2d.drawKeypoints(mGray, mkp, output);

                Bitmap resultBitmap_2 = Bitmap.createBitmap( mGray_2.width(), mGray_2.height(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(output_2, resultBitmap_2);

                iv_image.setImageBitmap(resultBitmap_2);
                descriptorExtractor_2.write(Environment.getExternalStorageDirectory().getAbsolutePath() + "/desc_2.txt");

                MatOfDMatch matches = new MatOfDMatch();
                DescriptorMatcher descriptormatcher = DescriptorMatcher
                        .create(DescriptorMatcher.BRUTEFORCE_HAMMING);
                descriptormatcher.match(desc_1, desc_2, matches);
                descriptormatcher.write(Environment.getExternalStorageDirectory().getAbsolutePath() + "/match.txt");

                Mat out_match = new Mat();
                Features2d.drawMatches(mGray_1, matOfKeyPoint_1, mGray_2, matOfKeyPoint_2, matches, out_match);
                DMatch[] dMatches = matches.toArray();

                Bitmap resultBitmapMatch= Bitmap.createBitmap( out_match.width(), out_match.height(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(out_match, resultBitmapMatch);

                iv_image.setImageBitmap(resultBitmapMatch);

                tvInfo.setText(dMatches != null ? dMatches.length+"" : "null");
            }
            else if (v == btn_restore){
                iv_image.setImageResource(R.drawable.splash);
                Toast.makeText(getApplicationContext(), "image has restored!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        OpenCVLoader.initDebug();
    }
}

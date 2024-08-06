package com.example.demo.utils;

import javax.imageio.*;

import java.awt.image.*;

import java.awt.*;

import java.io.*;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;

@Slf4j
public class PhotoDigest {

    public static void main(String[] args) throws Exception {

        File file = new File("C:\\Users\\75412\\Desktop\\0615150501.png");
        BufferedImage image = ImageIO.read(file);
        int height = image.getHeight();
        int width = image.getWidth();
        ImageUtil.createImage(width,height,"苑喜亚");


        compareImage("C:\\Users\\75412\\Desktop\\0615150501.png","D:/a.jpg");


        float percent = compare(getData("C:\\Users\\75412\\Desktop\\0615150501.png"),

                getData("D:/a.jpg"));

        if(percent==0){

            System.out.println("无法比较");

        }else{

            System.out.println("两张图片的相似度为：" + percent + "%");

        }

    }

    public static int[] getData(String name) {

        try{

            BufferedImage img = ImageIO.read(new File(name));

            BufferedImage slt = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

            slt.getGraphics().drawImage(img, 0, 0, 100, 100, null);

            int[] data = new int[256];

            for (int x = 0; x < slt.getWidth(); x++) {

                for (int y = 0; y < slt.getHeight(); y++) {

                    int rgb = slt.getRGB(x, y);

                    Color myColor = new Color(rgb);

                    int r = myColor.getRed();

                    int g = myColor.getGreen();

                    int b = myColor.getBlue();

                    data[(r + g + b) / 3]++;

                }

            }

            return data;

        }catch(Exception exception){

            System.out.println("有文件没有找到,请检查文件是否存在或路径是否正确");

            return null;

        }

    }

    public static float compare(int[] s, int[] t) {

        try{

            float result = 0F;

            for (int i = 0; i < 256; i++) {

                int abs = Math.abs(s[i] - t[i]);

                int max = Math.max(s[i], t[i]);

                result += (1 - ((float) abs / (max == 0 ? 1 : max)));

            }

            return (result / 256) * 100;

        }catch(Exception exception){

            return 0;

        }

    }


    //第二个图片对比


    public static void compareImage( String targetImageUrl, String baseImageUrl ){


        /**
         * 读取图片到数组
         */
        opencv_core.Mat targetImage = imread(targetImageUrl);
        opencv_core.Mat baseImage = imread(baseImageUrl);
        log.info("read image success");


        /**
         * 首先对比的两个图片宽度要一致，否则不能对比
         */
        if(targetImage.size().width()==baseImage.size().width()){


            /**
             * 基本算法
             * 1、判断高度是否一致，如果不一致，需要截取到高度一致
             * 2、截取算法
             *    a、因为图片有通用的顶部bar和底部bar，需要先找到底部bar。
             *    b、截取长图片的部分，然后和底部bar拼接，就完成了图片截取。
             *    c、这里设置一个默认的宽度，然后对比，找到相同部分，就是底部bar。
             */

            if(targetImage.size().height()!=baseImage.size().height()){

                if(targetImage.size().height()>baseImage.size().height()){
                    targetImage = dealLongImage(targetImage.clone(),baseImage.clone());
                } else {
                    baseImage = dealLongImage(baseImage.clone(),targetImage.clone());
                }
            }

            /**
             * 进行图片差异对比
             */
            Mat imageDiff = compareImage(targetImage,baseImage);

            double nonZeroPercent = 100 * (double) countNonZero(imageDiff) / (imageDiff.size().height() * imageDiff.size().width());

            /**
             * 展示图片，将标准图，对比图，差异图，拼接成一张大图。
             * 其中差异图会用绿色标出差异的部分。
             */
            set3ImageTo1("", targetImage, baseImage, showDiff(imageDiff, baseImage), "D:/c.jpg" );


            imageDiff.release();
            baseImage.release();
            targetImage.release();

        } else {

        }
    }


    /**
     * 2、截取算法
     *    a、因为图片有通用的顶部bar和底部bar，需要先找到底部bar。
     *    b、截取长图片的部分，然后和底部bar拼接，就完成了图片截取。
     *    c、这里设置一个默认的宽度，然后对比，找到相同部分，就是底部bar。
     * @return bar的高度
     */
    public static int interceptBarHeight( Mat longImage, Mat shortImage ){

        /**
         * 设置的默认高度。
         */
        int imageSearchMaxHeight = 400;
        Mat subImageLong = new Mat(longImage, new Rect(0, longImage.size().height() - imageSearchMaxHeight, longImage.size().width(), imageSearchMaxHeight));
        Mat subImageShort = new Mat(shortImage, new Rect(0, shortImage.size().height() - imageSearchMaxHeight, shortImage.size().width(), imageSearchMaxHeight));

        opencv_core.Mat imageDiff = compareImage(subImageLong,subImageShort);

        for (int row = imageDiff.size().height() - 1; row > -1; row--) {
            for (int col = 0; col < imageDiff.size().width(); col++) {
                BytePointer bytePointer = imageDiff.ptr(row, col);
                if (bytePointer.get(0) != 0) {
                    imageDiff.release();
                    return imageSearchMaxHeight-row;
                }
            }
        }
        return imageSearchMaxHeight;
    }

    /**
     * 这里将两张图片作为参数传入，
     * 获取到共同的底部之后。对长图进行截取，
     * 然后将顶部和底部拼接在一起就ok了。
     * @param longImage
     * @param shortImage
     * @return
     */
    public static opencv_core.Mat dealLongImage( Mat longImage, Mat shortImage ){

        int diffHeight = longImage.size().height()-shortImage.size().height();
        int barHeight = interceptBarHeight(longImage,shortImage);

        opencv_core.Mat dealedLongImage = new Mat(longImage,new Rect(0,0,longImage.size().width(),shortImage.size().height()-barHeight) );

        opencv_core.Mat imageBar = new Mat(longImage,new Rect(0,longImage.size().height()-barHeight,longImage.size().width(),barHeight) );

        opencv_core.Mat dealedLongImageNew = dealedLongImage.clone();

        /**
         * 将头部和底部bar拼接在一起。
         */
        vconcat(dealedLongImage, imageBar, dealedLongImageNew);
        imageBar.release();
        dealedLongImage.release();
        return dealedLongImageNew;
    }


    public static opencv_core.Mat compareImage( opencv_core.Mat targetImage, opencv_core.Mat baseImage ){

        opencv_core.Mat targetImageClone = targetImage.clone();
        opencv_core.Mat baseImageColne = baseImage.clone();
        opencv_core.Mat imgDiff1 = targetImage.clone();
        opencv_core.Mat imgDiff = targetImage.clone();

        /**
         * 首先将图片转成灰度图，
         */
        cvtColor(targetImage, targetImageClone, COLOR_BGR2GRAY);
        cvtColor(baseImage, baseImageColne, COLOR_BGR2GRAY);

        /**
         * 两个矩阵相减，获得差异图。
         */
        subtract(targetImageClone, baseImageColne, imgDiff1);
        subtract(baseImageColne, targetImageClone, imgDiff);

        /**
         * 按比重进行叠加。
         */
        addWeighted(imgDiff, 1, imgDiff1, 1, 0, imgDiff);

        /**
         * 图片二值化，大于24的为1，小于24的为0
         */
        threshold(imgDiff, imgDiff, 24, 255, THRESH_BINARY);
        erode(imgDiff, imgDiff, new opencv_core.Mat());
        dilate(imgDiff, imgDiff, new opencv_core.Mat());
        return imgDiff;
    }


    private static void set3ImageTo1(String logTag, Mat imageSrc, Mat imageBaseSrc, Mat imageDest, String mergePicResult ) {

        if (imageSrc.size().width() == imageDest.size().width() && imageBaseSrc.size().height() == imageDest.size().height()) {
            Mat img = imageSrc.clone();
            Mat imgBase = imageBaseSrc.clone();
            Mat imgDest = imageDest.clone();
            Mat imgLine = new Mat(imgBase.size().height(), 1, CV_8UC3, new Scalar(0, 0, 0, 255));
            Mat largeImg2 = new Mat();
            Mat largeImg3 = new Mat();
            Mat largeImg4 = new Mat();
            Mat largeImg5 = new Mat();
            /**
             * 横向拼接。
             */
            hconcat(img, imgLine, largeImg2);
            hconcat(largeImg2, imgBase, largeImg3);
            hconcat(largeImg3, imgLine, largeImg4);
            hconcat(largeImg4, imgDest, largeImg5);

            imwrite( mergePicResult, largeImg5);

            img.release();
            imgBase.release();
            imgDest.release();
            imgLine.release();
            largeImg2.release();
            largeImg3.release();
            largeImg4.release();
            largeImg5.release();
        } else {
            log.info(logTag+" pictures merge failed");
            imwrite( mergePicResult, imageDest);
        }

    }


    private static Mat showDiff(Mat imgDiff, Mat imgBase) {

        MatVector rgbFrame = new MatVector();
        Mat imgDest = imgBase.clone();
        split(imgBase, rgbFrame);
        subtract(rgbFrame.get(2), imgDiff, rgbFrame.get(2));
        subtract(rgbFrame.get(0), imgDiff, rgbFrame.get(0));
        addWeighted(rgbFrame.get(1), 1, imgDiff, 1, 0, rgbFrame.get(1));
        merge(rgbFrame, imgDest);
        return imgDest;
    }


}

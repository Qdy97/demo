package com.example.demo.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCompare {

    // 改变成二进制码
    private static String[][] getPX(BufferedImage image) {
        int[] rgb = new int[3];
        int width = image.getWidth();
        int height = image.getHeight();
        int minx = image.getMinX();
        int miny = image.getMinY();
        String[][] list = new String[width][height];
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = image.getRGB(i, j);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
            }
        }
        return list;
    }

    public static boolean compareImage(BufferedImage image1, BufferedImage image2) {
        boolean result = false;
        // 分析图片相似度 begin
        String[][] list1 = getPX(image1);
        String[][] list2 = getPX(image2);
        int xiangsi = 0;
        int busi = 0;
        int i = 0, j = 0;
        for (String[] strings : list1) {
            if ((i + 1) == list1.length) {
                continue;
            }
            for (int m = 0; m < strings.length; m++) {
                try {
                    String[] value1 = list1[i][j].toString().split(",");
                    String[] value2 = list2[i][j].toString().split(",");
                    int k = 0;
                    for (int n = 0; n < value2.length; n++) {
                        int demo1 = Integer.parseInt(value1[k]);
                        int demo2 = Integer.parseInt(value2[k]);
                        System.out.println(demo1);
                        System.out.println(demo2);

                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 1) {
                            xiangsi++;
                        } else {
                            busi++;
                        }
                    }
                } catch (RuntimeException e) {
                    continue;
                }
                j++;
            }
            i++;
        }
        list1 = getPX(image2);
        list2 = getPX(image1);
        i = 0;
        j = 0;
        for (String[] strings : list1) {
            if ((i + 1) == list1.length) {
                continue;
            }
            for (int m = 0; m < strings.length; m++) {
                try {
                    String[] value1 = list1[i][j].toString().split(",");
                    String[] value2 = list2[i][j].toString().split(",");
                    int k = 0;
                    for (int n = 0; n < value2.length; n++) {

                        int demo1 = Integer.parseInt(value1[k]);
                        int demo2 = Integer.parseInt(value2[k]);
                        System.out.println(demo1);
                        System.out.println(demo2);

                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 1) {
                            xiangsi++;
                        } else {
                            busi++;
                        }
                    }
                } catch (RuntimeException e) {
                    continue;
                }
                j++;
            }
            i++;
        }
        System.out.println(xiangsi);
        System.out.println(busi);
        if (busi == 0) {
            result = true;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

//        BufferedImage bi1 = ImageIO.read(new File("C:\\Users\\75412\\Desktop\\0615150501.png"));
//        BufferedImage bi2 = ImageIO.read(new File("C:\\Users\\75412\\Desktop\\615143315.png"));

//        System.out.println(compareImage(bi1,bi2));

//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("123");
//        Object o  = null;
//        String a = null;
//        stringBuilder.append(o.toString());
//        System.out.println(stringBuilder.toString());
//
//        System.out.println("123"+null);
        String a = "1";
        System.out.println(a.compareTo("2"));

    }


}

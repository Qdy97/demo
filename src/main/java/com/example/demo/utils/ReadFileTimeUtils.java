package com.example.demo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ReadFileTimeUtils {

//    public static String file = "C:\\Users\\75412\\Desktop\\WIN_20220520_10_02_35_Pro.jpg";
//    public static String file = "C:\\Users\\75412\\Desktop\\WIN_20220520_10_02_35_Pro.jpeg";
    public static String file = "C:\\Users\\75412\\Desktop\\WIN_20220520_10_02_35_Pro.png";
//    public static String file = "C:\\Users\\75412\\Desktop\\新建 DOCX 文档.docx";
//    public static String file = "C:\\Users\\75412\\Desktop\\1.pdf";
//    public static String file = "C:\\Users\\75412\\Desktop\\123.xlsx";
//    public static String file = "C:\\Users\\75412\\Desktop\\电脑.txt";

//    public static void main(String[] args) throws IOException {
//
//        File f = new File(file);
//        getCreationTime(f);
//    }

    public static String getCreationTime(File file) {
        if (file == null) {
            return null;
        }

        BasicFileAttributes attr = null;
        try {
            Path path =  file.toPath();
            attr = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建时间
        Instant instant = attr.creationTime().toInstant();
        // 更新时间
        Instant instant2 = attr.lastModifiedTime().toInstant();
        // 上次访问时间
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()).format(instant);
        System.out.println(format);
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()).format(instant2));
        return format;
    }

}

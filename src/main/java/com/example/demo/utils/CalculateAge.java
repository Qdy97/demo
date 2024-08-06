package com.example.demo.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CalculateAge {

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("请输入出生日期（格式：yyyy-MM-dd）：");
//        String birthDateString = scanner.nextLine();
//        LocalDate birthDate = LocalDate.parse(birthDateString);
//        LocalDate currentDate = LocalDate.now();
//        Period period = Period.between(birthDate, currentDate);
//        int age = period.getYears();
//        if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
//                (currentDate.getMonthValue() == birthDate.getMonthValue() &&
//                        currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
//            age--;
//        }
//        System.out.println("您的年龄是：" + age + "岁。");
//    }

    public static void main(String[] args) {
//        int L = 0;
//        int R = 100;
//        print(R);
//        System.out.println(L >> 1);
//        System.out.println(R >> 1);
//        print(R >> 1);
//        int mid = L + ((R - L) >> 1);
//        System.out.println(mid);
//        List<String> list1 = new ArrayList<>();
//        List<String> list2 = new LinkedList<>();
        Pattern pattern =
                Pattern.compile("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
        boolean matches = pattern.matcher("410103202402230014").matches();
        System.out.println(matches);
    }

    /**
     * 按32位二进制打印数字
     * @param num
     */
    public static void print(int num){
        for (int i = 31; i >=0 ; i--) {
            System.out.print((num & (1 << i))  == 0?"0":1);
        }
        System.out.println();
    }

}

package com.example.demo.utils;

import java.util.Arrays;

/**
 * @author zhy
 * @desc 有序数组中找到<= num最右的位置
        * @ since 2 0 2 3 / 5 / 1 5 1 7 : 1 0
 */
public class Code03_Dichotomy {

    public static int test(int[] arr, int num) {
        if (arr[arr.length - 1]< num){
            return arr.length - 1;
        }
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i] == num) {
                return i;
            }
        }

        return -1;
    }

    public static int mostRight(int[] arr, int num) {
        if (null == arr || arr.length < 1) {
            return -1;
        }
        int L = arr.length - 1;
        int R = 0;
        int ans = -1;
        while (L >= R) {

            int mid = L + (L - R) >> 1;
            if (arr[mid] <= num) {
                ans = mid;
                R = mid + 1;
            } else {
                L = mid - 1;
            }
        }

        return ans;
    }

    /*public static int lessleft(int[] arr, int num) {
        if (null == arr || arr.length < 1) {
            return -1;
        }
        int L = 0;
        int R = arr.length - 1;
        int ans = -1;
        while (L <= R) {

            int mid = (L + R) / 2;
            if (arr[mid] >= num) {
                ans = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }

        return ans;
    }*/

    public static int[] createArr(int v) {
        int[] arr = new int[v];
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) {
                num = arr[i - 1];
            }
            arr[i] = num + (int) (Math.random() * 2);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 50;
        boolean flag = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = createArr(10);
            int test = test(arr, 2);
            System.out.println(Arrays.toString(arr));

            int mostRight = mostRight(arr, 2);

            if (test != mostRight){
                flag = false;
                break;
            }
        }
        System.out.println(flag?"nice":"fuck");

    }
}

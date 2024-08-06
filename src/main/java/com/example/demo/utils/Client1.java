package com.example.demo.utils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhy
 * @since 2022/9/2 16:08
 */
public class Client1 {
    public static void main(String[] args) {
        int n =10000;
        double f = 1.0/n;

        Map<Integer, Double> collect = IntStream.range(0, n)
                .parallel()
                .mapToObj(p -> twoDiceThrows())
                .collect(Collectors.groupingBy(p -> p,
                        Collectors.summingDouble(p -> f)
                ));
        System.out.println(collect);

    }
    private static Integer twoDiceThrows() {
        Random random = new Random();
        int firstThrow = 1 + random.nextInt(6);
        int secondThrow = 1 + random.nextInt(6);
        return firstThrow + secondThrow;
    }
}

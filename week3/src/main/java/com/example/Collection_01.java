package com.example;

import java.util.*;
import java.util.stream.IntStream;

public class Collection_01 {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();

        // 숫자 100~109로 리스트 채우기
        IntStream.range(100, 110).forEach((int i) -> numbers.add(i));

        // 1. for문으로 출력
        System.out.print("for문으로 출력: ");
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(numbers.get(i) + " ");
        }
        System.out.println();

        // 2. enhanced for 문
        System.out.print("enhanced for 문으로 출력: ");
        for (var n: numbers) {
            System.out.print(n + " ");
        }
        System.out.println();

        // 3. Stream API - 짝수만 출력
        System.out.print("Stream API - 짝수만 출력: ");
        numbers.stream() // numbers의 요소들을 스트림 형태로 해서...
            .filter((Integer i) -> i % 2 == 0) // 스트림에서 짝수만 필터링해서 고르기 (2로 나눈 나머지가 0)
            .forEach((Integer i) -> System.out.print(i + " ")); // 스트림에서 각 요소들에 대해(forEach) 출력
    }
}

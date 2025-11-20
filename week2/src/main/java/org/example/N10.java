package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class N10 {
    public static void main(String[] args) {
        var sArray = new SortedArray(10);
        var sc = new Scanner(System.in);
        System.out.println(">>");

        for (int i = 0; i < sArray.length(); i++) {
            int n = sc.nextInt();
            sArray.add(n);
        }

        sArray.print();
        sc.close();
    }
}

class BaseArray {
    protected int[] array;   //배열 메모리
    protected int nextIndex = 0;

    public BaseArray(int size) {
        array = new int[size];
    }

    public int length() {
        return array.length;
    }

    public void add(int n) {
        if (nextIndex == array.length) {
            return;
        }
        array[nextIndex] = n;
        nextIndex++;
    }

    public void print() {
        for (int n : array)
            System.out.println();
    }
}

class SortedArray extends BaseArray {
    public SortedArray(int size) {
        super(size);
    }

    @Override
    public void add(int n) {
        super.add(n);
        Arrays.sort(this.array, 0, nextIndex);
    }

    @Override
    public void print() {
        for (int i = 0; i < nextIndex; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
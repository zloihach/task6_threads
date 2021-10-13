package com.vyatsu.ooplab.lab6;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class Threads {

    private static final int size = 10_000_000; //размер длинного массива
    private static final int n = 4; // число потоков
    private static final int h = size / n; //размер разделенных массивов
    private static float[] arr = new float[size]; //одномерный длинный массив

    public static void main(String[] args) {
        Threads th = new Threads();
        th.firstArr();
        th.secondArr();
    }

    private void secondArr() {
        Thread[] threads = new Thread[n];
        Arrays.fill(arr, 1.0f);
        long a = System.currentTimeMillis();
        float[][] newArr = new float[n][h];
        for (int i = 0; i < n; i++) {
            arraycopy(arr, i * h, newArr[i], 0, h);
        }
        long split = System.currentTimeMillis();
        System.out.println("Время разделения массива " + (split - a));
        for (int i = 0; i < n; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> calcSecondArr(newArr, finalI));
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long concat = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            arraycopy(newArr[i], 0, arr, i * h, h);
        }
        long end = System.currentTimeMillis(); //Проверяю время окончания метода
        System.out.println("Время склейки массива " + (end - concat)); //В консоль выводится время склейки массива
        System.out.println("Время выполнения второго метода " + (end - a)); //В консоль выводится время работы
    }
    private void calcSecondArr(float[][] arr, int n) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < h; i++) {
            arr[n][i] = (float) (arr[n][i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения " + (n + 1) + "-го потока " + (end - start)); //В консоль выводится время выполнения потока
    }
    private void firstArr() {
        Arrays.fill(arr, 1.0f);
        long a = System.currentTimeMillis(); //Засекаю время выполнения
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Первый " + arr[arr.length - 1]);
        long end = System.currentTimeMillis(); //Проверяю время окончания метода
        System.out.println("Время выполнения первого метода " + (end - a)); //В консоль выводится время работы
    }
}
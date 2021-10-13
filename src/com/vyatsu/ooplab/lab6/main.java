package com.vyatsu.ooplab.lab6;

import javax.imageio.ImageTranscoder;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class main {
    public static void main(String[] args) {
        final int size = 100_000_000;
        final int half = size / 2;

        float[] arr = new float[size];
        float[] arr2 = new float[size];

        Arrays.fill(arr, 1.0f);
        Arrays.fill(arr2, 1.0f);

        long a = System.currentTimeMillis();

        method1(arr);
        System.out.println(System.currentTimeMillis() - a);
        a = System.currentTimeMillis();
        float[] ar1 = new float[half];
        float[] ar2 = new float[half];
        System.arraycopy(arr2, 0, ar1, 0, half);
        System.arraycopy(arr2, ar1.length, ar2, 0, half);
        System.out.println(System.currentTimeMillis()-a);

        method2(ar1, ar2);
        System.arraycopy(ar1, 0, arr2, 0, half);
        System.arraycopy(ar2, 0, arr2, half, half);
        System.out.print("первый в первом " + arr[0] + "[1]" + " == ");
        System.out.println("первый во втором " + arr2[0] + "[2]");
        System.out.print("Среднее в первом " + arr[arr.length / 2] + "[1]" + " == ");
        System.out.println("Среднее во втором " + arr2[arr2.length / 2] + "[2]");
        System.out.print("последний в первом " + arr[arr.length - 1] + "[1]" + " == ");
        System.out.println("последний во втором " + arr2[arr2.length - 1] + "[2]");
        System.out.println(System.currentTimeMillis() - a);
        System.out.println(compair(arr, arr2));
    }

    static void method1(float[] n) {
        Thread t1 = new Thread(() -> {
            function(n, 0, n.length);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void method2(float[] n1, float[] n2) {
        Thread t1 = new Thread(() -> {
            function(n1, 0, n1.length);
        });
        Thread t2 = new Thread(() -> {
            function2(n2, 0, n2.length);
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static String compair(float[] n, float[] m) {
        int count = 0;
        for (var i = 0; i < n.length; i++) {
            if (n[i] == m[i]) count++;
        }
        return "Массив совпадает на " + (count / n.length) * 100 + "%";
    }

    static void function(float[] n, int one, int two) {
        for (var i = one; i < two; i++) {
            n[i] = (float) (n[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    static void function2(float[] n, int one, int two) {

        int half = n.length;
        for (var i = one; i < two; i++) {
            n[i] = (float) (n[i] * Math.sin(0.2f + (half + i) / 5) * Math.cos(0.2f + (half + i) / 5) * Math.cos(0.4f + (half + i) / 2));
        }
    }
}


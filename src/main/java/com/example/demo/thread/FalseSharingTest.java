package com.example.demo.thread;

import io.swagger.models.properties.StringProperty;
import sun.misc.Contended;

/**
 * @author Jay
 * @date 2019/6/13 0:07
 * @description 缓存伪共享测试
 */
public class FalseSharingTest {
    public static void main(String[] args) throws InterruptedException {
        testPointer(new Pointer());
    }

    private static void testPointer(Pointer pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.x++;
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000000; i++) {
                    pointer.y++;
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(pointer);

    }
}

/**
 * 加注解或者加56字节以上数字均有效，消除伪共享问题
 */
@Contended
class Pointer {
    volatile long x;
     long p1, p2, p3, p4, p5, p6, p7;
    volatile long y;

}

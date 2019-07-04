package com.example.demo.jdk.jdk8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author Jay
 * @description 在必要的情况下，将一个大任务进行必要的拆分Fork成若干个小任务，再将小任务的运算结果进行Join汇总
 * <p>
 * 采用“工作窃取”模式（Working-stealing），即当执行新的任务时它可以将其拆分分成更小的任务执行，
 * 并将小任务加到线程队列中，然后再从一个随机线程的队列中偷一个并把它放在自己的队列中。
 * <p>
 * 相对于一般的线程池实现，fork/join框架的优势体现在对其中包含的任务的处理方式上，
 * 如果一个线程正在执行的任务由于某些原因无法继续运行，那么该线程会处于等待状态，而在fork/join框架实现中，
 * 如果某个子问题由于等待另外一个子问题的完成而无法继续运行，那么处理该子问题的线程会主动寻找其他尚未运行的子问题来执行，
 * 这种方式减少了线程等待的时间，提高了性能。
 * <p>
 * 并行流就是把一个内容分成多个数据块，并用不同的线程分别处理每个数据块的流。
 * @date Created in 2019/7/3 9:45
 */
public class ForkJoinTest {
    /**
     * 1.自己实现forkjoin
     * 443 ms
     */
    @Test
    public void test1() {
        // 绝对时间
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 100000000L);

        Long sum = pool.invoke(task);
        Instant end = Instant.now();
        // 持续时间
        System.out.println(Duration.between(start, end).toMillis());
    }

    /**
     * 2.直接使用for循环
     * 772 ms
     */
    @Test
    public void test2() {
        Instant start = Instant.now();

        Long sum = 0L;
        for (int i = 0; i <= 100000000L; i++) {
            sum += i;
        }

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());

    }

    /**
     * 3.JDK8的并行流实现。
     * 443
     */
    @Test
    public void test3() {
        Instant start = Instant.now();

        long sum = LongStream.rangeClosed(0, 1000000000L)
                // 并行流
                .parallel()
                //.sequential()//串行流
                .reduce(0, Long::sum);

        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
    }

}

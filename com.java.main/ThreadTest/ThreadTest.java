package ThreadTest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1));
        for (int j = 0; j < 3; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < 50; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("当前线程名称：" + Thread.currentThread().getName() + " 数值：" + i);
                }
            });
        }
    }

}
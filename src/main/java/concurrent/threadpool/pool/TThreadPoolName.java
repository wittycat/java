package concurrent.threadpool.pool;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by chenxun on 2018/1/2.
 */
public class TThreadPoolName {
    @Test
    public void testPoolName() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new TThreadFactory("testPool"));
        for (int i = 0; i < 5; i++) {
            final int t = i;
            threadPoolExecutor.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(t * 50);
                    System.out.println("threadName=" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                }
            });
        }
        while (threadPoolExecutor.getActiveCount() > 0) {
        }
        TimeUnit.SECONDS.toSeconds(100);
        threadPoolExecutor.shutdown();
        System.out.println("done");
    }
}
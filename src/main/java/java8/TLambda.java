package java8;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by chenxun on 2017/9/7 23:33
 */
public class TLambda {

    @Test
    public void test1() throws InterruptedException {
        new Thread(() -> {
            System.out.println("thread...");
        }).start();
        System.out.println("mian...");
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test2() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("thread...");
        };
        new Thread(runnable).start();
        System.out.println("mian...");
        TimeUnit.SECONDS.sleep(1);
    }
}

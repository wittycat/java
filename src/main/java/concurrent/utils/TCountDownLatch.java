package concurrent.utils;

import java.util.concurrent.CountDownLatch;

/** 
 *
 * @author chenxun
 * @date 2017年1月4日 下午5:55:17 
 * 1、基于AQS共享锁的实现，当state为0时唤醒等待的一个或一组线程
 */
public class TCountDownLatch {
   public static void main(String[] args) {
	   final CountDownLatch countDownLatch = new CountDownLatch(1);
	   
	   new Thread(new Runnable() {
		@Override
		public void run() {
			System.out.format("指令线程 %s%n", Thread.currentThread().getName());
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			countDownLatch.countDown();
		}
	   }).start();
	   
	   for (int i = 0; i < 10; i++) {
		   new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.format("%s等待中%n", Thread.currentThread().getName());
					countDownLatch.await();
					System.out.format("%s结束等待%n", Thread.currentThread().getName());
				} catch (InterruptedException e) {
				}
			}
		}).start();
	   }
   }
}

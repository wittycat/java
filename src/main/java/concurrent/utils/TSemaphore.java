package concurrent.utils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import collection.ComporeTime;

/** 
 * @author:chenxun
 * @2016年12月21日 下午11:51:22 
 * @Theme:
 * @Reference:
 * @Descript:
 * 1.Semaphore可以应用于流量控制，比如对数据库的连接数控制
 * 2.基于AQS共享锁的实现
 */
public class TSemaphore {
	public static void main(String[] args) {
		//数据库允许最大的线程数
		int PERMITS = 10;
		//外界涌入的线程数
		int THREAD_MAX = 100;
		//数据库连接执行时间
		final Random random = new Random(100);
		final Semaphore semaphore = new Semaphore(PERMITS, true);
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(THREAD_MAX);
		
		for (int i = 0; i < THREAD_MAX; i++) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						Long start = ComporeTime.start();
						semaphore.acquire();
						
						String info = String.format("%s线程获取许%s",
								Thread.currentThread().getName(),
								ComporeTime.getMinus(start));
						
						long nextLong = random.nextInt(2000);
						Thread.sleep(nextLong);
						
						semaphore.release();
						
						System.out.format("%s,执行时间%d,结束  %n",info,nextLong);
					} catch (InterruptedException e) {
					}
				}
			};
			newFixedThreadPool.submit(runnable);
		};
		newFixedThreadPool.shutdown();
	}
}

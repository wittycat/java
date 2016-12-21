package concurrent;

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
 * @Descript:Semaphore可以应用于流量控制，比如对数据库的连接数控制
 */
public class TSemaphore {
	public static void main(String[] args) {
		//数据库允许最大的线程数
		int PERMITS = 10;
		//外界涌入的线程数
		int THREAD_MAX = 100;
		//数据库连接执行时间
		Random random = new Random(100);
		Semaphore semaphore = new Semaphore(PERMITS, true);
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

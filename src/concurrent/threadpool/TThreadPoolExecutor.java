package concurrent.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author：chenxun 
 * @创建时间：2016年6月2日 下午11:54:17 
 * @参考： 
 * @说明：
 * 1.ThreadPoolExecutor调节线程的原则是：先调整到最小线程，最小线程用完后，
 * 他会将优先将任务放入缓存队列(offer(task)),等缓冲队列用完了，才会向最大线程数调节
 * 2.也就是如果使用无界队列  ，就不可能使用最大线程数；
 *   无界队列：大小线程数一致
 *   有界队列：大小线程数可以不一致
 */
public class TThreadPoolExecutor {

	public static void main(String[] args) {
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		int maximumPoolSize = corePoolSize + 1;
		long keepAliveTime = 0;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(5);
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
		for (int i = 0; i < 10; i++) {
			Runnable task = new Runnable() {
				@SuppressWarnings("static-access")
				public void run() {
					try {
						Thread.currentThread().sleep(5000);
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			threadPoolExecutor.submit(task);
		}
		threadPoolExecutor.shutdown();
	}

}

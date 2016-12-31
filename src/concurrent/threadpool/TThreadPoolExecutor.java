package concurrent.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author：chenxun 创建时间：2016年6月2日 下午11:54:17 参考： 说明：
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

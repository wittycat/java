package concurrent.threadpool.pool;

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
 * 1.ThreadPoolExecutor调节线程的原则是：先调整到最小线程，最小线程用完后，他会将优先将任务
 * 放入缓存队列(offer(task)),等缓冲队列用完了，才会向最大线程数调节。
 * 原则：如果使用无界队列  ，就不可能使用最大线程数；
 *   	无界队列：大小线程数一致
 *   	有界队列：大小线程数可以不一致（当有界队列特别大时，也没有必要把core和max设置的不一样，
 *      因为可能很难达到有界的最值，也就更难达到max的值了）
 * 2.继承关系:
 *   Executor->ExecutorService->AbstractExecutorService->ThreadPoolExecutor
 * 3.理解线程复用？（难点）
 */
public class TThreadPoolExecutor {
	
	static class Monitor  extends Thread{
		
        private ThreadPoolExecutor pool;

		public Monitor(ThreadPoolExecutor pool) {
			this.pool = pool;
		}

		@Override
		public void run() {
			System.out.println("Monitor:"+Thread.currentThread().hashCode());
			int count = 0;
			while(true){
				try {
					TimeUnit.MILLISECONDS.sleep(500);
					int activeCount = pool.getActiveCount();
					if(count!=activeCount)
//					    System.out.format("当前活动线程数:%d,queueSize:%d%n",activeCount,pool.getQueue().size());
					count = activeCount;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public ThreadPoolExecutor getPool() {
			return pool;
		}

		public void setPool(ThreadPoolExecutor pool) {
			this.pool = pool;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		int maximumPoolSize = corePoolSize + 1;
		
		long keepAliveTime = 1000;
		
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(5);
		
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		
		RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
		
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				corePoolSize, 
				maximumPoolSize, 
				keepAliveTime, 
				TimeUnit.MILLISECONDS, 
				workQueue,
				threadFactory, 
				handler);
		
		new Monitor(threadPoolExecutor).start();
		 
		for (int i = 0; i < 10; i++) {
			final Integer integer = new Integer(i);
			TimeUnit.SECONDS.sleep(1);
			Runnable task = new Runnable() {
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(10+integer.intValue());
						int hashCode = Thread.currentThread().hashCode();
						System.out.println(hashCode);
					} catch (InterruptedException e) {
					}
				}
			};
			threadPoolExecutor.submit(task);
		}
		
		TimeUnit.SECONDS.sleep(1);
		while(threadPoolExecutor.getActiveCount()>0){
			Thread.yield();
		}
		
		System.out.println("================================");
		/**
		 * 测试 是否线程复用，结果：的确线程复用
		 */
		Runnable task = new Runnable() {
			public void run() {
				System.out.println("afterAdd:"+Thread.currentThread().hashCode());
			}
		};
		threadPoolExecutor.submit(task);
		TimeUnit.SECONDS.sleep(1);
		
		
		
		System.out.println("main:"+Thread.currentThread().hashCode());
		/**
		 * 1.拷贝线程组的信息到指定线程组
		 * 2.具体线程
		 *  main:1028566121   主线程
		 *	Thread-0:528123358  监控线程
		 *  以下为为线程池中的线程  （线程池本身不属于一个线程,它就属于一个类，它的包含的线程就是启动的线程数）
		 *	pool-1-thread-1:1617048292 
		 *	pool-1-thread-3:245887817
		 *	pool-1-thread-4:1035057739
		 *	pool-1-thread-5:1468479040
		 */
		System.out.println("==============="+Thread.activeCount()+"=================");
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		for (Thread thread : threads) {
			System.out.println(thread.getName()+":"+thread.hashCode());
		}
		
		threadPoolExecutor.shutdown();
		
		/**
		 * 小于7是由于线程池关闭 线程可能已经小于6
		 */
		while (Thread.activeCount()<7) {
			Runtime.getRuntime().exit(0);
		}
		
	}

}

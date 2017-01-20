package concurrent.threadpool.pool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** 
 * @Author:chenxun
 * @2017年1月18日 上午12:06:37 
 * @Theme:
 * @Reference:http://www.blogjava.net/xylz/archive/2011/01/10/342738.html
 * @Descript:
 * 1.ScheduledExecutorService的实现类ScheduledThreadPoolExecutor是继承线程池类
 *   ThreadPoolExecutor的，因此它拥有线程池的全部特性。但是同时又是一种特殊的线程池，这个
 *   线程池的线程数大小最大Integer最大值，任务队列是基于DelayQueue的无限任务队列。
 *   主要提供定时执行
 * 2.scheduleAtFixedRate【不关注上次线程执行完成】和scheduleWithFixedDelay【关注上次线程执行完成】
 */
public class TScheduledThreadPoolExecutor {
	static class TaskItem  implements Runnable{
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		@Override
		public void run() {
			try {
				System.out.println("业务执行:"+dateFormat.format(new Date()));
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException ignore) {
			}
		}
	}
	
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(10);
		TaskItem item = new TaskItem();
		/**
		 * 在给定延时后执行一次，和> 
		 *  schedule(Callable<V> callable, long delay, TimeUnit unit)一样
		 */
//		scheduled.schedule(item ,5, TimeUnit.SECONDS);
		/**
		 * 在给定延时后，每隔固定时间执行一次，不管上次启动的线程是否执行完了【不关注上次线程执行完成】
		 */
//		scheduled.scheduleAtFixedRate(item,3, 2, TimeUnit.SECONDS);
		/**
		 * 在给定延时后，线程执行完成后，和下一次线程执行之间间隔为固定时间:【关注上次线程执行完成】
		 */
		scheduled.scheduleWithFixedDelay(item,3, 2, TimeUnit.SECONDS);
//		scheduled.shutdown();
	}
}

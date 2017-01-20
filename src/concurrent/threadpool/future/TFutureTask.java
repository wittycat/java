package concurrent.threadpool.future;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/** 
 * @Author:chenxun
 * @2017年1月18日 上午12:35:24 
 * @Theme:
 * @Reference:
 * @Descript:
 * 1.仅在计算完成时才能获取结果；如果计算尚未完成，则阻塞 get 方法。
 * 2.单个任务获取结果的场景
 */
public class TFutureTask {
	
	static ExecutorService pool = Executors.newFixedThreadPool(5);
	
	static class RunnableTaskItem  implements Runnable{
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("业务执行:" + dateFormat.format(new Date()));
			} catch (Exception e) {
			}
		}
	}
	
	static class CallableTaskItem  implements Callable<String>{
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		@Override
		public String call() {
			try {
				TimeUnit.SECONDS.sleep(5);
				return "业务执行:" + dateFormat.format(new Date());
			} catch (Exception ignore) {
			}
			return null;
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/**
		 * 测试一
		 * 1.成功完成时 get 返回给定的结果 。
		 * 2.如果不需要特定的结果，则考虑使用下列形式的构造：
		 * Future<?> f = new FutureTask<Object>(runnable, null) 
		 */
//		String result = "success";
//		FutureTask<String> futureTask = new FutureTask<String>(new RunnableTaskItem(), result);
//		pool.submit(futureTask);
//		System.out.println(futureTask.get());
		/**
		 * 测试二
		 */
		FutureTask<String> futureTask = new FutureTask<String>(new CallableTaskItem());
		pool.submit(futureTask);
		System.out.println(futureTask.get());
		
		pool.shutdown();
	}
}

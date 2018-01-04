package concurrent.threadpool.future;

import org.junit.Test;

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

	class RunnableTaskItem implements Runnable {
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

	class CallableTaskItem implements Callable<String> {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		@Override
		public String call() {
			try {
				TimeUnit.SECONDS.sleep(5);
				return "业务执行:" + dateFormat.format(new Date());
				/**
				 * 当程序由于异常 返回null 时 没有什么影响  只是get() 也会返回null  不会影响正常流程
				 */
				//return null;
			} catch (Exception ignore) {
			}
			return null;
		}
	}

	/**
	 * 1.成功完成时 get 返回给定的结果 。
	 * 2.如果不需要特定的结果，则考虑使用下列形式的构造：
	 * Future<?> f = new FutureTask<Object>(runnable, null)
	 */
	@Test
	public void runable() throws ExecutionException, InterruptedException {
		String result = "success";
		FutureTask<String> futureTask = new FutureTask<>(new RunnableTaskItem(), result);
		pool.submit(futureTask);
		System.out.println(futureTask.get());
	}

	@Test
	public void callable() throws ExecutionException, InterruptedException {
		FutureTask<String> futureTask = new FutureTask<>(new CallableTaskItem());
		pool.submit(futureTask);
		System.out.println(futureTask.get());
		pool.shutdown();
	}
}

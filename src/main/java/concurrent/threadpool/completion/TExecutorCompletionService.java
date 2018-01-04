package concurrent.threadpool.completion;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/** 
 *
 * @Author chenxun
 * @Date 2017年1月20日 上午11:41:34 
 * 1.CompletionService实现了生产者提交任务和消费者获取结果的解耦，消费者一定是按照任务完成的先后顺序来获取执行结果,注意不是提交顺序。（自定义的实现为按照提交顺序）
 * 2.基本实现：Executor+阻塞队列实现
 * 3.一组任务获取结果的场景
 */
public class TExecutorCompletionService {

	class RunableTaskItem implements Runnable {
		private String j;
		public RunableTaskItem(String j) {
			this.j = j;
		}
		@Override
		public void run() {
			try {
				int i = new Random().nextInt(20);
				TimeUnit.SECONDS.sleep(i);
				System.out.println(j + "->(" + i + ")" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
			} catch (InterruptedException e) {
			}
		}
	}

	/**
     * 
     * @throws InterruptedException
     * @throws ExecutionException
     */
	private void runableExecutorCompletionService() throws InterruptedException, ExecutionException {
		int i = 10;
		ExecutorService pool = Executors.newFixedThreadPool(i);
    	ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(pool);
		for (int j = 0; j < 10; j++) {
			//completionService.submit(new CallableTaskItem(j));
			completionService.submit(new RunableTaskItem(String.valueOf(j)), String.valueOf(j)); //返回指定的结果
		}
		for (int j = 0; j < 10; j++) {
			System.out.println(completionService.take().get());
		}
    	pool.shutdown();
	}

	@Test
	public void runable() throws ExecutionException, InterruptedException {
		runableExecutorCompletionService();
	}

	/*******************************************
	 * Callable
	 ************************************/
	class CallableTaskItem implements Callable<String> {
		private int j;

		public CallableTaskItem(int j) {
			this.j = j;
		}

		@Override
		public String call() throws Exception {
			int i = new Random().nextInt(20);
			TimeUnit.SECONDS.sleep(i);
			return j + "->(" + i + ")" + new SimpleDateFormat("hh:mm:ss").format(new Date());
			/**
			 * 当程序由于异常 返回null 时 没有什么影响  只是 take().get() 也会返回null  不会影响正常流程
			 */
			//return null;
		}
	}

	/**
	 * 自定义ExecutorCompletionService类似的实现 ，并且实现的是消费者按照提交顺序获取
	 *
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private void callableCompletionService() throws InterruptedException, ExecutionException {
		int i = 10;
		ExecutorService pool = Executors.newFixedThreadPool(i);
		ArrayBlockingQueue<Future<String>> queue = new ArrayBlockingQueue<Future<String>>(i);
		for (int j = 0; j < 10; j++) {
			Future<String> future = pool.submit(new CallableTaskItem(j));
			queue.add(future);
		}
		//保证队列的确已经进入元素
		TimeUnit.SECONDS.sleep(2);
		while (true) {
			System.out.println(queue.take().get());
			if (queue.size() == 0) {
				pool.shutdown();
				break;
			}
		}
	}

	@Test
	public void callable() throws ExecutionException, InterruptedException {
		callableCompletionService();
	}
}

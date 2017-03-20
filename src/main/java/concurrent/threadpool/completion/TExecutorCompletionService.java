package concurrent.threadpool.completion;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * 1.CompletionService实现了生产者提交任务和消费者获取结果的解耦，生产者和消费者都不用关心任务的完成顺序，
 *   由 ExecutorCompletionService来保证，消费者一定是按照任务完成的先后顺序来获取执行结果。
 * 2.基本实现：Executor+阻塞队列实现
 * 3.一组任务获取结果的场景
 */
public class TExecutorCompletionService {
	
    static class CallableTaskItem implements Callable<String>{
    	private int j;
		public CallableTaskItem(int j) {
			this.j = j;
		}

		@Override
		public String call() throws Exception {
			TimeUnit.SECONDS.sleep(j);
			return j+"    ->"+new SimpleDateFormat("hh:mm:ss").format(new Date());
		}
    }
    
    static class RunableTaskItem implements Runnable{
    	
    	private String j;
		public RunableTaskItem(String j) {
			this.j = j;
		}
		@Override
		public void run() {
			System.out.println(j+"->"+new SimpleDateFormat("hh:mm:ss").format(new Date()));
		}
    	
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
//    	tExecutorCompletionService();
    	cCompletionService();
    	
	}
    /**
     * 自定义ExecutorCompletionService类似的实现
     * @throws InterruptedException
     * @throws ExecutionException
     */
	private static void cCompletionService() throws InterruptedException,ExecutionException {
		int i = 5;
    	ExecutorService pool = Executors.newFixedThreadPool(i);
    	ArrayBlockingQueue<Future<String>> queue = new ArrayBlockingQueue<Future<String>>(i);
    	for (int j = 0; j < 5; j++) {
    		Future<String> future = pool.submit(new CallableTaskItem(j));
    		queue.add(future);
    	}
    	
    	//保证队列的确已经进入元素
    	TimeUnit.SECONDS.sleep(2);
    	
    	while (true) {
    		System.out.println(queue.take().get());
    		if(queue.size()==0){
    			pool.shutdown();
    			break;
    		}
		}
    	
	}
    
    /**
     * 
     * @throws InterruptedException
     * @throws ExecutionException
     */
	private static void tExecutorCompletionService() throws InterruptedException, ExecutionException {
		int i = 5;
    	ExecutorService pool = Executors.newFixedThreadPool(i);
    	ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(pool);
    	for (int j = 0; j < 5; j++) {
//    		completionService.submit(new CallableTaskItem(j));
    		//返回指定的结果
    		completionService.submit(new RunableTaskItem(String.valueOf(j)), String.valueOf(j));
		}
    	System.out.println("before:"+new SimpleDateFormat("hh:mm:ss").format(new Date()));
    	
    	
    	for (int j = 0; j < 5; j++) {
    		System.out.println(completionService.take().get());
		}
    	
    	pool.shutdown();
	}
}

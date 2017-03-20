package concurrent.threadpool.rejectedpolicy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** 
 *
 * @Author chenxun
 * @Date 2017年1月20日 下午3:46:05 
 *
 */
public class TRejectedExecutionHandler {
	
   static class RunableTaskItem implements Runnable{
    	
	    private int j;
		public RunableTaskItem(int j) {
			this.j = j; 
		}
		@Override
		public void run() {
			try {
				System.out.println("          消费--"+j+"-->"+new SimpleDateFormat("hh:mm:ss").format(new Date()));
				TimeUnit.SECONDS.sleep(10);
			} catch (Exception e) {
			}
		}
    	
    }
	  
   public static void main(String[] args) throws InterruptedException {
	   
	   int available = Runtime.getRuntime().availableProcessors();
	   
	   LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<Runnable>(2);
	   
	   /**
	    * 1.当提交的不能立即执行且阻塞队列无法容纳时，
	    *  抛出异常，后续生产线程不能再正常提交
	    */
//	   RejectedExecutionHandler policy = new ThreadPoolExecutor.AbortPolicy();
	   /**
	    * 1.当提交的不能立即执行且阻塞队列无法容纳时，
	    *  则立即执行,后续正常提交
	    */
//	   RejectedExecutionHandler policy = new ThreadPoolExecutor.CallerRunsPolicy();
	   /**
	    * 1.当提交的不能立即执行且阻塞队列无法容纳时，
	    * 移除调队列中最早的任务,后续正常提交
	    */
//	   RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardOldestPolicy();
	   /**
	    * 1.当提交的不能立即执行且阻塞队列无法容纳时，
	    * 丢弃提交的任务,后续正常提交
	    */
	   RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
	   
	   ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			   available, available, 
			   0, TimeUnit.SECONDS,
			   linkedBlockingDeque, policy);
	   for (int j = 0; j < 2; j++) {
		   for (int i = 0; i < 7; i++) {
			   TimeUnit.SECONDS.sleep(1);
			   System.out.println("生产--"+i+"-->"+new SimpleDateFormat("hh:mm:ss").format(new Date()));
			   threadPoolExecutor.submit(new RunableTaskItem(i));
		   }
		   System.out.println("-------------------华丽分割线------------------------------");
		   TimeUnit.SECONDS.sleep(5);
	   }
	   
	   threadPoolExecutor.shutdown();
	   
   }
}

package concurrent.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** 
 * author：chenxun
 * create：2016年7月6日 下午4:45:20 
 * description：
 */
public class TCountDownLatchRunnable {
	  public static  void main(String[] args) {
		    CountDownLatch doneSignal = new CountDownLatch(2);
			ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);

			for (int i = 0; i < 2; ++i)
				newFixedThreadPool.execute(new WorkerRunnable(doneSignal, i));// create and start threads

			try {
				doneSignal.await(100,TimeUnit.MINUTES);// wait for all to finish
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} 
			System.out.println("执行完成");
			newFixedThreadPool.shutdown();
	   }

	   static class WorkerRunnable implements Runnable {
		   private final CountDownLatch doneSignal;
		   private final int i;

		   WorkerRunnable(CountDownLatch doneSignal, int i) {
			   this.doneSignal = doneSignal;
			   this.i = i;
		   }

		   public void run() {
			   System.out.println("执行中"+i);
			   try {
				   Thread.sleep(2000*i);
			   } catch (InterruptedException e) {
				   e.printStackTrace();
			   }finally{
				   doneSignal.countDown();
			   }
		   }
	   }
}

package concurrent.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * author：chenxun 
 * create：2016年7月6日 下午4:45:20 
 * description：
 */
public class TCountDownLatchCallback {
	
	public static void main(String[] args) throws InterruptedException,ExecutionException {
		CountDownLatch doneSignal = new CountDownLatch(2);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<ReBack> future1 = executor.submit(new WorkerRunnable2(doneSignal, 1));
		Future<ReBack> future2 = executor.submit(new WorkerRunnable2(doneSignal, 2));
		try {
			doneSignal.await(100, TimeUnit.MINUTES);// wait for all to finish
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		ReBack reBack = future1.get();
		System.out.println("完成:" + reBack.toString());
		ReBack reBack2 = future2.get();
		System.out.println("完成：" + reBack2.toString());
		executor.shutdown();
	}
	static  class WorkerRunnable2 implements Callable<ReBack> {
		private CountDownLatch doneSignal;
		private int i;

		WorkerRunnable2(CountDownLatch doneSignal, int i) {
			this.doneSignal = doneSignal;
			this.i = i;
		}

		public ReBack call() throws Exception {
			try {
				System.out.println("执行中" + i);
				try {
					Thread.sleep(2000 * i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ReBack reBack = new ReBack();
				if (i == 1)
					reBack.setS1(1 + "");
				else
					reBack.setS2(2 + "");
				return reBack;
			} finally {
				doneSignal.countDown();
			}
		}
	}

	static class ReBack {
		private String s1;
		private String s2;

		public String getS1() {
			return s1;
		}

		public void setS1(String s1) {
			this.s1 = s1;
		}

		public String getS2() {
			return s2;
		}

		public void setS2(String s2) {
			this.s2 = s2;
		}

		@Override
		public String toString() {
			return "reBack [s1=" + s1 + ", s2=" + s2 + "]";
		}

	}
}


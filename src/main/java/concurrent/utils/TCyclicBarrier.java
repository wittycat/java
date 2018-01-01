package concurrent.utils;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author：chenxun 
 * @create：2016年7月4日 上午11:50:44 
 * @description：
 * 1.内部维护一个count 当count为0时，等待的线程开始运行，
 * 恢复CyclicBarrier的count为原初始值parties
 */
public class TCyclicBarrier {

	public static void main(String[] args) throws InterruptedException {

		final Map <String,Number>  result = new ConcurrentHashMap <String,Number>();

	    final ExecutorService threadPool = Executors.newFixedThreadPool(2);
	    final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
			public void run() {
				Set<String> keySet = result.keySet();
				for (String key : keySet) {
					System.out.println(key+"="+result.get(key));
				}
				result.clear();
			}
		});
		threadPool.submit(new TestTask(cyclicBarrier, result,1));
		threadPool.submit(new TestTask(cyclicBarrier, result,2));
		
		//第二次重复使用
//		TimeUnit.SECONDS.sleep(3);
		System.out.println("=============华丽分割线=========================");
		threadPool.submit(new TestTask(cyclicBarrier, result,1));
		threadPool.submit(new TestTask(cyclicBarrier, result,2));
	}
	static  class TestTask implements Runnable {

		private CyclicBarrier cy;
		private Map <String,Number> result;
		private int  type;
		private Random random = new Random();
		public TestTask(CyclicBarrier cb, Map <String,Number> result,int  type) {
			this.cy = cb;
			this.result = result;
			this.type = type;
		}

		public void run() {
			try {
				if(1==type){
					System.out.println("type="+type);
					result.put("totalAmount", random.nextInt(100));
					result.put("countloan", random.nextInt(100));
				}
				if(2==type){
					System.out.println("type="+type);
					result.put("countplan", random.nextInt(100));
				}
			} finally {
				try {
					cy.await();
				} catch (InterruptedException | BrokenBarrierException ignore) {
				}
			}
		}

	}
}



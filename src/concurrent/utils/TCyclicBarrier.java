package concurrent.utils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author：chenxun create：2016年7月4日 上午11:50:44 description：
 */
public class TCyclicBarrier {

	public static void main(String[] args) throws InterruptedException {

		final Map <String,Number>  result = new ConcurrentHashMap <String,Number>();

	    final ExecutorService threadPool = Executors.newFixedThreadPool(2);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {

			public void run() {
				System.out.println("前"+result.size());
				Set<String> keySet = result.keySet();
				for (String key : keySet) {
					System.out.println(key+"="+result.get(key));
				}
				result.clear();
				System.out.println("后"+result.size());
				threadPool.shutdown();
			}
		});
		threadPool.submit(new TestTask(cyclicBarrier, result,1));
		threadPool.submit(new TestTask(cyclicBarrier, result,2));
		System.out.println("world");
	}

}

 class TestTask implements Runnable {

	private CyclicBarrier cy;
	private Map <String,Number> result;
	private int  type;
	public TestTask(CyclicBarrier cb, Map <String,Number> result,int  type) {
		this.cy = cb;
		this.result = result;
		this.type = type;
	}

	public void run() {
		try {
			if(1==type){
				System.out.println("。。。。"+type);
				Thread.sleep(5000);
				result.put("totalAmount", 100000);
				result.put("countloan", 200000);
			}
			
			if(2==type){
				System.out.println("。。。。"+type);
				Thread.sleep(10000);
				result.put("countplan", 200000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				cy.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

}

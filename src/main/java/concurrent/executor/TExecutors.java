package concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author：chenxun 
 * @创建时间：2016年6月2日 下午11:26:40 
 * @参考： 
 * @说明：
 */
public class TExecutors {

	public static void main(String[] args) {
		// 创建执行器 线程池
		ExecutorService pool = Executors.newFixedThreadPool(1);
		// 添加任务
		for (int i = 0; i < 3; i++) {
			Runnable runnable = new Runnable() {
				@SuppressWarnings("static-access")
				public void run() {
					try {
						Thread.currentThread().sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}
			};

			pool.submit(runnable);
		}
		pool.isShutdown();
	}

}

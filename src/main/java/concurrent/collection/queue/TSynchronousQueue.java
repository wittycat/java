package concurrent.collection.queue;

import java.util.concurrent.SynchronousQueue;

/** 
 * @Author:chenxun
 * @2017年1月9日 下午9:42:44 
 * @Theme:
 * @Reference:
 * @Descript:
 * 1.一个不存储元素的阻塞队列。
 * 2.其中每个插入操作必须等待另一个线程的对应移除操作，如果没被消费则一直处于阻塞
 * 3.此同步队列没有任何内部容量，甚至连一个队列的容量都没有,适合传递性的应用场景
 */
public class TSynchronousQueue {
	public static void main(String[] args) throws InterruptedException {
		final SynchronousQueue<String> synchronousQueue = new SynchronousQueue<String>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(true){
					try {
						String valueOf = String.valueOf(++i);
						System.out.format("放入%s%n", valueOf);
						synchronousQueue.put(valueOf);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
		Thread.sleep(10000L);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						System.out.format("take:%s%n%n",synchronousQueue.take());
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}
}

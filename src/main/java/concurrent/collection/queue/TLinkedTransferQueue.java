package concurrent.collection.queue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/** 
 * @Author:chenxun
 * @2017年1月12日 上午2:01:31 
 * @Theme:
 * @Reference:
 *  http://blog.csdn.net/yjian2008/article/details/16951811
 *  http://www.cnblogs.com/rockman12352/p/3790245.html
 * @Descript:一个链表实现的无界阻塞队列
 * 1.tryTransfer()尝试传递给消费者 ,没有消费者放入队列
 * 2.transfer()尝试传递给消费者 ,没有消费者自己处于阻塞状态，这种模式类似与SynchronousQueue
 * 3.无论是transfer还是tryTransfer方法，在>=1个消费者线程等待获取元素时（此时队列为空），都会立刻转交，
 *    这属于线程之间的元素交换。注意，这时，元素并没有进入队列。
 * 4.在队列中已有数据情况下，transfer将需要等待前面数据被消费掉，直到传递的元素e被消费线程取走为止。
 */
public class TLinkedTransferQueue {
    public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(2);
		final LinkedTransferQueue<String> quene = new LinkedTransferQueue<String>();
		//生产者
    	new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ; 
				while(true){
					try {
						String valueOf = String.valueOf(++i);
						quene.transfer(valueOf);
						quene.tryTransfer(valueOf);
						System.out.format("生产--------%s---size:%s%n", valueOf,quene.size());
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
    	
    	//消费者
    	new Thread(new Runnable() {
    		@Override
    		public void run() {
    			while(true){
					try {
						TimeUnit.SECONDS.sleep(5);
						System.out.format("消费********%s%n", quene.take());
					} catch (InterruptedException e) {
					}
				}
    		}
    	}).start();
    	
    	while(Thread.activeCount()>1)
    		Thread.yield();
	}
}

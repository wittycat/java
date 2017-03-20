package concurrent.collection.queue;

import java.util.concurrent.ArrayBlockingQueue;

/** 
 * @Author:chenxun
 * @2017年1月6日 下午11:32:35 
 * @Theme:
 * @Reference:
 * @Descript:
 * <ul>
 * <li>一个由数组支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序</li>
 * <li>[add:remove]没有值或队列满时，操作报异常；属于Queue接口的规范</li>
 * <li>[offer:poll]返回特殊值（null或波尔值），队列满时添加失败，造成丢失元素；属于Queue接口的规范</li>
 * <li>[put:take]空或满时操作阻塞，但是不会丢失元素；属于BlockingQueue接口的规范</li>
 * </ul>
 */
public class TArrayBlockingQueue {
    public static void main(String[] args) {
		final ArrayBlockingQueue<String> quene = new ArrayBlockingQueue<String>(10);
    	//生产者
    	new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ; 
				while(true){
					String valueOf = String.valueOf(++i);
					try {
						
						quene.put(valueOf);	
						System.out.format("生产--------%s%n", valueOf);
						
//						boolean offer = quene.put(valueOf);	
//						System.out.format("生产--------%s%n", offer==true?valueOf:"");
						
//						quene.add(valueOf);	
//						System.out.format("生产--------%s%n", valueOf);
						
//						System.out.format("容器--------%s%n%n", quene.toString());
						Thread.sleep(1000L);
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
						quene.take();
//						quene.poll();
//						quene.remove();
//						System.out.format("消费********%s%n", quene.poll());
						Thread.sleep(2000L);
					} catch (InterruptedException e) {
					}
				}
    		}
    	}).start();
    	
    	while(Thread.activeCount()>1)
    		Thread.yield();
	}
}

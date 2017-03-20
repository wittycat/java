package concurrent.collection.queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/** 
 * @Author:chenxun
 * @2017年1月9日 下午10:19:20 
 * @Theme:延时队列测试
 * @Reference:
 * @Descript:
 * 1.DelayQueue一个无界阻塞队列
 * 2.内部根据PriorityQueue（无界线程不安全队列） 存储元素
 * 3.适用场景：定时执行（获取），缓存失效等
 * 
 */
public class TDelayQueue {
	
	static class  Element implements Delayed{
		
		private final long beginTime = System.nanoTime();
		private final long endTime ;
		private final long lastTime;
		private String name;
		
		public Element(String name,long lastTime) {
			this.name = name;
			this.lastTime = lastTime;
			this.endTime = beginTime+lastTime;
		}
		
		@Override
		public int compareTo(Delayed o) {
			if(((Element) o).getLastTime()>getLastTime()){
				return -1;
			}
			if(((Element) o).getLastTime()<getLastTime()){
				return 1;
			}
			return 0;
		}

		public long getLastTime() {
			return lastTime;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return  endTime - System.nanoTime();
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			long convert = TimeUnit.SECONDS.convert(getDelay(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
			return getName()+":"+convert;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {

		final long oneSecond = TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS);
//		PriorityQueue<Element> priorityQueue = new PriorityQueue<Element>(10);
//		for (int i = 0; i < 10; i++) {
//			priorityQueue.add(new Element(i+"", i));
//			System.out.println(priorityQueue.toString());
//		}
		final DelayQueue<Element> queue = new DelayQueue<Element>();
		final Random random = new Random();
		new Thread(new Runnable() {
			@Override
			public void run() {
				long i = 0;
				while(true){
					i++;
					System.out.format("放入%s%n", i);
					//随机数+1，防止为0
					queue.put(new Element(String.valueOf(i),(random.nextInt(10)+1)*oneSecond) );
					if(i==10)
					     break;
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
		
		Thread.sleep(1000L);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						if(queue.size()>0){
							System.out.format("take:%s队列剩余%s%n",queue.take().getName(),queue.toString());
						}else{
							 break;
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}
}

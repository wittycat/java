package concurrent.utils;

import java.util.concurrent.Exchanger;

/** 
 * @author:chenxun
 * @2016年12月24日 上午10:02:30 
 * @Theme:
 * @Reference:
 * @Descript:
 * 1.每个线程既可以充当消费者，也可以充当生产者
 * 2.当生产大于消费时，如果一直没有被消费完，线程将处于阻塞状态
 * 
 */
public class TExchanger {
	public static void main(String[] args) {
		final Exchanger<Integer> exchanger = new Exchanger<Integer>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.format("%s,放入值1,取出的值%d%n",Thread.currentThread().getName(), exchanger.exchange(1));
				} catch (InterruptedException e) {}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.format("%s,放入值2,取出的值%d%n",Thread.currentThread().getName(), exchanger.exchange(2));
				} catch (InterruptedException e) {}
			}
		}).start();
	}
}
